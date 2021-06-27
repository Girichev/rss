package com.sample.rss.support.recyclerview.decorator

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

/**
 * EdgeDecorator
 *
 * @param context  Context
 * @param spaceRes padding set on the left side of the first item and the right side of the last item
 */

class EdgeDecoration(val context: Context, @DimenRes spaceRes: Int) : RecyclerView.ItemDecoration() {

    private val edgePadding: Int = context
        .resources
        .getDimensionPixelOffset(spaceRes)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val itemCount = state.itemCount

        val itemPosition = parent.getChildAdapterPosition(view)

        // no position, leave it alone
        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }
        outRect.set(edgePadding, edgePadding, edgePadding, edgePadding)

        // first item
        if (itemPosition == 0) {
            outRect.set(edgePadding, edgePadding, edgePadding, edgePadding / 2)
        } else if (itemCount > 0 && itemPosition == itemCount - 1) {
            outRect.set(edgePadding, edgePadding / 2, edgePadding, edgePadding)
        } else {
            outRect.set(edgePadding, edgePadding / 2, edgePadding, edgePadding / 2)
        }// every other item
        // last item
    }
}