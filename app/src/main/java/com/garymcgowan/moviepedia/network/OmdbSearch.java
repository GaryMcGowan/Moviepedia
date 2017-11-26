package com.garymcgowan.moviepedia.network;

import com.garymcgowan.moviepedia.model.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gary on 2016/10/26.
 */

public class OmdbSearch extends OmdbBaseResponse {


    @SerializedName("Search") public List<Movie> search;
    public int totalResults;

}
