package com.garymcgowan.moviepedia;

import com.garymcgowan.moviepedia.model.Movie;
import com.garymcgowan.moviepedia.model.MovieRepository;
import com.garymcgowan.moviepedia.view.MovieListActivityPresenter;
import com.garymcgowan.moviepedia.view.MovieListActivityView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;

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


    @Before
    public void setUp() {
        presenter = new MovieListActivityPresenter(view, movieRepository);
    }

    @Test
    public void shouldPassMoviesToView() {
        //given
        Flowable<List<Movie>> flowableMovies = Flowable.just(Arrays.asList(new Movie(), new Movie(), new Movie()));

        //when
        when(movieRepository.getMovieSearch("")).thenReturn(flowableMovies);
        presenter.loadMovies("");

        //then
        verify(view).displayMovies(flowableMovies);
    }


    @Test
    public void shouldPassMoviesToViewNull() {

        //given
        Flowable<List<Movie>> flowableMovies = null;

        //when
        when(movieRepository.getMovieSearch("")).thenReturn(flowableMovies);
        presenter.loadMovies("");

        //then
        verify(view).displayMovies(flowableMovies);

    }

    @Test
    public void shouldPassMoviesToViewEmpty() {
        //given
        Flowable<List<Movie>> flowableMovies = Flowable.just(Collections.EMPTY_LIST);

        //when
        when(movieRepository.getMovieSearch("")).thenReturn(flowableMovies);
        presenter.loadMovies("");

        //then
        verify(view).displayMovies(flowableMovies);
    }

    @Test
    public void shouldHandleException() {
        when(movieRepository.getMovieSearch("")).thenThrow(new RuntimeException("Something when wrong"));

        presenter.loadMovies("");

        verify(view).displayError();
    }
}