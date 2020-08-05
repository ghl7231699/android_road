package com.ghl.lib_dirty

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Build
import android.widget.Toast
import com.ghl.imc.ModuleServiceManager
import com.ghl.lib_dirty.constants.Constants
import com.ghl.lib_dirty.flutter.TextPlatformViewFactory
import com.ghl.lib_dirty.network.RetrofitManager
import com.ghl.lib_dirty.network.loginterceptor.LogInterceptor
import com.ghl.lib_dirty.service.AutoModuleService
import com.ghl.lib_dirty.service.FlutterModuleService
import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.FlutterBoost.BoostLifecycleListener
import com.idlefish.flutterboost.Utils
import com.idlefish.flutterboost.interfaces.INativeRouter
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.StandardMessageCodec
import okhttp3.OkHttpClient

const val ENGINE_ID = "1"

@SuppressLint("StaticFieldLeak")
class AutoApplication : Application() {

    var count = 0

    private lateinit var channel: MethodChannel

    lateinit var flutterEngine: FlutterEngine

    override fun onCreate() {
        super.onCreate()
        instance = this
        mContext = applicationContext

        ModuleServiceManager.createApp(this)

        val builder = OkHttpClient.Builder()
        builder.addInterceptor(LogInterceptor())
        RetrofitManager.getInstance().init(builder)
        ModuleServiceManager.getClassTarget(AutoModuleService::class.java).init()

        configFlutterBoost()
    }


    private fun configOriginalFlutterInit() {
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

    private fun configFlutterBoost() {
//        val router = INativeRouter { context, url, urlParams, _, _ ->
//            val assembleUrl = Utils.assembleUrl(url, urlParams)
//            ModuleServiceManager.getClassTarget(FlutterModuleService::class.java).openPageByUrl(context, assembleUrl, urlParams)
//        }
//
//        val platform = FlutterBoost.ConfigBuilder(this, router)
//                .isDebug(true)
//                .whenEngineStart(FlutterBoost.ConfigBuilder.ANY_ACTIVITY_CREATED)
//                .build()
//
//        FlutterBoost.instance().init(platform)


        val router = INativeRouter { context, url, urlParams, _, _ ->
            val assembleUrl = Utils.assembleUrl(url, urlParams)
            ModuleServiceManager.getClassTarget(FlutterModuleService::class.java).openPageByUrl(context, assembleUrl, urlParams)
        }

        val boostLifecycleListener: BoostLifecycleListener = object : BoostLifecycleListener {
            override fun beforeCreateEngine() {}
            override fun onEngineCreated() {

                // 注册MethodChannel，监听flutter侧的getPlatformVersion调用
                val methodChannel = MethodChannel(FlutterBoost.instance().engineProvider().dartExecutor, "flutter_native_channel")
                methodChannel.setMethodCallHandler { call: MethodCall, result: MethodChannel.Result ->
                    if (call.method == "getPlatformVersion") {
                        result.success(Build.VERSION.RELEASE)
                    } else {
                        result.notImplemented()
                    }
                }

                // 注册PlatformView viewTypeId要和flutter中的viewType对应
                FlutterBoost
                        .instance()
                        .engineProvider()
                        .platformViewsController
                        .registry
                        .registerViewFactory("plugins.test/view", TextPlatformViewFactory(StandardMessageCodec.INSTANCE))
            }

            override fun onPluginsRegistered() {}
            override fun onEngineDestroy() {}
        }
        //
        // AndroidManifest.xml 中必须要添加 flutterEmbedding 版本设置
        //
        //   <meta-data android:name="flutterEmbedding"
        //               android:value="2">
        //    </meta-data>
        // GeneratedPluginRegistrant 会自动生成 新的插件方式　
        //
        // 插件注册方式请使用
        // FlutterBoost.instance().engineProvider().getPlugins().add(new FlutterPlugin());
        // GeneratedPluginRegistrant.registerWith()，是在engine 创建后马上执行，放射形式调用
        //
        val platform = FlutterBoost.ConfigBuilder(this, router)
                .isDebug(true)
                .whenEngineStart(FlutterBoost.ConfigBuilder.ANY_ACTIVITY_CREATED)
                .renderMode(FlutterView.RenderMode.texture)
                .lifecycleListener(boostLifecycleListener)
                .build()
        FlutterBoost.instance().init(platform)

    }

    private fun reportCounter() {
        channel.invokeMethod("reportCounter", count)
    }

    companion object {
        lateinit var mContext: Context
        lateinit var instance: AutoApplication
    }

}