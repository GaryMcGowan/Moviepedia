package com.garymcgowan.moviepedia.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.garymcgowan.moviepedia.view.search.MovieListActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MovieListActivity.class);
        startActivity(intent);
        finish();
    }
}
