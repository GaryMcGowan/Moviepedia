package com.garymcgowan.moviepedia.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gary on 2016/10/26.
 */

public class Movie extends BaseOMDBResponse {
    //Summary fields (returned by search)
    @SerializedName("Title")
    String title;
    @SerializedName("Year")
    String year;
    @SerializedName("imdbID")
    String imdbID;
    @SerializedName("Type")
    String type;
    @SerializedName("Poster")
    String posterURL;

    //Detail fields
    @SerializedName("Rated")
    String rated;
    @SerializedName("Released")
    String released;
    @SerializedName("Runtime")
    String runtime;
    @SerializedName("Genre")
    String genre;
    @SerializedName("Director")
    String director;
    @SerializedName("Writer")
    String writer;
    @SerializedName("Actors")
    String actors;
    @SerializedName("Plot")
    String plot;
    @SerializedName("Language")
    String language;
    @SerializedName("Country")
    String country;
    @SerializedName("Awards")
    String awards;
    @SerializedName("Metascore")
    String metascore;
    @SerializedName("imdbRating")
    String imdbRating;
    @SerializedName("imdbVotes")
    String imdbVotes;


    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getType() {
        return type;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public String getRated() {
        return rated;
    }

    public String getReleased() {
        return released;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getWriter() {
        return writer;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public String getAwards() {
        return awards;
    }

    public String getMetascore() {
        return metascore;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public String getTitleYear() {
        return title + " (" + year + ")";
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
