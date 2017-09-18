package com.example.lsireneva.flickster.network;

import com.example.lsireneva.flickster.models.Movie;

import java.util.ArrayList;

/**
 * Created by luba on 9/17/17.
 */

public interface NowPlayingMoviesCallback {
    void onSuccess(ArrayList<Movie> movies);

    void onError(Error error);
}
