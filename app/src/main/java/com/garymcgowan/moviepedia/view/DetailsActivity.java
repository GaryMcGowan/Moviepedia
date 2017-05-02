package com.garymcgowan.moviepedia.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.garymcgowan.moviepedia.App;
import com.garymcgowan.moviepedia.R;
import com.garymcgowan.moviepedia.model.Movie;
import com.garymcgowan.moviepedia.network.MoviesAPI;
import com.squareup.picasso.Picasso;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailsActivity extends AppCompatActivity {

    public static final String ARG_ITEM_ID = "imdb_id";
    public static final String ARG_ITEM_TITLE = "movie_title";

    @Inject
    MoviesAPI moviesAPI;
    Movie movie = null;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.posterImageView)
    ImageView posterImageView;
    @BindView(R.id.ratedTextView)
    TextView ratedTextView;
    @BindView(R.id.runtimeTextView)
    TextView runtimeTextView;
    @BindView(R.id.plotTextView)
    TextView plotTextView;
    @BindView(R.id.ratingTextView)
    TextView ratingTextView;
    @BindView(R.id.directorTextView)
    TextView directorTextView;
    @BindView(R.id.actorsTextView)
    TextView actorsTextView;
    @BindView(R.id.writerTextView)
    TextView writerTextView;
    @BindView(R.id.awardsTextView)
    TextView awardsTextView;

    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        App.getApplicationComponent().inject(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //clear dummy text
        ratedTextView.setText(null);
        runtimeTextView.setText(null);
        plotTextView.setText(null);
        ratingTextView.setText(null);
        directorTextView.setText(null);
        actorsTextView.setText(null);
        writerTextView.setText(null);
        awardsTextView.setText(null);


        String imdbId = getIntent().getStringExtra(ARG_ITEM_ID);
        String title = getIntent().getStringExtra(ARG_ITEM_TITLE);

        if (title != null) {
            collapsingToolbar.setTitle(title);
        }

        if (imdbId != null) {
            subscription = moviesAPI.getObservableMovie(imdbId, null, null, null, null, null, null, null, null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Movie>() {
                        @Override
                        public void onCompleted() {
                            Log.d("MOVIES", "onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("MOVIES", "onError: " + e);
                        }

                        @Override
                        public void onNext(Movie movie) {
                            Log.d("MOVIES", "onNext");
                            setMovie(movie);
                        }
                    });
        }
    }

    public void setMovie(Movie movie) {
        this.movie = movie;

        assert movie != null;

        if (posterImageView != null)
            Picasso.with(getApplicationContext())
                    .load(movie.getPosterURL())
                    .placeholder(R.color.imagePlaceholder)
                    .error(R.color.imagePlaceholder)
                    .into(posterImageView);

        if (toolbar != null) {
            collapsingToolbar.setTitle(movie.getTitleYear());
        }

        if (plotTextView != null) {
            ratedTextView.setText(movie.getRated());
            runtimeTextView.setText(movie.getRuntime());
            plotTextView.setText(movie.getPlot());

            ratingTextView.setText(movie.getImdbRating() + "/10 (" + movie.getImdbVotes() + ")");
            directorTextView.setText(movie.getDirector());
            actorsTextView.setText(movie.getActors());
            writerTextView.setText(movie.getWriter());
            awardsTextView.setText(movie.getAwards());
        }
    }

    // for UP AS HOME functionality
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //clean up Rx
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && subscription.isUnsubscribed())
            subscription.unsubscribe();
    }
}
