package com.garymcgowan.moviepedia;

import com.garymcgowan.moviepedia.model.Movie;
import com.garymcgowan.moviepedia.model.Search;
import com.garymcgowan.moviepedia.network.MoviesAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


public class RESTUnitTest {

    public static final String mBaseUrl = "http://www.omdbapi.com";

    static OkHttpClient client;
    static Gson gson;
    static Retrofit retrofit;
    static MoviesAPI moviesAPI;

    @BeforeClass
    public static void setUp() {
        gson = new GsonBuilder().create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        moviesAPI = retrofit.create(MoviesAPI.class);
    }

    @Test
    public void movieTitles() throws Exception {
        String testMovieName = "taken";
        List<Movie> movies = moviesAPI.getObservableMoviesSearch(testMovieName, null, null, null, null, null, null).toBlocking().first().search;
        System.out.println("Movies " + movies);

        assertNotNull(movies);

        boolean containsName = movies.toString().toUpperCase().contains(testMovieName.toUpperCase());
        assertTrue(containsName);
    }

    @Test
    public void movieTitlesFailure() throws Exception {
        String testMovieName = "zzzzzzzzzzzzzzzzzzzzz";
        Search movies = moviesAPI.getObservableMoviesSearch(testMovieName, null, null, null, null, null, null).toBlocking().first();
        System.out.println("Movies " + movies);

        assertNotNull(movies);
        assertNull(movies.search);
    }

    @Test
    public void getMovieSuccess() throws Exception {
        String id = "tt0936501";
        Movie movie = moviesAPI.getObservableMovie(id, null, null, null, null, null, null, null, null).toBlocking().first();
        System.out.println("Movie " + movie);

        assertNotNull(movie);
        assertEquals(id, movie.getImdbID());
    }

    @Test
    public void getMovieFailure() throws Exception {
        String id = "ttbadid";
        Movie movie = moviesAPI.getObservableMovie(id, null, null, null, null, null, null, null, null).toBlocking().first();
        System.out.println("Movie " + movie.toString());
        assertNull(movie.getImdbID());
    }
}