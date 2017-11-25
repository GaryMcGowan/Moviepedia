package com.garymcgowan.moviepedia.dagger;

import android.app.Application;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.garymcgowan.moviepedia.network.MoviesAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gary on 2016/11/02.
 */

@Module
public class NetworkModule {

    String baseUrl;

    public NetworkModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @VisibleForTesting
    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder()
                .create();
    }


    @VisibleForTesting
    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        return client;
    }


    @VisibleForTesting
    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @VisibleForTesting
    @Provides
    @Singleton
    public MoviesAPI provideObservableMoviesApi(Retrofit retrofit) {
        return retrofit.create(MoviesAPI.class);

    }


    @VisibleForTesting
    @Provides
    @Singleton
    public Picasso providePicasso(Application app, OkHttpClient client) {
        return new Picasso.Builder(app)
                .downloader(new OkHttp3Downloader(client))
                .listener((picasso, uri, e) -> Log.e("Twitter", e + " Failed to load image: " + uri))
                .build();
    }
}
