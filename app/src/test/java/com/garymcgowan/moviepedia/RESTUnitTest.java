package com.garymcgowan.moviepedia;

import com.garymcgowan.moviepedia.dagger.NetworkModule;
import com.garymcgowan.moviepedia.model.Movie;
import com.garymcgowan.moviepedia.network.OmdbMoviesAPI;
import com.garymcgowan.moviepedia.network.OmdbSearch;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * ensure dagger is setup correctly and ensure api is available
 */


public class RESTUnitTest {

    private static final String mBaseUrl = "http://www.omdbapi.com";

    static OmdbMoviesAPI api;

    @BeforeClass
    public static void setUp() {
        NetworkModule network = new NetworkModule(mBaseUrl);
        api = network.provideObservableMoviesApi(
                network.provideRetrofit(network.provideOkHttpClient(), network.provideGson())
        );


//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
//        client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

    }

    @Test
    public void movieTitles() throws Exception {
        String testMovieName = "taken";
        List<Movie> movies = api.getObservableMoviesSearch(testMovieName, null, null, null, null, null, null)
                .blockingFirst().getSearch();
        System.out.println("Movies " + movies);

        assertNotNull(movies);

        boolean containsName = movies.toString().toUpperCase().contains(testMovieName.toUpperCase());
        assertTrue(containsName);
    }

    @Test
    public void movieTitlesFailure() throws Exception {
        String testMovieName = "zzzzzzzzzzzzzzzzzzzzz";
        OmdbSearch movies = api.getObservableMoviesSearch(testMovieName, null, null, null, null, null, null)
                .blockingFirst();
        System.out.println("Movies " + movies);

        assertNotNull(movies);
        assertNull(movies.getSearch());
    }

    @Test
    public void getMovieSuccess() throws Exception {
        String id = "tt0936501";
        Movie movie = api.getObservableMovie(id, null, null, null, null, null, null, null, null)
                .blockingGet();
        System.out.println("Movie " + movie);

        assertNotNull(movie);
        assertEquals(id, movie.getImdbID());
    }

    @Test
    public void getMovieFailure() throws Exception {
        String id = "ttbadid";
        Movie movie = api.getObservableMovie(id, null, null, null, null, null, null, null, null)
                .blockingGet();
        System.out.println("Movie " + movie.toString());
        assertNull(movie.getImdbID());
    }
}