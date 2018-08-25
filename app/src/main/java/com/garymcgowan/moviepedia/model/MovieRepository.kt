package com.garymcgowan.moviepedia.model

import io.reactivex.Flowable
import io.reactivex.Single

interface MovieRepository {

    fun getMovieSearch(search: String): Flowable<List<Movie>>

    fun getMovieDetails(movieId: String): Single<Movie>
}
