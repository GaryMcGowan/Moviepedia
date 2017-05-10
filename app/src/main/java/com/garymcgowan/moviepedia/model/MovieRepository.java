package com.garymcgowan.moviepedia.model;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by garymcgowan on 08/05/2017.
 */

public interface MovieRepository {

    Flowable<List<Movie>> getMovieSearch(String search);
}
