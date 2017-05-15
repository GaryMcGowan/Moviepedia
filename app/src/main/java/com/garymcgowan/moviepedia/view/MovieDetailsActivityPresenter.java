package com.garymcgowan.moviepedia.view;

import com.garymcgowan.moviepedia.model.Movie;
import com.garymcgowan.moviepedia.model.MovieRepository;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by garymcgowan on 15/05/2017.
 */

public class MovieDetailsActivityPresenter extends BasePresenter {

    private MovieDetailsActivityView view;
    private MovieRepository movieRepository;
    private Scheduler mainScheduler;

    public MovieDetailsActivityPresenter(MovieDetailsActivityView view, MovieRepository movieRepository, Scheduler mainScheduler) {
        this.view = view;
        this.movieRepository = movieRepository;
        this.mainScheduler = mainScheduler;
    }

    public void loadMovieDetails(String movieId) {
        addDisposable(movieRepository.getMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .subscribeWith(new DisposableSingleObserver<Movie>() {
                    @Override
                    public void onSuccess(@NonNull Movie movie) {
                        if (movie == null) {
                            view.displayError("details not found");
                        } else {
                            view.displayMovieDetails(movie);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.displayError("onError: " + e.getLocalizedMessage());
                    }
                }));
    }
}
