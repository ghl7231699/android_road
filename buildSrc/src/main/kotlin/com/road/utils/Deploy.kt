package com.road.utils

import java.text.SimpleDateFormat
import java.util.*

object Deploy {
    fun releaseTime(): String =
        SimpleDateFormat("yyyy-MM-dd_HH:mm", Locale.CHINA).format(System.currentTimeMillis())
}