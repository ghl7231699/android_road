package com.ghl.common.extension

import android.content.Context
import com.ghl.common.util.ScreenUtil

// float 扩展
fun Float.d2p(context: Context) = ScreenUtil.dip2px(context, this).toFloat()

// Int 扩展
fun Int.d2p(context: Context) = ScreenUtil.dip2px(context, this.toFloat())