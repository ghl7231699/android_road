package com.ghl.common

import android.app.Application
import android.content.Context
import com.ghl.common.network.initNet
import com.ghl.common.service.AutoModuleService
import com.ghl.common.service.FlutterModuleService
import com.ghl.imc.ModuleServiceManager

const val ENGINE_ID = "1"

class AutoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        println("我是release模式下的组合模式")
        instance = this
        mContext = applicationContext

        ModuleServiceManager.createApp(this)

        initNet()

        ModuleServiceManager.getClassTarget(AutoModuleService::class.java)?.init()
        ModuleServiceManager.getClassTarget(FlutterModuleService::class.java)?.initFlutterBoost(this)
    }


    companion object {
        lateinit var mContext: Context
        lateinit var instance: AutoApplication
    }

}