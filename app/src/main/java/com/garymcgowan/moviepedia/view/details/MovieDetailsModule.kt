package com.garymcgowan.moviepedia.view.details


import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MovieDetailsModule {

    @ContributesAndroidInjector
    abstract fun bindMovieDetailsFragment(): MovieDetailsFragment

    @Binds
    abstract fun bindMovieListPresenter(presenter: MovieDetailsPresenter): MovieDetailsContract.Presenter

}
