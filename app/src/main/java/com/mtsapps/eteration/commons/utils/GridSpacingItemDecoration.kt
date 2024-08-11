package com.mtsapps.eteration.commons.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class GridSpacingItemDecoration (private val spacing: Int,private val context: Context) : RecyclerView.ItemDecoration()
{
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    )
    {
        super.getItemOffsets(outRect, view, parent, state)
        val padding = (context.resources.displayMetrics.density * spacing)
        outRect.top = padding.toInt()
        outRect.bottom = padding.toInt()
        outRect.left = padding.toInt()
        outRect.right = padding.toInt()
    }
}