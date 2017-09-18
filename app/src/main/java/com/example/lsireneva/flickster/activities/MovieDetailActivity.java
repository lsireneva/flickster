package com.example.lsireneva.flickster.activities;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.lsireneva.flickster.R;
import com.example.lsireneva.flickster.models.Movie;
import com.example.lsireneva.flickster.models.Video;
import com.example.lsireneva.flickster.network.MovieRestClient;
import com.example.lsireneva.flickster.network.MovieVideoCallback;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

public class MovieDetailActivity extends YouTubeBaseActivity {

    public static final String MOVIE_DETAIL = "MOVIE";
    public static final String YT_API_KEY = " AIzaSyC3nJT7LRguczFXiHTpcbgHwFwo5EAyBMo";

    private Movie movie;
    //ImageView tvImage;
    TextView tvTitle, tvOverview, tvPopularity;
    RatingBar ratingBar;
    YouTubePlayerView player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle data = getIntent().getExtras();
        movie = data.getParcelable(MOVIE_DETAIL);
        setupUILayout();
    }

    private void setupUILayout() {
        //tvImage =(ImageView) findViewById(R.id.ivMovieImage);
        MovieRestClient.getMovieVideo(movie.getMovieId(), new MovieVideoCallback() {
            @Override
            public void onSuccess(ArrayList<Video> videos) {
                movie.setVideos(videos);
                showYoutubeVideo();
                            }

            @Override
            public void onError(Error error) {
                error.printStackTrace();
            }
        });
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        tvPopularity = (TextView) findViewById(R.id.tvPopularity);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        /*Picasso.with(this).load(movie.getPosterPath())
                .resize(600, 0)
                //.placeholder(R.drawable.placeholder)
                //.error(R.drawable.placeholder_error)
                .transform(new RoundedCornersTransformation(15, 15, RoundedCornersTransformation.CornerType.ALL))
                .into(tvImage);*/

        tvTitle.setText(movie.getOriginalTitle());
        tvOverview.setText(movie.getOverview());
        tvPopularity.setText(String.format("Popularity: %.2f", movie.getPopularity()));
        ratingBar.setRating(movie.getVoteAverage().floatValue() / 2);

    }

    private void showYoutubeVideo() {
        player = (YouTubePlayerView) findViewById(R.id.player);

        player.initialize(YT_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                final YouTubePlayer youTubePlayer, boolean b) {

                ArrayList<String> keys = new ArrayList<>();
                for (Video video : movie.getVideos()) {
                    if ("YouTube".equals(video.getSite()))
                        keys.add(video.getKey());
                }

                if (movie.getVoteAverage().compareTo(5.0) > 0) {
                    youTubePlayer.loadVideos(keys);
                    youTubePlayer.setFullscreen(true);
                } else {
                    youTubePlayer.cueVideos(keys);
                }

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }
}
