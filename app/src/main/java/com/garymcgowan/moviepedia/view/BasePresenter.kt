package com.garymcgowan.moviepedia.view

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter {

    private val disposables = CompositeDisposable()


    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun clearDisposables() {
        disposables.clear()
    }
}
