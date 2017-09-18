package com.example.lsireneva.flickster.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luba on 9/16/17.
 */

public class Video implements Parcelable {

    private String videoId;
    private String key;
    private String site;


    public String getVideoId() {
        return videoId;
    }



    public String getKey() {
        return key;
    }

    public String getSite() {
        return site;
    }


    public Video(JSONObject jsonObject) throws JSONException {
        this.videoId = jsonObject.getString("id");
        this.key = jsonObject.getString("key");
        this.site = jsonObject.getString("site");

    }

    protected Video(Parcel in) {
        videoId = in.readString();
        key = in.readString();
        site = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(videoId);
        parcel.writeString(key);
        parcel.writeString(site);
    }


}
