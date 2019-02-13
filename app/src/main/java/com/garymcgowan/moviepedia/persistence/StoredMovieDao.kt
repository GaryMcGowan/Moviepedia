package com.garymcgowan.moviepedia.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StoredMovieDao {

    @Query("SELECT * FROM favourite_movies")
    fun getAll(): List<StoredMovie>

    @Insert
    fun insertAll(vararg movies: StoredMovie)

    @Delete
    fun deleteAll(vararg movies: StoredMovie)
}
