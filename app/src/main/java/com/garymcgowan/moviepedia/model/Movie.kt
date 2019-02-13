package com.garymcgowan.moviepedia.model

import com.garymcgowan.moviepedia.network.OmdbBaseResponse
import com.google.gson.annotations.SerializedName

data class Movie(
    //Summary fields (returned by search)
        @SerializedName("Title") var title: String = "",
        @SerializedName("Year") var year: String? = null,
        @SerializedName("imdbID") var imdbID: String,
        @SerializedName("Type") var type: String? = null,
        @SerializedName("Poster") var posterURL: String? = null,
    //Detail fields
        @SerializedName("Rated") var rated: String? = null,
        @SerializedName("Released") var released: String? = null,
        @SerializedName("Runtime") var runtime: String? = null,
        @SerializedName("Genre") var genre: String? = null,
        @SerializedName("Director") var director: String? = null,
        @SerializedName("Writer") var writer: String? = null,
        @SerializedName("Actors") var actors: String? = null,
        @SerializedName("Plot") var plot: String? = null,
        @SerializedName("Language") var language: String? = null,
        @SerializedName("Country") var country: String? = null,
        @SerializedName("Awards") var awards: String? = null,
        @SerializedName("Metascore") var metascore: String? = null,
        @SerializedName("imdbRating") var imdbRating: String? = null,
        @SerializedName("imdbVotes") var imdbVotes: String? = null

) : OmdbBaseResponse() {

    fun titleYear() = "$title ($year)"

    override fun toString(): String {
        return title ?: ""
    }


}
