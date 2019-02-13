package com.garymcgowan.moviepedia.view.details

import com.garymcgowan.moviepedia.model.MovieRepository
import com.garymcgowan.moviepedia.view.BasePresenter
import com.garymcgowan.moviepedia.view.search.MovieListContract
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MovieDetailsPresenter @Inject constructor(
        private val movieRepository: MovieRepository,
        private val mainScheduler: Scheduler
) : BasePresenter<MovieDetailsContract.View>(), MovieDetailsContract.Presenter {

    //private var view: MovieDetailsContract.View? = null

//    fun takeView(view: MovieDetailsContract.View) {
//        this.view = view
//    }

    override fun loadMovieDetails(movieId: String) {
        addDisposable(movieRepository.getMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .subscribe({ movie ->
                    if (movie == null) {
                        getView()?.displayError("details not found")
                    } else {
                        getView()?.displayMovieDetails(movie)
                    }
                }, { error ->
                    getView()?.displayError("onError: " + error.localizedMessage)
                })
        )
    }
}
