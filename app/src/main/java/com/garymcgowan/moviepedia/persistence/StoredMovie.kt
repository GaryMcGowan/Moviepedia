package com.garymcgowan.moviepedia.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.garymcgowan.moviepedia.model.Movie
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favourite_movies")
data class StoredMovie(
        @ColumnInfo() @SerializedName("Title") var title: String? = null,
        @ColumnInfo() @SerializedName("Year") var year: String? = null,
        @ColumnInfo() @PrimaryKey(autoGenerate = false) @SerializedName("imdbID") var imdbID: String,
        @ColumnInfo() @SerializedName("Type") var type: String? = null,
        @ColumnInfo() @SerializedName("Poster") var posterURL: String? = null,
        //Detail fields
        @ColumnInfo() @SerializedName("Rated") var rated: String? = null,
        @ColumnInfo() @SerializedName("Released") var released: String? = null,
        @ColumnInfo() @SerializedName("Runtime") var runtime: String? = null,
        @ColumnInfo() @SerializedName("Genre") var genre: String? = null,
        @ColumnInfo() @SerializedName("Director") var director: String? = null,
        @ColumnInfo() @SerializedName("Writer") var writer: String? = null,
        @ColumnInfo() @SerializedName("Actors") var actors: String? = null,
        @ColumnInfo() @SerializedName("Plot") var plot: String? = null,
        @ColumnInfo() @SerializedName("Language") var language: String? = null,
        @ColumnInfo() @SerializedName("Country") var country: String? = null,
        @ColumnInfo() @SerializedName("Awards") var awards: String? = null,
        @ColumnInfo() @SerializedName("Metascore") var metascore: String? = null,
        @ColumnInfo() @SerializedName("imdbRating") var imdbRating: String? = null,
        @ColumnInfo() @SerializedName("imdbVotes") var imdbVotes: String? = null
) {
//    companion object {
//        fun fromMovie(movie: Movie) }

    constructor(movie: Movie) : this(movie.title, movie.year, movie.imdbID, movie.type, movie.posterURL, movie.rated, movie.released, movie.runtime, movie.genre, movie.director,
            movie.writer, movie.actors, movie.plot, movie.language, movie.country, movie.awards, movie.metascore, movie.imdbRating, movie.imdbVotes)

    fun toMovie() = Movie(title!!, year, imdbID, type, posterURL, rated, released, runtime, genre, director, writer, actors, plot, language, country, awards, metascore, imdbRating, imdbVotes)
}

