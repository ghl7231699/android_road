package com.ghl.opt_startup

import android.content.Context
import androidx.startup.AppInitializer

class StartUpBoost {
    fun init(context: Context) {
        //为了实现 WorkManagerInitializer 的延迟初始化，您可以进行如下操作:
        AppInitializer.getInstance(context)
                .initializeComponent(WorkManagerInitializer::class.java)
    }
}