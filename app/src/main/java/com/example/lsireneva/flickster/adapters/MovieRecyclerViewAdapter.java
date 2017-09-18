package com.example.lsireneva.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lsireneva.flickster.R;
import com.example.lsireneva.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.example.lsireneva.flickster.R.id.ivBackdrop;
import static com.example.lsireneva.flickster.R.id.ivMovieImage;

/**
 * Created by luba on 9/13/17.
 */

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Movie> playingMovies;
    private Context mContext;
    private final int POPULAR = 0, LESSPOPULAR = 1;
    private OnMovieRecyclerViewAdapterListener recyclerViewAdapterListener;

    public MovieRecyclerViewAdapter(ArrayList<Movie> movies, Context context, OnMovieRecyclerViewAdapterListener listener) {
        this.playingMovies = movies;
        this.mContext = context;
        this.recyclerViewAdapterListener = listener;
    }

    public interface OnMovieRecyclerViewAdapterListener {
        void selectMovie(Movie movie);
    }



    private Context getContext() {
        return mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;

        switch (viewType) {
            case POPULAR:
                View viewPopularMovie = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_popular, parent, false);
                viewHolder = new PopularMovieViewHolder(viewPopularMovie);
                break;
            case LESSPOPULAR:
                View viewLessPopularMovie = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
                viewHolder = new MovieViewHolder(viewLessPopularMovie);
                break;
            default:
                View viewLessPopularMovieDefault = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
                viewHolder = new MovieViewHolder(viewLessPopularMovieDefault);
                break;
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {
            case POPULAR:
                ((PopularMovieViewHolder) viewHolder).setupMovieView(playingMovies.get(position));
                break;
            case LESSPOPULAR:
                ((MovieViewHolder) viewHolder).setupMovieView(playingMovies.get(position));
                break;
            default:
                ((MovieViewHolder) viewHolder).setupMovieView(playingMovies.get(position));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return this.playingMovies != null ? this.playingMovies.size() : 0;
    }

    public void notifyDataSetChanged(ArrayList<Movie> movies) {
        this.playingMovies = movies;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        Movie movie = playingMovies.get(position);
        if (movie.getVoteAverage().compareTo(5.0) >= 0) {
            return POPULAR;
        } else {
            return LESSPOPULAR;
        }
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private Movie movie;

        ImageView ivImage;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivBackDrop;
        ProgressBar progressBar;

        public MovieViewHolder(View itemView) {
            super(itemView);

            ivImage = (ImageView) itemView.findViewById(ivMovieImage);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            ivBackDrop = (ImageView) itemView.findViewById(ivBackdrop);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewAdapterListener != null) recyclerViewAdapterListener.selectMovie(movie);
                }
            });
        }

        public void setupMovieView(Movie movie) {
            this.movie = movie;
            if (this.ivImage != null) {
                // Show progress bar
                progressBar.setVisibility(View.VISIBLE);
                Picasso.with(itemView.getContext()).load(movie.getPosterPath())
                        .resize(600, 0)
                        //.placeholder(R.drawable.placeholder)
                        //.error(R.drawable.placeholder_error)
                        .transform(new RoundedCornersTransformation(15, 15, RoundedCornersTransformation.CornerType.ALL))
                        .into(ivImage, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                if (progressBar != null) {
                                    progressBar.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onError() {

                            }
                        });

            }


            if (this.ivBackDrop != null) {
                ivBackDrop.setImageDrawable(null);
                Picasso.with(itemView.getContext()).load(movie.getBackDropPath())
                        .resize(1280, 0)
                        //.placeholder(R.drawable.placeholder)
                        //.error(R.drawable.placeholder_error)
                        .transform(new RoundedCornersTransformation(15, 15, RoundedCornersTransformation.CornerType.ALL))
                        .into(ivBackDrop, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                if (progressBar != null) {
                                    progressBar.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onError() {

                            }
                        });


            }

            this.tvTitle.setText(movie.getOriginalTitle());
            this.tvOverview.setText(movie.getOverview());

        }
    }

    class PopularMovieViewHolder extends RecyclerView.ViewHolder {
        private View view;

        private Movie movie;


        ImageView ivBackDrop;
        ProgressBar progressBar;

        TextView tvTitle, tvOverview;


        public PopularMovieViewHolder(View itemView) {
            super(itemView);
            ivBackDrop = (ImageView) itemView.findViewById(R.id.ivBackdropForPopular);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewAdapterListener != null) recyclerViewAdapterListener.selectMovie(movie);
                }
            });

        }


        public void setupMovieView(Movie movie) {
            this.movie = movie;
            if (this.ivBackDrop != null) {
                ivBackDrop.setImageDrawable(null);
                Picasso.with(itemView.getContext()).load(movie.getBackDropPath())
                        .resize(1280, 0)
                        //.placeholder(R.drawable.placeholder)
                        //.error(R.drawable.placeholder_error)
                        .transform(new RoundedCornersTransformation(15, 15, RoundedCornersTransformation.CornerType.ALL))
                        .into(ivBackDrop, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                if (progressBar != null) {
                                    progressBar.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onError() {

                            }
                        });

            }
            int orientation = getContext().getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                this.tvTitle.setText(movie.getOriginalTitle());
                this.tvOverview.setText(movie.getOverview());
            }

        }
    }
}
