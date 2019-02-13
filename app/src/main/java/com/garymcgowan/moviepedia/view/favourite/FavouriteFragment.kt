package com.garymcgowan.moviepedia.view.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.garymcgowan.moviepedia.BaseFragment
import com.garymcgowan.moviepedia.R

class FavouriteFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

}