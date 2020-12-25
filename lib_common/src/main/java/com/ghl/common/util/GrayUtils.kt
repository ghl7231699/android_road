package com.ghl.common.util

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.view.View

/**
 * 通过硬件加速设置灰度
 */
fun grayPageColor(view: View) {
    val paint = Paint()
    val cm = ColorMatrix()
    cm.setSaturation(0f)
    paint.colorFilter = ColorMatrixColorFilter(cm)
//        getWindow().getDecorView()
    view.setLayerType(View.LAYER_TYPE_HARDWARE, paint)
}