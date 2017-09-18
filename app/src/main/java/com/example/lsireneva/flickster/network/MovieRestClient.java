package com.example.lsireneva.flickster.network;

import com.example.lsireneva.flickster.models.Movie;
import com.example.lsireneva.flickster.models.Video;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

/**
 * Created by luba on 9/16/17.
 */

public class MovieRestClient {

    private static String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=5a7453417d0d70173f124faff2a0599e";

    private static AsyncHttpClient client = new AsyncHttpClient();


    public static void getNowPlayingMovies(final NowPlayingMoviesCallback callback) {

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults = null;
                ArrayList<Movie> movies = new ArrayList<>();

                try {

                    movieJsonResults = response.getJSONArray("results");
                    for (int i = 0; i < movieJsonResults.length(); i++) {

                        movies.add(new Movie(movieJsonResults.getJSONObject(i)));
                    }
                    callback.onSuccess(movies);

                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onError(new Error(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                callback.onError(new Error(throwable.getMessage()));
            }
        });

    }


    public static void getMovieVideo(Long movieId, final MovieVideoCallback callback) {
        String url = String.format(Locale.getDefault(), "https://api.themoviedb.org/3/movie/%d/videos?api_key=5a7453417d0d70173f124faff2a0599e", movieId);

        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray jsonVideos = response.getJSONArray("results");
                    ArrayList<Video> videos = new ArrayList<>();
                    for (int i = 0; i < jsonVideos.length(); i++) {
                        videos.add(new Video(jsonVideos.getJSONObject(i)));
                    }

                    callback.onSuccess(videos);
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onError(new Error(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                callback.onError(new Error(throwable.getMessage()));
            }
        });
    }


}
