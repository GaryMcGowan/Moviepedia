package com.garymcgowan.moviepedia.dagger;

import com.garymcgowan.moviepedia.App;
import com.garymcgowan.moviepedia.model.MovieRepository;
import com.garymcgowan.moviepedia.view.DetailsActivity;
import com.garymcgowan.moviepedia.view.MovieListActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Gary on 2016/11/02.
 */

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {
    void inject(App application);

    void inject(DetailsActivity fragment);

    void inject(MovieListActivity activity);

    void inject(MovieRepository movieRepository);

}
