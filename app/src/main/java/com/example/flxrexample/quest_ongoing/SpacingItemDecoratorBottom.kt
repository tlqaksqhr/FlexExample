package com.example.flxrexample.quest_ongoing

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacingItemDecoratorBottom(private val spanCount: Int, private val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State){
        outRect.bottom = spacing
    }
}