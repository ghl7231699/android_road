package com.ghl.lib_dirty

import android.app.Application
import android.content.Context
import com.ghl.imc.ModuleServiceManager
import com.ghl.lib_dirty.network.RetrofitManager
import com.ghl.lib_dirty.network.loginterceptor.LogInterceptor
import com.ghl.lib_dirty.service.AutoModuleService
import com.ghl.lib_dirty.service.FlutterModuleService
import okhttp3.OkHttpClient

const val ENGINE_ID = "1"

class AutoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        mContext = applicationContext

        ModuleServiceManager.createApp(this)

        val builder = OkHttpClient.Builder()
        builder.addInterceptor(LogInterceptor())
        RetrofitManager.getInstance().init(builder)
        ModuleServiceManager.getClassTarget(AutoModuleService::class.java).init()
        ModuleServiceManager.getClassTarget(FlutterModuleService::class.java).initFlutterBoost(this)
    }

    companion object {
        lateinit var mContext: Context
        lateinit var instance: AutoApplication
    }

}