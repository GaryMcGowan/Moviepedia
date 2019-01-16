package com.garymcgowan.moviepedia.view.search

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MovieListModule {

    @ContributesAndroidInjector()
    abstract fun bindMovieListActivity(): MovieListActivity

    @Binds
    abstract fun bindMovieListPresenter(presenter: MovieListPresenter): MovieListContract.Presenter
}
