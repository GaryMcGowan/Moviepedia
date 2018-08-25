package com.garymcgowan.moviepedia.network

import com.google.gson.annotations.SerializedName

open class OmdbBaseResponse {

    //error fields
    //    {
    //        "Response": "False",
    //            "Error": "Movie not found!"
    //    }

    @SerializedName("Response") var isResponse: Boolean = false
    @SerializedName("Error") var error: String? = null
}
