package com.garymcgowan.moviepedia.network

import com.garymcgowan.moviepedia.model.Movie
import com.google.gson.annotations.SerializedName

class OmdbSearch : OmdbBaseResponse() {

    @SerializedName("Search") var search: List<Movie>? = null
    @SerializedName("totalResults") var totalResults: Int = 0

}
