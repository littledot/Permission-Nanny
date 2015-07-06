package com.permissionnanny.missioncontrol;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int mPxLeft;
    private int mPxTop;
    private int mPxRight;
    private int mPxBottom;

    public SpacesItemDecoration(Context context, int unit, float left, float top, float right, float bottom) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mPxLeft = (int) TypedValue.applyDimension(unit, left, dm);
        mPxTop = (int) TypedValue.applyDimension(unit, top, dm);
        mPxRight = (int) TypedValue.applyDimension(unit, right, dm);
        mPxBottom = (int) TypedValue.applyDimension(unit, bottom, dm);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mPxLeft;
        outRect.top = mPxTop;
        outRect.right = mPxRight;
        outRect.bottom = mPxBottom;
    }
}

