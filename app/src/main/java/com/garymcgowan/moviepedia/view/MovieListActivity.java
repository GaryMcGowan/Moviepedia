package com.garymcgowan.moviepedia.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.garymcgowan.moviepedia.App;
import com.garymcgowan.moviepedia.R;
import com.garymcgowan.moviepedia.model.Movie;
import com.garymcgowan.moviepedia.model.OmdbMovieRepository;
import com.garymcgowan.moviepedia.network.MoviesAPI;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.BackpressureStrategy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class MovieListActivity extends AppCompatActivity implements MovieListActivityView {

    private CompositeDisposable disposables = new CompositeDisposable();
    SearchView searchView = null;

    @Inject MoviesAPI moviesAPI;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.emptyTextView) TextView emptyTextView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    MovieListActivityPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        App.getApplicationComponent().inject(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        toolbar.setNavigationIcon(R.mipmap.movie_white);
        assert recyclerView != null;


        setupRecyclerView(recyclerView, null);

        presenter = new MovieListActivityPresenter(this, new OmdbMovieRepository(moviesAPI), AndroidSchedulers.mainThread());

        if (searchView != null)
            presenter.setSearchTermObservable(RxSearchView.queryTextChanges(searchView)
                    .toFlowable(BackpressureStrategy.LATEST)
                    .map(CharSequence::toString));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_list, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MovieListActivity.this.getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            setSearchView((SearchView) searchItem.getActionView());
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MovieListActivity.this.getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void setSearchView(final SearchView searchView) {

        if (this.searchView != searchView) {
            this.searchView = searchView;

            if (searchView != null) {

                if (presenter != null)
                    presenter.setSearchTermObservable(RxSearchView.queryTextChanges(searchView)
                            .toFlowable(BackpressureStrategy.LATEST)
                            .map(CharSequence::toString));

                //clean up old subscription
//                if (disposables != null)
//                    disposables.clear();

                //subscribe again
                // filter characters >1
                // debounce 400ms
                // flatmap
//                disposables.add(
//                        RxSearchView.queryTextChanges(searchView)
//                                .toFlowable(BackpressureStrategy.LATEST)
//                                .filter(s -> s.length() > 1)
//                                .subscribeOn(AndroidSchedulers.mainThread())
//                                .debounce(QUERY_UPDATE_DELAY_MILLIS, TimeUnit.MILLISECONDS)
//                                .flatMap(s -> moviesAPI.getObservableMoviesSearch(s.toString(),
//                                        null, null, null, null, null,
//                                        null)
//                                        .subscribeOn(Schedulers.io())
//
//                                )
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribeWith(new DisposableSubscriber<Search>() {
//                                    @Override
//                                    public void onNext(Search search) {
//                                        if (search == null)
//                                            Snackbar.make(searchView, R.string.connection_failure, Snackbar.LENGTH_LONG).show();
//                                        else
//                                            setupRecyclerView(recyclerView, search.search);
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable t) {
//                                        Timber.d("onError: " + t);
//                                        Timber.e(t);
//                                    }
//
//                                    @Override
//                                    public void onComplete() {
//
//                                    }
//                                }));
            }
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (disposables != null) {
//            disposables.clear();
//        }
//    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, List<Movie> list) {

        if (list == null) // null for first time setup
        {
            emptyTextView.setText(R.string.empty_text_first_time);

            emptyTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else if (list.size() == 0) // empty list means no results
        {
            emptyTextView.setText(R.string.empty_text);

            emptyTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {

            // finally, we have some movies

            emptyTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(list));
        }
    }

    @Override
    public void displayMovies(List<Movie> movies) {
        setupRecyclerView(recyclerView, movies);
    }

    @Override
    public void displayError(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Error");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.create().show();
    }


    class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Movie> mValues;

        SimpleItemRecyclerViewAdapter(List<Movie> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);

            //set movie title
            holder.titleTextView.setText(holder.mItem.getTitleYear());

            //load image with Picasso
            Picasso.with(getApplicationContext())
                    .load(holder.mItem.getPosterURL())
                    .placeholder(R.color.imagePlaceholder)
                    .error(R.color.imagePlaceholder)
                    .into(holder.posterImageView);

            holder.mView.setOnClickListener(v -> {
                //launch details intent
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(DetailsActivity.ARG_ITEM_ID, holder.mItem.getImdbID());
                intent.putExtra(DetailsActivity.ARG_ITEM_TITLE, holder.mItem.getTitle());

                context.startActivity(intent);

            });
        }

        @Override
        public int getItemCount() {
            if (mValues == null)
                return 0;
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final View mView;
            Movie mItem;

            @BindView(R.id.titleTextView) TextView titleTextView;
            @BindView(R.id.posterImageView) ImageView posterImageView;


            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                mView = view;
            }

            @Override
            public String toString() {
                return super.toString() + " '" + titleTextView.getText() + "'";
            }
        }
    }
}
