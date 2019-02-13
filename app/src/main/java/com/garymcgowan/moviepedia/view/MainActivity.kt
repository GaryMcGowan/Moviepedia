package com.garymcgowan.moviepedia.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.garymcgowan.moviepedia.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setupWithNavController(Navigation.findNavController(this, R.id.my_nav_host_fragment))

        navigation.setOnNavigationItemSelectedListener {item ->
            onNavDestinationSelected(item, Navigation.findNavController(this, R.id.my_nav_host_fragment))
        }
    }
}
