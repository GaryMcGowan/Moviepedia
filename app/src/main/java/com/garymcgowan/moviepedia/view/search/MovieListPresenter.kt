package com.garymcgowan.moviepedia.view.search

import com.garymcgowan.moviepedia.model.Movie
import com.garymcgowan.moviepedia.model.MovieRepository
import com.garymcgowan.moviepedia.persistence.StoredMovie
import com.garymcgowan.moviepedia.persistence.StoredMovieDao
import com.garymcgowan.moviepedia.view.BasePresenter
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.annotations.NonNull
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MovieListPresenter @Inject constructor(private val movieRepository: MovieRepository, private val storedMovieDao: StoredMovieDao, private val mainScheduler: Scheduler) : BasePresenter(), MovieListContract.Presenter {

    companion object {
        private val QUERY_UPDATE_DELAY_MILLIS: Long = 400
    }

    private var view: MovieListContract.View? = null

    fun takeView(view: MovieListContract.View) {
        this.view = view
    }

    override fun setSearchTermObservable(@NonNull flowable: Flowable<String>) {
        addDisposable(flowable
                //.filter(s -> s.length() > 1)
                .subscribeOn(mainScheduler)
                //.debounce(QUERY_UPDATE_DELAY_MILLIS, TimeUnit.MILLISECONDS)
                .flatMap { search ->
                    movieRepository.getMovieSearch(search).subscribeOn(Schedulers.io())
                }.map { list ->
                    list.toMutableList().apply { addAll(storedMovieDao.getAll().map { it.toMovie() }) }
                }.observeOn(mainScheduler).subscribe({ search ->
                    if (search == null) view?.displayError("movies null")
                    else view?.displayMovies(search)
                }, { error ->
                    Timber.d("onError: $error")
                    Timber.e(error)
                    view?.displayError("onError: " + error.localizedMessage)
                }

                ))
    }

    override fun favouriteClicked(fav: Movie, isFav: Boolean) {

        //TODO: imrpove this so the UI is updated
        addDisposable(Completable.fromAction {
            if (isFav) storedMovieDao.insertAll(StoredMovie(fav))
            else storedMovieDao.deleteAll(StoredMovie(fav))
        }.subscribeOn(Schedulers.io()).subscribe())
    }


}
