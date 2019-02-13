package com.garymcgowan.moviepedia.view.details

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.garymcgowan.moviepedia.BaseFragment
import com.garymcgowan.moviepedia.R
import com.garymcgowan.moviepedia.model.Movie
import com.squareup.picasso.Picasso
import io.reactivex.annotations.NonNull
import kotlinx.android.synthetic.main.fragment_details.*
import timber.log.Timber
import javax.inject.Inject


class MovieDetailsFragment : BaseFragment(), MovieDetailsContract.View {

    companion object {
        const val ARG_ITEM_ID = "imdb_id"
        const val ARG_ITEM_TITLE = "movie_title"

        fun safeArgs(imdbId: String, movieTitle: String) = Bundle().apply {
            putString(ARG_ITEM_ID,imdbId)
            putString(ARG_ITEM_TITLE,movieTitle)
        }
    }

    @Inject lateinit var picasso: Picasso

    internal var movie: Movie? = null

    @Inject lateinit var presenter: MovieDetailsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? AppCompatActivity)?.supportActionBar?.setHomeButtonEnabled(true)

        //clear dummy text
        ratedTextView.text = null
        runtimeTextView.text = null
        plotTextView.text = null
        ratingTextView.text = null
        directorTextView.text = null
        actorsTextView.text = null
        writerTextView.text = null
        awardsTextView.text = null


        val imdbId = arguments?.getString(ARG_ITEM_ID)
        val title = arguments?.getString(ARG_ITEM_TITLE)

        toolbar.apply {
            title?.let { (toolbar_layout as? Toolbar)?.title = it }
        }

        imdbId?.let { presenter.loadMovieDetails(it) }
    }


    private fun setMovie(@NonNull movie: Movie?) {
        this.movie = movie

        assert(movie != null)
        if (movie == null) {
            return
        }

        if (posterImageView != null) picasso.apply {
            load(movie.posterURL).placeholder(R.color.imagePlaceholder).error(R.color.imagePlaceholder).into(posterImageView)
        }

        if (toolbar != null) {
            (toolbar_layout as? Toolbar)?.title = movie.titleYear()
        }

        if (plotTextView != null) {
            ratedTextView.text = movie.rated
            runtimeTextView.text = movie.runtime
            plotTextView.text = movie.plot

            ratingTextView.text = movie.imdbRating + "/10 (" + movie.imdbVotes + ")"
            directorTextView.text = movie.director
            actorsTextView.text = movie.actors
            writerTextView.text = movie.writer
            awardsTextView.text = movie.awards
        }
    }

    // for UP AS HOME functionality
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            //todo go back
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        presenter.takeView(this)
    }

    //clean up Rx
    override fun onDestroy() {
        super.onDestroy()
        presenter.clearDisposables()
        presenter.dropView()
    }

    override fun displayMovieDetails(movieDetails: Movie) {
        setMovie(movieDetails)
    }

    override fun displayError(message: String) {
        Timber.d("Error: $message")
        context?.let {
            AlertDialog.Builder(it).setTitle("Error").setMessage("Something went wrong").create().show()
        }
    }

}
