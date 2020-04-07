package com.mmc.lamandys.liba_datapick.widgets.gray_layer

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.LinearLayout

class GrayLinearLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {
    var cm = ColorMatrix()
    var mPaint = Paint().apply {
        cm.setSaturation(0f)
        colorFilter = ColorMatrixColorFilter(cm)
    }

    override fun draw(canvas: Canvas?) {
        canvas?.saveLayer(null, mPaint)
        super.draw(canvas)
        canvas?.restore()
    }

    override fun dispatchDraw(canvas: Canvas?) {
        canvas?.saveLayer(null, mPaint)
        super.dispatchDraw(canvas)
        canvas?.restore()
    }

}