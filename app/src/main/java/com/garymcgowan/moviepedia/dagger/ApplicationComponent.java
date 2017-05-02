package com.garymcgowan.moviepedia.dagger;

import com.garymcgowan.moviepedia.App;
import com.garymcgowan.moviepedia.view.DetailsActivity;
import com.garymcgowan.moviepedia.view.ListActivity;

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

    void inject(ListActivity activity);

}
