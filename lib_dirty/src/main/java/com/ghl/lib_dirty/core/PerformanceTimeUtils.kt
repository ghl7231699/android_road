package com.ghl.lib_dirty.core

import android.util.Log

object PerformanceTimeUtils {
    // 保存开始与可见时间
    private var timePair: Pair<Long, Long>? = null

    // 程序启动时间
    fun appStart() {
        val currentTimeMillis = System.currentTimeMillis()
        timePair = Pair(currentTimeMillis, currentTimeMillis)
    }

    // 第一个activity可见
    fun firstActivityResume() {
        timePair?.apply {
            Log.i("PerformanceTimeUtils", "第一个页面启动耗时${(System.currentTimeMillis() - this.first) / 1000.0}s")
        }
    }

    // home可见
    fun homeActivityResume() {
        timePair?.run {
            Log.i("PerformanceTimeUtils", "首页启动耗时${(System.currentTimeMillis() - this.second) / 1000.0}s")
            timePair = null
        }
    }

}