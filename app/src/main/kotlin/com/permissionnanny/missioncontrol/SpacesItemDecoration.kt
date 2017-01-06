package com.permissionnanny.missioncontrol

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View

class SpacesItemDecoration(
        context: Context,
        unit: Int,
        left: Float,
        top: Float,
        right: Float,
        bottom: Float)
    : RecyclerView.ItemDecoration() {

    private val pxLeft: Int
    private val pxTop: Int
    private val pxRight: Int
    private val pxBottom: Int

    init {
        val dm = context.resources.displayMetrics
        pxLeft = TypedValue.applyDimension(unit, left, dm).toInt()
        pxTop = TypedValue.applyDimension(unit, top, dm).toInt()
        pxRight = TypedValue.applyDimension(unit, right, dm).toInt()
        pxBottom = TypedValue.applyDimension(unit, bottom, dm).toInt()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        outRect.left = pxLeft
        outRect.top = pxTop
        outRect.right = pxRight
        outRect.bottom = pxBottom
    }
}

