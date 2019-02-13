package com.garymcgowan.moviepedia.persistence

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StoredMovie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun storedMovieDao(): StoredMovieDao
}
