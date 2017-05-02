package com.garymcgowan.moviepedia.dagger;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gary on 2016/11/02.
 */

@Module
public class ApplicationModule {
    Application mApplication;

    public ApplicationModule(Application app) {
        mApplication = app;
    }

    @Provides
    @Singleton
    Application providesApp() {
        return mApplication;
    }

    @Provides
    @Singleton
    Context providesAppContext(Application application) {
        return application.getApplicationContext();
    }

}
