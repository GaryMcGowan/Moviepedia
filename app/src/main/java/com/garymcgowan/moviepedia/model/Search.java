package com.garymcgowan.moviepedia.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gary on 2016/10/26.
 */

public class Search extends BaseOMDBResponse {


    @SerializedName("Search") public List<Movie> search;
    public int totalResults;

}
