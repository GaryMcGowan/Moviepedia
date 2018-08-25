package com.garymcgowan.moviepedia.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.garymcgowan.moviepedia.view.search.MovieListActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, MovieListActivity::class.java))
        finish()
    }
}
