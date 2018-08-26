package com.garymcgowan.moviepedia.view.search

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.garymcgowan.moviepedia.R
import com.garymcgowan.moviepedia.model.Movie
import com.garymcgowan.moviepedia.view.details.MovieDetailsActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieListAdapter(
        private val movieList: List<Movie>?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false)

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
                holder.titleTextView.text = currentMovie.titleYear

                //load image with Picasso
                Picasso.with(holder.parentView.context)
                        .load(currentMovie.posterURL)
                        .placeholder(R.color.imagePlaceholder)
                        .error(R.color.imagePlaceholder)
                        .into(holder.posterImageView)

                holder.parentView.setOnClickListener { v ->
                    //launch details intent
                    val context = v.context
                    val intent = Intent(context, MovieDetailsActivity::class.java)
                    intent.putExtra(MovieDetailsActivity.ARG_ITEM_ID, currentMovie.imdbID)
                    intent.putExtra(MovieDetailsActivity.ARG_ITEM_TITLE, currentMovie.title)

                    context.startActivity(intent)
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
    }
}
