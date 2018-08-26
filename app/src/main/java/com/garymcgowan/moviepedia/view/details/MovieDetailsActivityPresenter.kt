package com.garymcgowan.moviepedia.view.details

import com.garymcgowan.moviepedia.model.MovieRepository
import com.garymcgowan.moviepedia.view.BasePresenter
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


class MovieDetailsActivityPresenter(
        private val view: MovieDetailsActivityContract.View,
        private val movieRepository: MovieRepository,
        private val mainScheduler: Scheduler
) : BasePresenter() {

    fun loadMovieDetails(movieId: String) {
        addDisposable(movieRepository.getMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .subscribe({ movie ->
                    if (movie == null) {
                        view.displayError("details not found")
                    } else {
                        view.displayMovieDetails(movie)
                    }
                }, { error ->
                    view.displayError("onError: " + error.localizedMessage)
                })
        )
    }
}
