package com.garymcgowan.moviepedia.view.search

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import com.garymcgowan.moviepedia.R
import com.garymcgowan.moviepedia.model.Movie
import com.garymcgowan.moviepedia.view.details.MovieDetailsFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieListAdapter(private val movieList: List<Movie>?, private val favCallback: (Movie) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)

        //        RxView.clicks(view)
        //                .map(v -> vh.getAdapterPosition())
        //                .subscribe(onClickSubject);

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMovie = movieList!![position]
        when (holder) {
            is ViewHolder -> {
                //set movie title
                holder.titleTextView.text = currentMovie.titleYear()

                //load image with Picasso
                Picasso.with(holder.parentView.context).load(currentMovie.posterURL).placeholder(R.color.imagePlaceholder).error(R.color.imagePlaceholder).into(holder.posterImageView)

                holder.parentView.setOnClickListener { v ->
                    Navigation.findNavController(v)
                            .navigate(
                                    R.id.navigation_details,
                                    MovieDetailsFragment.safeArgs(currentMovie.imdbID, currentMovie.title)
                    )
                }

                holder.favouriteButton.setOnClickListener {
                    favCallback.invoke(currentMovie)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return movieList?.size ?: 0
    }

    internal class ViewHolder(val parentView: View) : RecyclerView.ViewHolder(parentView) {
        var titleTextView: TextView = parentView.titleTextView
        var posterImageView: ImageView = parentView.posterImageView
        var favouriteButton: View = parentView.favourite_button
    }
}
