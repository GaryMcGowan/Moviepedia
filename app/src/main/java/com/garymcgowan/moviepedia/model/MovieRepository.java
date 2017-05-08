package com.garymcgowan.moviepedia.model;

import java.util.List;

/**
 * Created by garymcgowan on 08/05/2017.
 */

public interface MovieRepository {

    List<Movie> getMovieSearch(String search);
}
