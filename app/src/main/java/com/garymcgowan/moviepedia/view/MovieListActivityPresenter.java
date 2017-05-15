package com.garymcgowan.moviepedia.view;

import com.garymcgowan.moviepedia.model.Movie;
import com.garymcgowan.moviepedia.model.MovieRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import timber.log.Timber;

/**
 * Created by garymcgowan on 08/05/2017.
 */

public class MovieListActivityPresenter extends BasePresenter {

    private static final long QUERY_UPDATE_DELAY_MILLIS = 400;


    private MovieListActivityView view;
    private MovieRepository movieRepository;
    private Scheduler mainScheduler;

    public MovieListActivityPresenter(MovieListActivityView view, MovieRepository movieRepository, Scheduler mainScheduler) {
        this.view = view;
        this.movieRepository = movieRepository;
        this.mainScheduler = mainScheduler;
    }

//    public void loadMovies(String search) {
//        try {
//            view.displayMovies(movieRepository.getMovieSearch(search));
//        } catch (Exception e) {
//            e.printStackTrace();
//            view.displayError();
//        }
//    }

    public void setSearchTermObservable(Flowable<String> flowable) {
        addDisposable(flowable.filter(s -> s.length() > 1)
                .subscribeOn(mainScheduler)
                .debounce(QUERY_UPDATE_DELAY_MILLIS, TimeUnit.MILLISECONDS)
                .flatMap(search -> movieRepository.getMovieSearch(search)
                        .subscribeOn(Schedulers.io())
                )
                .observeOn(mainScheduler)
                .subscribeWith(new DisposableSubscriber<List<Movie>>() {
                    @Override
                    public void onNext(List<Movie> search) {
                        if (search == null)
                            view.displayError("movies null");
                            //Snackbar.make(searchView, R.string.connection_failure, Snackbar.LENGTH_LONG).show();
                        else
                            view.displayMovies(search);
                        //setupRecyclerView(recyclerView, search.search);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Timber.d("onError: " + t);
                        Timber.e(t);
                        view.displayError("onError: " + t.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
