package com.garymcgowan.moviepedia.view;

import com.garymcgowan.moviepedia.model.MovieRepository;

/**
 * Created by garymcgowan on 08/05/2017.
 */

public class MovieListActivityPresenter {

    private MovieListActivityView view;
    private MovieRepository movieRepository;

    public MovieListActivityPresenter(MovieListActivityView view, MovieRepository movieRepository) {
        this.view = view;
        this.movieRepository = movieRepository;
    }

    public void loadMovies(String search) {
        try {
            view.displayMovies(movieRepository.getMovieSearch(search));
        } catch (Exception e) {
            e.printStackTrace();
            view.displayError();
        }
    }
}
