package com.garymcgowan.moviepedia.network;

import com.garymcgowan.moviepedia.model.Movie;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Gary on 2016/11/09.
 */

/**
 * api: http://www.omdbapi.com/
 */
public interface OmdbMoviesAPI {

    String API_KEY = "955c021d"; // TODO this can be extracted to gradle property

    String TYPE_MOVIE = "movie";
    String TYPE_SERIES = "series";
    String TYPE_EPISODE = "episode";

    String PLOT_SHORT = "short";
    String PLOT_FULL = "full";

    String RESPONSE_JSON = "json";
    String RESPONSE_XML = "xml";

    String TRUE = "true";
    String FALSE = "false";

    @GET("/?apikey=" + API_KEY)
    Flowable<OmdbSearch> getObservableMoviesSearch(
            @Query("s") String search,
            @Query("type") String type,// movie, series, episode
            @Query("y") Integer year,
            @Query("r") Boolean responseType,//json, xml
            @Query("page") Integer page,
            @Query("callback") String callback,
            @Query("v") Integer apiVersion);

    @GET("/?apikey=" + API_KEY)
    Single<Movie> getObservableMovie(
            @Query("i") String id,
            @Query("t") String title,
            @Query("type") String type,// movie, series, episode
            @Query("y") Integer year,
            @Query("plot") String plotLength,//short, full
            @Query("r") Boolean responseType,//json, xml
            @Query("tomatoes") Boolean tomatoes,
            @Query("callback") String callback,
            @Query("v") Integer apiVersion);

}
