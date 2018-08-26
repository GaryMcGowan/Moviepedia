package com.garymcgowan.moviepedia.view.details

import com.garymcgowan.moviepedia.model.Movie


interface MovieDetailsActivityContract {

    interface View {
        fun displayMovieDetails(movieDetails: Movie)

        fun displayError(message: String)
    }
}
