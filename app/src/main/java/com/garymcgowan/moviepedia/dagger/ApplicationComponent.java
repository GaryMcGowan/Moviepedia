package com.garymcgowan.moviepedia.dagger;

import com.garymcgowan.moviepedia.App;
import com.garymcgowan.moviepedia.view.MovieDetailsActivity;
import com.garymcgowan.moviepedia.view.MovieListActivity;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by Gary on 2016/11/02.
 */

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {
    void inject(App application);

    void inject(MovieDetailsActivity fragment);

    void inject(MovieListActivity activity);


    OkHttpClient getOkHttpClient();
}
