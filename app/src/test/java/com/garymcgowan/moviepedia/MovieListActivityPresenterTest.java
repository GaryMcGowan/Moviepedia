package com.garymcgowan.moviepedia;

import com.garymcgowan.moviepedia.model.Movie;
import com.garymcgowan.moviepedia.model.MovieRepository;
import com.garymcgowan.moviepedia.view.MovieListActivityPresenter;
import com.garymcgowan.moviepedia.view.MovieListActivityView;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by garymcgowan on 08/05/2017.
 */
public class MovieListActivityPresenterTest {

    @Test
    public void shouldPassMoviesToView() {
        //given
        MovieListActivityView view = new MockView();
        MovieRepository movieRepository = new MockMoviesRepository(new Movie(), new Movie(), new Movie());
        
        //when
        MovieListActivityPresenter presenter = new MovieListActivityPresenter(view, movieRepository);
        presenter.loadMovies();

        //then
        Assert.assertEquals(3,((MockView)view).count );
    }


    @Test
    public void shouldPassMoviesToViewNull() {
        //given
        MovieListActivityView view = new MockView();
        MovieRepository movieRepository = new MockMoviesRepository((List<Movie>)null);

        //when
        MovieListActivityPresenter presenter = new MovieListActivityPresenter(view, movieRepository);
        presenter.loadMovies();

        //then
        Assert.assertEquals(0,((MockView)view).count );
    }

    @Test
    public void shouldPassMoviesToViewEmpty() {
        //given
        MovieListActivityView view = new MockView();
        MovieRepository movieRepository = new MockMoviesRepository(Collections.emptyList());

        //when
        MovieListActivityPresenter presenter = new MovieListActivityPresenter(view, movieRepository);
        presenter.loadMovies();

        //then
        Assert.assertEquals(0,((MockView)view).count );
    }

    private class MockView implements MovieListActivityView {
        int count = -1;
        @Override
        public void displayMovies(List<Movie> movies) {
            if(movies == null) {
                count = 0;
            }
            else {
                count = movies.size();
            }
        }
    }

    private class MockMoviesRepository implements MovieRepository {
        private List<Movie> list;
        public MockMoviesRepository(Movie... list) {
            this.list = Arrays.asList(list);
        }
        public MockMoviesRepository(List<Movie> list) {
            this.list = list;
        }

        @Override
        public List<Movie> getMovieSearch(String search) {
            return list;
        }
    }


}