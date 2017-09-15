package com.example.lsireneva.flickster.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    public MovieRecyclerViewAdapter(ArrayList<Movie> movies, Context context) {
        this.playingMovies = movies;
        this.mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MovieViewHolder) {
            ((MovieViewHolder) holder).setupMovieView(playingMovies.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return this.playingMovies != null ? this.playingMovies.size() : 0;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private Movie movie;

        ImageView ivImage;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivBackDrop;

        public MovieViewHolder(View itemView) {
            super(itemView);

            ivImage = (ImageView) itemView.findViewById(ivMovieImage);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            ivBackDrop = (ImageView) itemView.findViewById(ivBackdrop);
        }

        public void setupMovieView(Movie movie) {
            this.movie = movie;
            if (this.ivImage != null) {
                Picasso.with(itemView.getContext()).load(movie.getPosterPath())
                        .resize(600, 0)
                        //.placeholder(R.drawable.placeholder)
                        .transform(new RoundedCornersTransformation(15, 15, RoundedCornersTransformation.CornerType.ALL))
                        .into(ivImage);

            }


            if (this.ivBackDrop != null) {
                ivBackDrop.setImageDrawable(null);
                Picasso.with(itemView.getContext()).load(movie.getBackDropPath())
                        .resize(900, 0)
                        //.placeholder(R.drawable.placeholder)
                        .transform(new RoundedCornersTransformation(15, 15, RoundedCornersTransformation.CornerType.ALL))
                        .into(ivBackDrop);

            }


            this.tvTitle.setText(movie.getOriginalTitle());
            this.tvOverview.setText(movie.getOverview());

        }
    }
}
