package com.garymcgowan.moviepedia.view.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.garymcgowan.moviepedia.BaseFragment
import com.garymcgowan.moviepedia.R
import com.garymcgowan.moviepedia.model.Movie
import com.garymcgowan.moviepedia.widget.VariableColumnGridLayoutManager
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import io.reactivex.BackpressureStrategy
import kotlinx.android.synthetic.main.fragment_search_list.*
import timber.log.Timber
import javax.inject.Inject

class MovieListFragment : BaseFragment(), MovieListContract.View {

    @Inject lateinit var presenter: MovieListPresenter

    private var searchView: SearchView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_search_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.apply {
            title = title
            setNavigationIcon(R.mipmap.movie_white)
            //setNavigationOnClickListener { presenter.onCancelPressed() }
        }

        context?.let { context ->
            recyclerView.layoutManager = VariableColumnGridLayoutManager(context, R.dimen.list_item_width)
            setupRecyclerView(recyclerView, null)
        }

        setSearchView(searchView)
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_list, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as? SearchManager

        if (searchItem != null) {
            setSearchView(searchItem.actionView as SearchView)
        }
        searchView?.setSearchableInfo(searchManager?.getSearchableInfo(activity?.componentName))

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setSearchView(searchView: SearchView?) {

        if (this.searchView !== searchView) {
            this.searchView = searchView
            searchView?.let {
                presenter.setSearchTermObservable(RxSearchView.queryTextChanges(searchView).toFlowable(BackpressureStrategy.LATEST).map { it.toString() })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearDisposables()
    }

    override fun onStart() {
        super.onStart()
        presenter.takeView(this)
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
                recyclerView.adapter = MovieListAdapter(list) { presenter?.favouriteClicked(it, true) }
            }
        }
    }

    override fun displayMovies(movies: List<Movie>) {
        setupRecyclerView(recyclerView, movies)
    }

    override fun displayError(message: String) {

        context?.let {
            //show nice message
            val alertDialogBuilder = AlertDialog.Builder(it)
            alertDialogBuilder.setTitle("Error")
            alertDialogBuilder.setMessage("Something went wrong")
            alertDialogBuilder.create().show()
        }

        Timber.d("Error: $message")
    }
}
