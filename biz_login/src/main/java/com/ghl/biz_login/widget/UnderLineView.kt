package com.ghl.biz_login.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.ghl.biz_login.R
import com.ghl.biz_login.d2p

class UnderLineView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint: Paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.xz_212121)
        isAntiAlias = true

    }

    private val normal = 14f.d2p(context)
    private val bold = 24f.d2p(context)

    init {

    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.run {
            paint.run {
                textSize = normal
                strokeWidth = 1f
                drawText("第", 0f, 0f, this)

            }
        }


        super.onDraw(canvas)
    }


}