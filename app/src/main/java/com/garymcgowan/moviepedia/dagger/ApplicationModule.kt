package com.garymcgowan.moviepedia.dagger

import android.app.Application
import android.content.Context
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

}
