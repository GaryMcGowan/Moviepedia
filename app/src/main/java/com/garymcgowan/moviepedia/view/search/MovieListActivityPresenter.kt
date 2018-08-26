package com.garymcgowan.moviepedia.view.search

import com.garymcgowan.moviepedia.model.MovieRepository
import com.garymcgowan.moviepedia.view.BasePresenter
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.annotations.NonNull
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MovieListActivityPresenter(
        private val view: MovieListActivityContract.View,
        private val movieRepository: MovieRepository,
        private val mainScheduler: Scheduler) : BasePresenter() {

    companion object {
        private val QUERY_UPDATE_DELAY_MILLIS: Long = 400
    }

    fun setSearchTermObservable(@NonNull flowable: Flowable<String>) {
        addDisposable(flowable
                //.filter(s -> s.length() > 1)
                .subscribeOn(mainScheduler)
                //.debounce(QUERY_UPDATE_DELAY_MILLIS, TimeUnit.MILLISECONDS)
                .flatMap { search ->
                    movieRepository.getMovieSearch(search).subscribeOn(Schedulers.io())
                }
                .observeOn(mainScheduler)
                .subscribe(
                        { search ->
                            if (search == null) view.displayError("movies null")
                            else view.displayMovies(search)
                        },
                        { error ->
                            Timber.d("onError: $error")
                            Timber.e(error)
                            view.displayError("onError: " + error.localizedMessage)
                        }

                ))
    }


}
