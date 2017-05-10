package com.garymcgowan.moviepedia.view;

import com.garymcgowan.moviepedia.model.Movie;

import java.util.List;

import io.reactivex.Flowable;

public interface MovieListActivityView {

    void displayMovies(Flowable<List<Movie>> movies);

    void displayError();
}
