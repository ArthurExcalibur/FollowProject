package com.excalibur.followproject.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lieniu on 2017/8/23.
 */

public class SpaceItemDeration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = 10;
        outRect.left = 20;
        outRect.right = 20;
    }
}
