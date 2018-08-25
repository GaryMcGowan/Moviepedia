package com.garymcgowan.moviepedia.dagger

import android.app.Application
import android.support.annotation.VisibleForTesting
import android.util.Log
import com.garymcgowan.moviepedia.network.OmdbMoviesAPI
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
class NetworkModule(internal var baseUrl: String) {

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
    fun providePicasso(app: Application, client: OkHttpClient): Picasso {
        return Picasso.Builder(app)
                .downloader(OkHttp3Downloader(client))
                .listener { picasso, uri, e -> Log.e("Twitter", e.toString() + " Failed to load image: " + uri) }
                .build()
    }
}
