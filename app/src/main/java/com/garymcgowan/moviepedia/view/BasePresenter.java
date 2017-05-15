package com.garymcgowan.moviepedia.view;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by garymcgowan on 13/05/2017.
 */

public class BasePresenter {

    private CompositeDisposable disposables = new CompositeDisposable();


    protected void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    protected void clearDisposables() {
        disposables.clear();
    }
}