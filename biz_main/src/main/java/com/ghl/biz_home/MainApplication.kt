package com.ghl.biz_home

import android.app.Application
import com.ghl.common.application.BaseApp
import com.ghl.common.network.initNet

class MainApplication : BaseApp() {
    override fun createApp(application: Application) {
        initNet()
    }
}