package com.garymcgowan.moviepedia.view.details;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.garymcgowan.moviepedia.App;
import com.garymcgowan.moviepedia.R;
import com.garymcgowan.moviepedia.model.Movie;
import com.garymcgowan.moviepedia.network.OmdbMovieRepository;
import com.squareup.picasso.Picasso;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import timber.log.Timber;

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsActivityView {

    public static final String ARG_ITEM_ID = "imdb_id";
    public static final String ARG_ITEM_TITLE = "movie_title";

    @Inject OmdbMovieRepository movieRepository;
    @Inject Picasso picasso;
    Movie movie = null;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_layout) CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.posterImageView) ImageView posterImageView;
    @BindView(R.id.ratedTextView) TextView ratedTextView;
    @BindView(R.id.runtimeTextView) TextView runtimeTextView;
    @BindView(R.id.plotTextView) TextView plotTextView;
    @BindView(R.id.ratingTextView) TextView ratingTextView;
    @BindView(R.id.directorTextView) TextView directorTextView;
    @BindView(R.id.actorsTextView) TextView actorsTextView;
    @BindView(R.id.writerTextView) TextView writerTextView;
    @BindView(R.id.awardsTextView) TextView awardsTextView;

    MovieDetailsActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        App.getApplicationComponent().inject(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        //clear dummy text
        ratedTextView.setText(null);
        runtimeTextView.setText(null);
        plotTextView.setText(null);
        ratingTextView.setText(null);
        directorTextView.setText(null);
        actorsTextView.setText(null);
        writerTextView.setText(null);
        awardsTextView.setText(null);

        presenter = new MovieDetailsActivityPresenter(this, movieRepository, AndroidSchedulers.mainThread());

        String imdbId = getIntent().getStringExtra(ARG_ITEM_ID);
        String title = getIntent().getStringExtra(ARG_ITEM_TITLE);

        if (title != null) {
            collapsingToolbar.setTitle(title);
        }

        if (imdbId != null) {
            presenter.loadMovieDetails(imdbId);
        }
    }

    public void setMovie(@NonNull Movie movie) {
        this.movie = movie;

        assert movie != null;
        if (movie == null) {
            return;
        }

        if (posterImageView != null)
            picasso.load(movie.getPosterURL())
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
        if (presenter != null) {
            presenter.clearDisposables();
        }
    }

    @Override
    public void displayMovieDetails(Movie movieDetails) {
        setMovie(movieDetails);
    }

    @Override
    public void displayError(String message) {
        //show nice message
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Error");
        alertDialogBuilder.setMessage("Something went wrong");
        alertDialogBuilder.create().show();

        Timber.d("Error: " + message);
    }
}
