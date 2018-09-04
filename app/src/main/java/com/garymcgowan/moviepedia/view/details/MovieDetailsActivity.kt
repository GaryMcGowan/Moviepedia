package com.garymcgowan.moviepedia.view.details

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.garymcgowan.moviepedia.App
import com.garymcgowan.moviepedia.R
import com.garymcgowan.moviepedia.model.Movie
import com.garymcgowan.moviepedia.network.OmdbMovieRepository
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import kotlinx.android.synthetic.main.activity_details.*
import timber.log.Timber
import javax.inject.Inject

class MovieDetailsActivity : AppCompatActivity(), MovieDetailsActivityContract.View {

    companion object {

        val ARG_ITEM_ID = "imdb_id"
        val ARG_ITEM_TITLE = "movie_title"
    }

    @Inject lateinit var movieRepository: OmdbMovieRepository
    @Inject lateinit var picasso: Picasso
    internal var movie: Movie? = null

    internal var presenter: MovieDetailsActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        App.getApplicationComponent().inject(this)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        //clear dummy text
        ratedTextView.text = null
        runtimeTextView.text = null
        plotTextView.text = null
        ratingTextView.text = null
        directorTextView.text = null
        actorsTextView.text = null
        writerTextView.text = null
        awardsTextView.text = null

        presenter = MovieDetailsActivityPresenter(this, movieRepository, AndroidSchedulers.mainThread())

        val imdbId = intent.getStringExtra(ARG_ITEM_ID)
        val title = intent.getStringExtra(ARG_ITEM_TITLE)

        if (title != null) {
            (toolbar_layout as? Toolbar)?.title = title
        }

        if (imdbId != null) {
            presenter?.loadMovieDetails(imdbId)
        }
    }

    fun setMovie(@NonNull movie: Movie?) {
        this.movie = movie

        assert(movie != null)
        if (movie == null) {
            return
        }

        if (posterImageView != null)
            picasso.apply {
                load(movie.posterURL)
                        .placeholder(R.color.imagePlaceholder)
                        .error(R.color.imagePlaceholder)
                        .into(posterImageView)
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
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //clean up Rx
    override fun onDestroy() {
        super.onDestroy()
        presenter?.clearDisposables()
    }

    override fun displayMovieDetails(movieDetails: Movie) {
        setMovie(movieDetails)
    }

    override fun displayError(message: String) {
        //show nice message
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Error")
        alertDialogBuilder.setMessage("Something went wrong")
        alertDialogBuilder.create().show()

        Timber.d("Error: $message")
    }


}
