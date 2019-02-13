package com.garymcgowan.moviepedia.view

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

abstract class BasePresenter<V> {

    private var view: WeakReference<V>? = null

    private val disposables = CompositeDisposable()

    fun takeView(view: V) {
        this.view = WeakReference(view)
    }
    fun dropView() {
        this.view = null
        this.disposables.clear()
    }

    fun getView() = this.view?.get()

    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun clearDisposables() {
        disposables.clear()
    }
}
