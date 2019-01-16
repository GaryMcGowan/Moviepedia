package com.garymcgowan.moviepedia.view.details

import com.garymcgowan.moviepedia.model.Movie


interface MovieDetailsContract {

    interface Presenter {
        fun loadMovieDetails(movieId: String)
    }

    interface View {
        fun displayMovieDetails(movieDetails: Movie)

        fun displayError(message: String)
    }
}
