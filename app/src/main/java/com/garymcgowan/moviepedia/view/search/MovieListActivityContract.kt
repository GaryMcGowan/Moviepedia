package com.garymcgowan.moviepedia.view.search

import com.garymcgowan.moviepedia.model.Movie

interface MovieListActivityContract {

    interface View {
        fun displayMovies(movies: List<Movie>)
        fun displayError(message: String)
    }

}
