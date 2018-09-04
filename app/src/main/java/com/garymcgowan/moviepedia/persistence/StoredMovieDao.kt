package com.garymcgowan.moviepedia.persistence

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface StoredMovieDao {

    @Query("SELECT * FROM favourite_movies")
    fun getAll(): List<StoredMovie>

    @Insert
    fun insertAll(vararg movies: StoredMovie)

    @Delete
    fun deleteAll(vararg movies: StoredMovie)
}
