package com.ghl.biz_home

import android.app.Application
import com.ghl.common.application.BaseApp
import com.ghl.common.network.initNet
import com.ghl.common.service.AutoModuleService
import com.ghl.imc.ModuleServiceManager

class MainApplication : BaseApp() {
    override fun createApp(application: Application) {
        ModuleServiceManager.createApp(this)
        ModuleServiceManager.getClassTarget(AutoModuleService::class.java).init()
        initNet()
    }
}