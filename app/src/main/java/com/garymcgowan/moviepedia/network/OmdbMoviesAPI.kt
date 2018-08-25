package com.garymcgowan.moviepedia.network

import com.garymcgowan.moviepedia.model.Movie

import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * api: http://www.omdbapi.com/
 */
interface OmdbMoviesAPI {

    @GET("/?apikey=$API_KEY")
    fun getObservableMoviesSearch(
            @Query("s") search: String,
            @Query("type") type: String?, // movie, series, episode
            @Query("y") year: Int?,
            @Query("r") responseType: String, //json, xml
            @Query("page") page: Int?,
            @Query("callback") callback: String?,
            @Query("v") apiVersion: Int?): Flowable<OmdbSearch>

    @GET("/?apikey=$API_KEY")
    fun getObservableMovie(
            @Query("i") id: String,
            @Query("t") title: String?,
            @Query("type") type: String?, // movie, series, episode
            @Query("y") year: Int?,
            @Query("plot") plotLength: String, //short, full
            @Query("r") responseType: String, //json, xml
            @Query("tomatoes") tomatoes: Boolean?,
            @Query("callback") callback: String?,
            @Query("v") apiVersion: Int?): Single<Movie>

    companion object {

        const val API_KEY = "955c021d" // TODO this can be extracted to gradle property

        const val TYPE_MOVIE = "movie"
        const val TYPE_SERIES = "series"
        const val TYPE_EPISODE = "episode"

        const val PLOT_SHORT = "short"
        const val PLOT_FULL = "full"

        const val RESPONSE_JSON = "json"
        const val RESPONSE_XML = "xml"

        const val TRUE = "true"
        const val FALSE = "false"
    }

}
