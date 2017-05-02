package com.garymcgowan.moviepedia;

import android.app.Application;

import com.garymcgowan.moviepedia.dagger.ApplicationComponent;
import com.garymcgowan.moviepedia.dagger.ApplicationModule;
import com.garymcgowan.moviepedia.dagger.DaggerApplicationComponent;
import com.garymcgowan.moviepedia.dagger.NetworkModule;

/**
 * Created by Gary on 2016/10/31.
 */

public class App extends Application {

    public static final String mBaseUrl = "http://www.omdbapi.com";

    private static App singleton;


    public static App getInstance() {
        return singleton;
    }


    ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        component = DaggerApplicationComponent.builder()
                .networkModule(new NetworkModule(mBaseUrl))
                .applicationModule(new ApplicationModule(this))
                .build();
        component.inject(this);

    }

    public static ApplicationComponent getApplicationComponent() {
        return singleton.component;
    }
}
