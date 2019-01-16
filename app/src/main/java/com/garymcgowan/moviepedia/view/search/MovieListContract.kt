package com.garymcgowan.moviepedia.view.search

import com.garymcgowan.moviepedia.model.Movie
import io.reactivex.Flowable
import io.reactivex.annotations.NonNull

interface MovieListContract {

    interface View {
        fun displayMovies(movies: List<Movie>)
        fun displayError(message: String)
    }

    interface Presenter {
        fun setSearchTermObservable(@NonNull flowable: Flowable<String>)
        fun favouriteClicked(favMovie: Movie, isFav: Boolean)
    }


}
