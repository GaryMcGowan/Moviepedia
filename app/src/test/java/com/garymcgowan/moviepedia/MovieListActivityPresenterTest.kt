package com.garymcgowan.moviepedia

import com.garymcgowan.moviepedia.model.Movie
import com.garymcgowan.moviepedia.model.MovieRepository
import com.garymcgowan.moviepedia.view.search.MovieListActivityContract
import com.garymcgowan.moviepedia.view.search.MovieListActivityPresenter
import io.reactivex.Flowable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

class MovieListActivityPresenterTest {

//    @Rule var mockitoRule = MockitoJUnit.rule()

    @Mock lateinit var view: MovieListActivityContract.View
    @Mock lateinit var movieRepository: MovieRepository
    lateinit var presenter: MovieListActivityPresenter

    private val THREE_MOVIES = Arrays.asList(Movie(), Movie(), Movie())


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MovieListActivityPresenter(view, movieRepository, Schedulers.trampoline())
        RxJavaPlugins.setIoSchedulerHandler { scheduler -> Schedulers.trampoline() }
    }

    @After
    fun cleanUp() {
        RxJavaPlugins.reset()
    }

    @Test
    fun shouldPassMoviesToView() {

        `when`(movieRepository.getMovieSearch("123")).thenReturn(Flowable.just(THREE_MOVIES))

        presenter.setSearchTermObservable(Flowable.just("123"))

        verify(view).displayMovies(THREE_MOVIES)
    }


    @Test
    fun shouldPassMoviesToViewNull() {
        `when`(movieRepository.getMovieSearch("123")).thenReturn(null)

        presenter.setSearchTermObservable(Flowable.just("123"))

        verify(view).displayError(Mockito.anyString())
    }

    @Test
    fun shouldPassMoviesToViewEmpty() {
        `when`(movieRepository.getMovieSearch("123")).thenReturn(Flowable.just<List<Movie>>(emptyList()))

        presenter.setSearchTermObservable(Flowable.just("123"))

        verify(view).displayMovies(emptyList())
    }

    @Test
    fun shouldHandleException() {
        `when`(movieRepository.getMovieSearch("123")).thenThrow(RuntimeException("Something when wrong"))

        presenter.setSearchTermObservable(Flowable.just("123"))

        verify(view).displayError(Mockito.anyString())
    }


    @Test
    fun multipleRequests() {
        `when`(movieRepository.getMovieSearch(Mockito.anyString())).thenReturn(Flowable.just(THREE_MOVIES))

        presenter.setSearchTermObservable(Flowable.just("t", "ta", "tak", "take", "taken"))

        verify(view, times(5)).displayMovies(THREE_MOVIES)
    }
}