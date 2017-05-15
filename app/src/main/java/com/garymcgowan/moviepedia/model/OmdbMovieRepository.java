package com.garymcgowan.moviepedia.model;

import com.garymcgowan.moviepedia.network.MoviesAPI;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by garymcgowan on 10/05/2017.
 */

public class OmdbMovieRepository implements MovieRepository {

    MoviesAPI moviesAPI;

    public OmdbMovieRepository(MoviesAPI moviesAPI) {
        this.moviesAPI = moviesAPI;
    }

    @Override
    public Flowable<List<Movie>> getMovieSearch(String search) {
        return moviesAPI.getObservableMoviesSearch(search,
                null, null, null, null, null,
                null).map(searchObject -> searchObject.search);
    }

}
