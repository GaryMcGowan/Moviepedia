package com.garymcgowan.moviepedia.dagger;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
