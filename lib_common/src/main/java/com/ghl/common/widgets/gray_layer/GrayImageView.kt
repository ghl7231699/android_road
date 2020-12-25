package com.ghl.common.widgets.gray_layer

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class GrayImageView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0)
    : AppCompatImageView(context, attributeSet, defStyleAttr) {

    var mPaint = Paint()

    var cm = ColorMatrix()

    init {
        cm.setSaturation(0f)
        mPaint.colorFilter = ColorMatrixColorFilter(cm)
    }

    override fun draw(canvas: Canvas) {
        with(canvas) {
            saveLayer(null, mPaint)
            super.draw(canvas)
            restore()
        }
    }
}