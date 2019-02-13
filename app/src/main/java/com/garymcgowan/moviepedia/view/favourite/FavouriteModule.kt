package com.garymcgowan.moviepedia.view.favourite

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FavouriteModule {

    @ContributesAndroidInjector
    abstract fun bindFavouriteFragment(): FavouriteFragment

}
