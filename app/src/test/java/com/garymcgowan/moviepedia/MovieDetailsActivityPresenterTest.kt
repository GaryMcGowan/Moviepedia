package com.garymcgowan.moviepedia

import com.garymcgowan.moviepedia.model.Movie
import com.garymcgowan.moviepedia.model.MovieRepository
import com.garymcgowan.moviepedia.view.details.MovieDetailsContract
import com.garymcgowan.moviepedia.view.details.MovieDetailsPresenter
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MovieDetailsActivityPresenterTest {

    //@Rule open var mockitoRule = MockitoJUnit.rule()

    @Mock lateinit var view: MovieDetailsContract.View
    @Mock lateinit var movieRepository: MovieRepository
    lateinit var presenter: MovieDetailsPresenter
    private val MOVIE = Movie()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxJavaPlugins.setIoSchedulerHandler { scheduler -> Schedulers.trampoline() }

        presenter = MovieDetailsPresenter( movieRepository, Schedulers.trampoline())

    }

    @After
    fun cleanUp() {
        RxJavaPlugins.reset()
    }

    @Test
    fun shouldPassMovieDetailsToView() {
        `when`(movieRepository!!.getMovieDetails("123")).thenReturn(Single.just(MOVIE))

        presenter.loadMovieDetails("123")

        verify<MovieDetailsContract.View>(view).displayMovieDetails(MOVIE)
    }

    @Test
    fun loadMovieDetailsDisplayError() {
        `when`(movieRepository!!.getMovieDetails("123")).thenReturn(Single.error(Throwable("error occurred")))

        presenter.loadMovieDetails("123")

        verify<MovieDetailsContract.View>(view).displayError(Mockito.anyString())
    }

}