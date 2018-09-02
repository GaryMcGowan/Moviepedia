package com.garymcgowan.moviepedia.view.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import com.garymcgowan.moviepedia.App
import com.garymcgowan.moviepedia.R
import com.garymcgowan.moviepedia.model.Movie
import com.garymcgowan.moviepedia.network.OmdbMovieRepository
import com.garymcgowan.moviepedia.widget.VariableColumnGridLayoutManager
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_list.*
import timber.log.Timber
import javax.inject.Inject

class MovieListActivity : AppCompatActivity(), MovieListActivityContract.View {

    @Inject lateinit var movieRepository: OmdbMovieRepository

    internal var presenter: MovieListActivityPresenter? = null
    internal var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        App.getApplicationComponent().inject(this)

        setSupportActionBar(toolbar)
        toolbar.title = title
        toolbar.setNavigationIcon(R.mipmap.movie_white)
        assert(recyclerView != null)

        recyclerView.layoutManager = VariableColumnGridLayoutManager(this, R.dimen.list_item_width)
        setupRecyclerView(recyclerView, null)

        presenter = MovieListActivityPresenter(this, movieRepository, AndroidSchedulers.mainThread())

        searchView?.let { searchView ->
            presenter?.setSearchTermObservable(RxSearchView.queryTextChanges(searchView).toFlowable(BackpressureStrategy.LATEST).map { it.toString() })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_list, menu)

        val searchItem = menu.findItem(R.id.action_search)

        val searchManager = this@MovieListActivity.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        if (searchItem != null) {
            setSearchView(searchItem.actionView as SearchView)
        }
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(this@MovieListActivity.componentName))

        return super.onCreateOptionsMenu(menu)
    }

    private fun setSearchView(searchView: SearchView?) {

        if (this.searchView !== searchView) {
            this.searchView = searchView
            searchView?.let {
                presenter?.setSearchTermObservable(RxSearchView.queryTextChanges(searchView).toFlowable(BackpressureStrategy.LATEST).map { it.toString() })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.clearDisposables()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, list: List<Movie>?) {
        when {
            // null for first time setup
            list == null -> {
                emptyTextView.setText(R.string.empty_text_first_time)
                emptyTextView.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
            // empty list means no results
            list.isEmpty() -> {
                emptyTextView.setText(R.string.empty_text)
                emptyTextView.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
            // finally, we have some movies
            else -> {
                emptyTextView.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                recyclerView.adapter = MovieListAdapter(list)
            }
        }
    }

    override fun displayMovies(movies: List<Movie>) {
        setupRecyclerView(recyclerView, movies)
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
