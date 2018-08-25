package com.garymcgowan.moviepedia.network

import com.garymcgowan.moviepedia.model.Movie
import com.garymcgowan.moviepedia.model.MovieRepository
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OmdbMovieRepository @Inject
constructor(internal var moviesAPI: OmdbMoviesAPI) : MovieRepository {

    override fun getMovieSearch(search: String): Flowable<List<Movie>> {
        return moviesAPI.getObservableMoviesSearch(search,
                null, null, OmdbMoviesAPI.RESPONSE_JSON, null, null, null)
                .map { searchObject ->
                    if ( searchObject.search == null || !searchObject.isResponse) {
                        emptyList<Movie>()
                    } else
                    searchObject.search
                }
    }

    override fun getMovieDetails(movieId: String): Single<Movie> {
        return moviesAPI.getObservableMovie(movieId, null, null, null, OmdbMoviesAPI.PLOT_FULL, OmdbMoviesAPI.RESPONSE_JSON, null, null, null)
                .map<Movie> { movie ->
                    if (movie.isResponse) {
                        movie
                    } else {
                        throw Exception(movie.error)
                    }
                }
    }
}
