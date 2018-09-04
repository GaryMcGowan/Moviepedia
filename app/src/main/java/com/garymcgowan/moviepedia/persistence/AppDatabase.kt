package com.garymcgowan.moviepedia.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [(StoredMovie::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun storedMovieDao(): StoredMovieDao
}
