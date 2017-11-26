package com.garymcgowan.moviepedia.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gary on 2016/10/27.
 */

public class OmdbBaseResponse {

    //error fields
//    {
//        "Response": "False",
//            "Error": "Movie not found!"
//    }

    @SerializedName("Response") boolean response;
    @SerializedName("Error") String error;


    public boolean isResponse() {
        return response;
    }

    public String getError() {
        return error;
    }
}
