package com.mtsapps.eteration.commons.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LinearSpacingItemDecoration(
    private val spacing: Int,
    private val context: Context,
    private val includeBottom: Boolean = true
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0
        val padding = (context.resources.displayMetrics.density * spacing)
        outRect.top = padding.toInt()
        if (includeBottom && position == itemCount - 1) outRect.bottom = padding.toInt()

    }

}