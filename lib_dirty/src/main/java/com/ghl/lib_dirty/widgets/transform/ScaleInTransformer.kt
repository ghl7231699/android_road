package com.ghl.lib_dirty.widgets.transform

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class ScaleInTransformer : ViewPager2.PageTransformer {
    private val mMinScale = DEFAULT_MIN_SCALE
    override fun transformPage(page: View, position: Float) {
        page.elevation = -abs(position)

        val pageWidth = page.width
        val pageHeight = page.height

        page.pivotX = (pageWidth / 2).toFloat()
        page.pivotY = (pageHeight / 2).toFloat()

        if (position < -1) {
            page.scaleX = mMinScale
            page.scaleY = mMinScale
            page.pivotX = pageWidth.toFloat()
        } else if (position <= 1) {
            if (position < 0) {
                val scaleFactor = (1 + position) * (1 - mMinScale) + mMinScale
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
                page.pivotX = pageWidth * (DEFAULT_CENTER + DEFAULT_CENTER * -position)
            } else {
                val scaleFactor = (1 - position) * (1 - mMinScale) + mMinScale
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
                page.pivotX = pageWidth * ((1 - position) * DEFAULT_CENTER)
            }
        } else {
            page.pivotX = 0f
            page.scaleX = mMinScale
            page.scaleY = mMinScale
        }

    }

    companion object {

        const val DEFAULT_MIN_SCALE = 0.85f
        const val DEFAULT_CENTER = 0.5f
    }

}