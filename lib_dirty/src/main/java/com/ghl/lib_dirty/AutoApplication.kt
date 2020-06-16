package com.ghl.lib_dirty

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.widget.Toast
import com.ghl.imc.ModuleServiceManager
import com.ghl.lib_dirty.constants.Constants
import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.Utils
import com.idlefish.flutterboost.interfaces.INativeRouter
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel

const val ENGINE_ID = "1"

@SuppressLint("StaticFieldLeak")
class AutoApplication : Application() {

    var count = 0

    private lateinit var channel: MethodChannel

    lateinit var flutterEngine: FlutterEngine

    override fun onCreate() {
        super.onCreate()
        ModuleServiceManager.createApp(this)
        //TODO  moduleService实现
        //AutoTrackHelper.frameDetection();
//        AutoTrackHelper.frameDetection2()
//        CrashHandler.getinstance().init()

        val router: INativeRouter = INativeRouter { context, url, urlParams, _, _ ->
            val assembleUrl = Utils.assembleUrl(url, urlParams)
            //TODO  moduleService解决
//            PageRouter.openPageByUrl(context, assembleUrl, urlParams)
        }

        val platform = FlutterBoost.ConfigBuilder(this, router)
                .isDebug(true)
                .whenEngineStart(FlutterBoost.ConfigBuilder.ANY_ACTIVITY_CREATED)
                .build()

        FlutterBoost.instance().init(platform)


        flutterEngine = FlutterEngine(this)
        flutterEngine
                .dartExecutor
                .executeDartEntrypoint(
                        DartExecutor.DartEntrypoint.createDefault()
                )

        FlutterEngineCache.getInstance().put(ENGINE_ID, flutterEngine)

        channel = MethodChannel(flutterEngine.dartExecutor, Constants.Method_channel)

        channel.setMethodCallHandler { call, _ ->
            when (call.method) {
                Constants.method_increment_counter -> {
                    count++
                    Toast.makeText(this, "count = $count", Toast.LENGTH_SHORT).show()
                    reportCounter()
                }
                Constants.method_request_counter -> {
                    reportCounter()
                }

//                Constants.method_finish -> {
//                    Toast.makeText(this, "我跳转了", Toast.LENGTH_SHORT).show()
//                }
            }
        }
    }

    private fun reportCounter() {
        channel.invokeMethod("reportCounter", count)
    }

    companion object {
        var mApplicationContext: Context? = null
        var instance: AutoApplication? = null
    }
}