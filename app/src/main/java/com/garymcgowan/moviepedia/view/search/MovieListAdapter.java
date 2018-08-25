package com.garymcgowan.moviepedia.view.search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.garymcgowan.moviepedia.R;
import com.garymcgowan.moviepedia.model.Movie;
import com.garymcgowan.moviepedia.view.details.MovieDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private final List<Movie> movieList;

    public MovieListAdapter(List<Movie> items) {
        movieList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        ViewHolder vh = new ViewHolder(view);

//        RxView.clicks(view)
//                .map(v -> vh.getAdapterPosition())
//                .subscribe(onClickSubject);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie currentMovie = movieList.get(position);
//set movie title
        holder.titleTextView.setText(currentMovie.getTitleYear());

        //load image with Picasso
        Picasso.with(holder.mView.getContext())
                .load(currentMovie.getPosterURL())
                .placeholder(R.color.imagePlaceholder)
                .error(R.color.imagePlaceholder)
                .into(holder.posterImageView);

        holder.mView.setOnClickListener(v -> {
            //launch details intent
            Context context = v.getContext();
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra(MovieDetailsActivity.ARG_ITEM_ID, currentMovie.getImdbID());
            intent.putExtra(MovieDetailsActivity.ARG_ITEM_TITLE, currentMovie.getTitle());

            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        if (movieList == null) {
            return 0;
        }
        return movieList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;

        @BindView(R.id.titleTextView) TextView titleTextView;
        @BindView(R.id.posterImageView) ImageView posterImageView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
