package com.garymcgowan.moviepedia.dagger

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.garymcgowan.moviepedia.persistence.AppDatabase
import com.garymcgowan.moviepedia.persistence.StoredMovieDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(internal var mApplication: Application) {

    @Provides
    @Singleton
    internal fun providesApp(): Application {
        return mApplication
    }

    @Provides
    @Singleton
    internal fun providesAppContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    internal fun providesDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "list-master-db").build()
    }

    @Provides
    @Singleton
    internal fun providesStoredMovieDao(database: AppDatabase): StoredMovieDao {
        return database.storedMovieDao()
    }

}
