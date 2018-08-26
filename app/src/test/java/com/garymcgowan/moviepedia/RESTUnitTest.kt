package com.garymcgowan.moviepedia

import com.garymcgowan.moviepedia.dagger.NetworkModule
import com.garymcgowan.moviepedia.network.OmdbMoviesAPI
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * ensure dagger is setup correctly and ensure api is available
 */


class RESTUnitTest {

    companion object {
        const val mBaseUrl = "http://www.omdbapi.com"
    }

    lateinit var api: OmdbMoviesAPI

    @Before
    fun setUp() {
        val network = NetworkModule(mBaseUrl)
        api = network.provideObservableMoviesApi(
                network.provideRetrofit(network.provideOkHttpClient(), network.provideGson())
        )


        //        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        //        client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

    }

    @Test
    @Throws(Exception::class)
    fun movieTitles() {
        val testMovieName = "taken"
        val movies = api.getObservableMoviesSearch(testMovieName, null, null, null, null, null, null)
                .blockingFirst().search
        println("Movies " + movies!!)

        assertNotNull(movies)

        val containsName = movies.toString().toUpperCase().contains(testMovieName.toUpperCase())
        assertTrue(containsName)
    }

    @Test
    @Throws(Exception::class)
    fun movieTitlesFailure() {
        val testMovieName = "zzzzzzzzzzzzzzzzzzzzz"
        val movies = api.getObservableMoviesSearch(testMovieName, null, null, null, null, null, null)
                .blockingFirst()
        println("Movies $movies")

        assertNotNull(movies)
        assertNull(movies.search)
    }

    @Test
    @Throws(Exception::class)
    fun getMovieSuccess() {
        val id = "tt0936501"
        val movie = api.getObservableMovie(id, null, null, null, null, null, null, null, null)
                .blockingGet()
        println("Movie $movie")

        assertNotNull(movie)
        assertEquals(id, movie.imdbID)
    }

    @Test
    @Throws(Exception::class)
    fun getMovieFailure() {
        val id = "ttbadid"
        val movie = api.getObservableMovie(id, null, null, null, null, null, null, null, null)
                .blockingGet()
        println("Movie " + movie.toString())
        assertNull(movie.imdbID)
    }
}