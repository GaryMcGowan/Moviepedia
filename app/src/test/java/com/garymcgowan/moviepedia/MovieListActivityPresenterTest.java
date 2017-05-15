package com.garymcgowan.moviepedia;

import com.garymcgowan.moviepedia.model.Movie;
import com.garymcgowan.moviepedia.model.MovieRepository;
import com.garymcgowan.moviepedia.view.MovieListActivityPresenter;
import com.garymcgowan.moviepedia.view.MovieListActivityView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by garymcgowan on 08/05/2017.
 */
public class MovieListActivityPresenterTest {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock MovieListActivityView view;
    @Mock MovieRepository movieRepository;
    MovieListActivityPresenter presenter;

    private final List<Movie> THREE_MOVIES = Arrays.asList(new Movie(), new Movie(), new Movie());


    @Before
    public void setUp() {
        presenter = new MovieListActivityPresenter(view, movieRepository, Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @After
    public void cleanUp() {
        RxJavaPlugins.reset();
    }

    @Test
    public void shouldPassMoviesToView() {

        when(movieRepository.getMovieSearch("123")).thenReturn(Flowable.just(THREE_MOVIES));

        presenter.setSearchTermObservable(Flowable.just("123"));

        verify(view).displayMovies(THREE_MOVIES);
    }


    @Test
    public void shouldPassMoviesToViewNull() {
        when(movieRepository.getMovieSearch("123")).thenReturn(null);

        presenter.setSearchTermObservable(Flowable.just("123"));

        verify(view).displayError(Mockito.anyString());
    }

    @Test
    public void shouldPassMoviesToViewEmpty() {
        when(movieRepository.getMovieSearch("123")).thenReturn(Flowable.just(Collections.EMPTY_LIST));

        presenter.setSearchTermObservable(Flowable.just("123"));

        verify(view).displayMovies(Collections.EMPTY_LIST);
    }

    @Test
    public void shouldHandleException() {
        when(movieRepository.getMovieSearch("123")).thenThrow(new RuntimeException("Something when wrong"));

        presenter.setSearchTermObservable(Flowable.just("123"));

        verify(view).displayError(Mockito.anyString());
    }


    @Test
    public void multipleRequests() {
        when(movieRepository.getMovieSearch(Mockito.anyString())).thenReturn(Flowable.just(THREE_MOVIES));

        presenter.setSearchTermObservable(Flowable.just("t", "ta", "tak", "take", "taken"));

        verify(view, times(5)).displayMovies(THREE_MOVIES);
    }
}