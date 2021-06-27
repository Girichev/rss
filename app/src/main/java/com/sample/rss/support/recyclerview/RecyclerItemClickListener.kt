package com.sample.rss.support.recyclerview

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemClickListener(
    context: Context,
    recyclerView: RecyclerView,
    private val mListener: OnItemClickListener?
) : RecyclerView.OnItemTouchListener {

    private var mGestureDetector: GestureDetector =
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                recyclerView.findChildViewUnder(e.x, e.y)?.let { child ->
                    mListener?.onLongItemClick(child, recyclerView.getChildAdapterPosition(child))
                }
            }
        })

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int){}
        fun onLongItemClick(view: View?, position: Int){}
    }

    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
        val childView = view.findChildViewUnder(e.x, e.y)
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView))
            return false
        }
        return false
    }

    override fun onTouchEvent(view: RecyclerView, motionEvent: MotionEvent) {}

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
}

/**
 * Extension for RecyclerView to add OnItemClickListener
 */
fun RecyclerView.addOnItemClickListener(
    context: Context,
    listener: RecyclerItemClickListener.OnItemClickListener
): RecyclerItemClickListener {
    val touchListener = RecyclerItemClickListener(
        context,
        this,
        listener
    )
    addOnItemTouchListener(touchListener)
    return touchListener
}
