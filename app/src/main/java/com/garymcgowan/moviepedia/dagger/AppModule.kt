package com.garymcgowan.moviepedia.dagger

import androidx.room.Room
import android.content.Context
import com.garymcgowan.moviepedia.App
import com.garymcgowan.moviepedia.persistence.AppDatabase
import com.garymcgowan.moviepedia.persistence.StoredMovieDao
import com.garymcgowan.moviepedia.view.details.MovieDetailsModule
import com.garymcgowan.moviepedia.view.favourite.FavouriteModule
import com.garymcgowan.moviepedia.view.search.MovieListModule
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Singleton

@Module(includes = [
    MovieListModule::class,
    FavouriteModule::class,
    MovieDetailsModule::class
])
class AppModule {

    @Provides
    @Singleton
    internal fun providesAppContext(application: App): Context {
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

    @Provides
    @Singleton
    internal fun providesScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
