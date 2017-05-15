package com.garymcgowan.moviepedia;

import com.garymcgowan.moviepedia.model.Movie;
import com.garymcgowan.moviepedia.model.MovieRepository;
import com.garymcgowan.moviepedia.view.MovieDetailsActivityPresenter;
import com.garymcgowan.moviepedia.view.MovieDetailsActivityView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by garymcgowan on 08/05/2017.
 */
public class MovieDetailsActivityPresenterTest {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock MovieDetailsActivityView view;
    @Mock MovieRepository movieRepository;
    MovieDetailsActivityPresenter presenter;

    private final Movie MOVIE = new Movie();


    @Before
    public void setUp() {
        presenter = new MovieDetailsActivityPresenter(view, movieRepository, Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @After
    public void cleanUp() {
        RxJavaPlugins.reset();
    }

    @Test
    public void shouldPassMovieDetailsToView() {
        when(movieRepository.getMovieDetails("123")).thenReturn(Single.just(MOVIE));

        presenter.loadMovieDetails("123");

        verify(view).displayMovieDetails(MOVIE);
    }

    @Test
    public void loadMovieDetailsDisplayError() {
        when(movieRepository.getMovieDetails("123")).thenReturn(Single.error(new Throwable("error occurred")));

        presenter.loadMovieDetails("123");

        verify(view).displayError(Mockito.anyString());
    }

}