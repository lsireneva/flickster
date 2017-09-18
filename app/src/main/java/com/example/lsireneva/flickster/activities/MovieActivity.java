package com.example.lsireneva.flickster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lsireneva.flickster.R;
import com.example.lsireneva.flickster.adapters.MovieRecyclerViewAdapter;
import com.example.lsireneva.flickster.models.Movie;
import com.example.lsireneva.flickster.network.MovieRestClient;
import com.example.lsireneva.flickster.network.NowPlayingMoviesCallback;

import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity implements MovieRecyclerViewAdapter.OnMovieRecyclerViewAdapterListener{

    ArrayList<Movie> movies;
    MovieRecyclerViewAdapter movieAdapter;
    RecyclerView rvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        rvMovies = (RecyclerView) findViewById(R.id.rvMovies);
        rvMovies.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvMovies.setLayoutManager(linearLayoutManager);
        movieAdapter = new MovieRecyclerViewAdapter(movies, this, this);
        rvMovies.setAdapter(movieAdapter);

        MovieRestClient.getNowPlayingMovies(new NowPlayingMoviesCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> movies) {
                MovieActivity.this.movies = new ArrayList<>();
                MovieActivity.this.movies.addAll(movies);
                movieAdapter.notifyDataSetChanged(MovieActivity.this.movies);
            }

            @Override
            public void onError(Error error) {
                error.printStackTrace();
            }
        });

    }

    @Override
    public void selectMovie(Movie movie) {

        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.MOVIE_DETAIL, (Parcelable) movie);
        startActivity(intent);
    }
}
