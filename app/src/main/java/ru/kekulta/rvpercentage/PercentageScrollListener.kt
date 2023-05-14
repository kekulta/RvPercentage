package ru.kekulta.rvpercentage

import android.graphics.Rect
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class PercentageScrollListener(
    val percentage: Int,
    var callback: (item: Int, visibility: Int) -> Unit,
) :
    RecyclerView.OnScrollListener() {

    private val visibleSet = mutableSetOf<Int>()

    override fun onScrolled(rvPercentage: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(rvPercentage, dx, dy)

        val layoutManager = rvPercentage.layoutManager as LinearLayoutManager
        val firstPosition = layoutManager.findFirstVisibleItemPosition()
        val lastPosition = layoutManager.findLastVisibleItemPosition()
        val rvRect = Rect()
        visibleSet.removeIf { it !in firstPosition..lastPosition }
        rvPercentage.getGlobalVisibleRect(rvRect)

        for (i in firstPosition..lastPosition) {
            val rowRect = Rect()
            layoutManager.findViewByPosition(i)!!.getGlobalVisibleRect(rowRect)
            val percentFirst: Int =
                if (layoutManager.orientation == RecyclerView.VERTICAL) {
                    if (rowRect.bottom >= rvRect.bottom) {
                        val visibleHeightFirst = rvRect.bottom - rowRect.top
                        visibleHeightFirst * 100 / layoutManager.findViewByPosition(i)!!.height
                    } else {
                        val visibleHeightFirst = rowRect.bottom - rvRect.top
                        visibleHeightFirst * 100 / layoutManager.findViewByPosition(i)!!.height
                    }.coerceAtMost(100)
                } else {
                    if (rowRect.left >= rvRect.right) {
                        val visibleWidthsFirst = rvRect.left - rowRect.right
                        visibleWidthsFirst * 100 / layoutManager.findViewByPosition(i)!!.height
                    } else {
                        val visibleWidthsFirst = rowRect.left - rvRect.right
                        visibleWidthsFirst * 100 / layoutManager.findViewByPosition(i)!!.width
                    }.coerceAtMost(100)
                }

            if (percentFirst >= percentage && visibleSet.add(i)) {
                callback(i, percentFirst)
            }
        }
    }
}