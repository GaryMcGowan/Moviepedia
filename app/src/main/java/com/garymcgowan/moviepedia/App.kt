package com.garymcgowan.moviepedia

import android.app.Application

import com.garymcgowan.moviepedia.dagger.ApplicationComponent
import com.garymcgowan.moviepedia.dagger.ApplicationModule
import com.garymcgowan.moviepedia.dagger.DaggerApplicationComponent
import com.garymcgowan.moviepedia.dagger.NetworkModule

import timber.log.Timber

class App : Application() {

    companion object {
        const val mBaseUrl = "http://www.omdbapi.com"
    }

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = initDagger(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

    private fun initDagger(app: App): ApplicationComponent =
            DaggerApplicationComponent.builder()
                    .networkModule(NetworkModule(mBaseUrl))
                    .applicationModule(ApplicationModule(app))
                    .build()
}
