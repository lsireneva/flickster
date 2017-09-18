package com.example.lsireneva.flickster.network;

import com.example.lsireneva.flickster.models.Video;

import java.util.ArrayList;

/**
 * Created by luba on 9/17/17.
 */

public interface MovieVideoCallback {
    void onSuccess(ArrayList<Video> videos);

    void onError(Error error);
}
