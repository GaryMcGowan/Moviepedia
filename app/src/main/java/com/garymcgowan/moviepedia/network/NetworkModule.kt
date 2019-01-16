package com.garymcgowan.moviepedia.network

import android.support.annotation.VisibleForTesting
import android.util.Log
import com.garymcgowan.moviepedia.App
import com.garymcgowan.moviepedia.model.MovieRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    val baseUrl = "http://www.omdbapi.com"

    @VisibleForTesting
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
                .create()
    }


    @VisibleForTesting
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }


    @VisibleForTesting
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @VisibleForTesting
    @Provides
    @Singleton
    fun provideObservableMoviesApi(retrofit: Retrofit): OmdbMoviesAPI {
        return retrofit.create(OmdbMoviesAPI::class.java)

    }


    @VisibleForTesting
    @Provides
    @Singleton
    fun providePicasso(app: App, client: OkHttpClient): Picasso {
        return Picasso.Builder(app)
                .downloader(OkHttp3Downloader(client))
                .listener { picasso, uri, e -> Log.e("Twitter", e.toString() + " Failed to load image: " + uri) }
                .build()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(repository: OmdbMovieRepository): MovieRepository = repository
}
