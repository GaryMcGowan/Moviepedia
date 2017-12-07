package com.garymcgowan.moviepedia.widget;

import android.content.Context;
import android.support.annotation.DimenRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;


public class VariableColumnGridLayoutManager extends GridLayoutManager {

    private float minItemWidthPixels;

    public VariableColumnGridLayoutManager(Context context, @DimenRes int minItemWidth) {
        super(context, 1);
        this.minItemWidthPixels = context.getResources().getDimension(minItemWidth);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler,
                                 RecyclerView.State state) {
        updateSpanCount();
        super.onLayoutChildren(recycler, state);
    }

    private void updateSpanCount() {
        float spanCount = getWidth() / minItemWidthPixels;
        if (spanCount < 1) {
            spanCount = 1;
        }
        this.setSpanCount((int) spanCount);
    }
}
