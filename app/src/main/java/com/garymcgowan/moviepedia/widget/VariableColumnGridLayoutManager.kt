package com.garymcgowan.moviepedia.widget

import android.content.Context
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VariableColumnGridLayoutManager(context: Context, @DimenRes minItemWidth: Int) : GridLayoutManager(context, 1) {

    private val minItemWidthPixels: Float = context.resources.getDimension(minItemWidth)

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?,
                                  state: RecyclerView.State) {
        updateSpanCount()
        super.onLayoutChildren(recycler, state)
    }

    private fun updateSpanCount() {
        var spanCount = width / minItemWidthPixels
        if (spanCount < 1) {
            spanCount = 1f
        }
        this.spanCount = spanCount.toInt()
    }
}
