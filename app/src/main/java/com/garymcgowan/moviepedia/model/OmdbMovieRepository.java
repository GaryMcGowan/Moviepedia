package com.garymcgowan.moviepedia.model;

import com.garymcgowan.moviepedia.network.MoviesAPI;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by garymcgowan on 10/05/2017.
 */

@Singleton
public class OmdbMovieRepository implements MovieRepository {

    MoviesAPI moviesAPI;

    @Inject
    public OmdbMovieRepository(MoviesAPI moviesAPI) {
        this.moviesAPI = moviesAPI;
    }

    @Override
    public Flowable<List<Movie>> getMovieSearch(String search) {
        return moviesAPI.getObservableMoviesSearch(search,
                null, null, null, null, null,
                null)
                .map(searchObject -> {
                    if (searchObject == null || searchObject.search == null || !searchObject.isResponse()) {
                        return Collections.emptyList();
                    }
                    return searchObject.search;
                });
    }

    @Override
    public Single<Movie> getMovieDetails(String movieId) {
        return moviesAPI.getObservableMovie(movieId, null, null, null, null, null, null, null, null)
                .map(movie -> {
                    if (movie.isResponse()) {
                        return movie;
                    } else {
                        throw new Exception(movie.error);
                    }
                });
    }
}
