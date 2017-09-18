package com.example.lsireneva.flickster.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by luba on 9/5/17.
 */

public class Movie implements Parcelable {

    protected Movie(Parcel in) {
        posterPath = in.readString();
        originalTitle = in.readString();
        overview = in.readString();
        backDropPath = in.readString();
        popularity = in.readDouble();
        voteAverage = in.readDouble();
        movieId = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterPath);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(backDropPath);
        dest.writeDouble(popularity);
        dest.writeDouble(voteAverage);
        dest.writeLong(movieId);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackDropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backDropPath);
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public Long getMovieId() {
        return movieId;
    }
    public Double getPopularity() {
        return popularity;
    }


    private Long movieId;
    private Double popularity;
    private String posterPath;
    private String originalTitle;
    private String overview;
    private String backDropPath;
    private Double voteAverage;
    private ArrayList<Video> videos;


    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.backDropPath = jsonObject.getString("backdrop_path");
        this.voteAverage = jsonObject.getDouble("vote_average");
        this.movieId = jsonObject.getLong("id");
        this.popularity = jsonObject.getDouble("popularity");

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }


}
