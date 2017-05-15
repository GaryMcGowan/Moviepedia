package com.garymcgowan.moviepedia.view;

import com.garymcgowan.moviepedia.model.Movie;

/**
 * Created by garymcgowan on 15/05/2017.
 */

public interface MovieDetailsActivityView {

    void displayMovieDetails(Movie movieDetails);

    void displayError(String message);
}
