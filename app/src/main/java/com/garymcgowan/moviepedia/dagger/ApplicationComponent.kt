package com.garymcgowan.moviepedia.dagger

import com.garymcgowan.moviepedia.App
import com.garymcgowan.moviepedia.view.details.MovieDetailsActivity
import com.garymcgowan.moviepedia.view.search.MovieListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, NetworkModule::class))
interface ApplicationComponent {
    fun inject(application: App)

    fun inject(fragment: MovieDetailsActivity)

    fun inject(activity: MovieListActivity)


}
