package com.ghl.biz_flutter.service

import android.app.Application
import android.content.Context
import android.os.Build
import android.widget.Toast
import com.ghl.biz_flutter.flutter.PageRouter
import com.ghl.biz_flutter.flutter.TextPlatformViewFactory
import com.ghl.biz_flutter.ui.FlutterFragmentActivity.Companion.TAG_FLUTTER_FRAGMENT
import com.ghl.imc.AbsModuleService
import com.ghl.imc.ModuleServiceManager
import com.ghl.common.ENGINE_ID
import com.ghl.common.constants.Constants
import com.ghl.common.service.FlutterModuleService
import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.Utils
import com.idlefish.flutterboost.interfaces.INativeRouter
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.StandardMessageCodec

class FlutterModuleImp : AbsModuleService(), FlutterModuleService {

    override fun openPageByUrl(context: Context?, url: String?, params: Map<String?, *>?): Boolean {
        return PageRouter.openPageByUrl(context, url,
                params)
    }

    override fun getPageTag(key: String): String {
        return TAG_FLUTTER_FRAGMENT
    }

    override fun initFlutterBoost(application: Application) {
        val router = INativeRouter { context, url, urlParams, _, _ ->
            val assembleUrl = Utils.assembleUrl(url, urlParams)
            ModuleServiceManager.getClassTarget(FlutterModuleService::class.java).openPageByUrl(context, assembleUrl, urlParams)
        }

        val boostLifecycleListener: FlutterBoost.BoostLifecycleListener = object : FlutterBoost.BoostLifecycleListener {
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
        val platform = FlutterBoost.ConfigBuilder(application, router)
                .isDebug(true)
                .whenEngineStart(FlutterBoost.ConfigBuilder.ANY_ACTIVITY_CREATED)
                .renderMode(FlutterView.RenderMode.texture)
                .lifecycleListener(boostLifecycleListener)
                .build()
        FlutterBoost.instance().init(platform)

    }

    override fun initFlutter(application: Application) {
        var count = 0

        val flutterEngine: FlutterEngine = FlutterEngine(application).apply {
            dartExecutor
                    .executeDartEntrypoint(
                            DartExecutor.DartEntrypoint.createDefault()
                    )
        }
        FlutterEngineCache.getInstance().put(ENGINE_ID, flutterEngine)

        MethodChannel(flutterEngine.dartExecutor, Constants.Method_channel).apply {
            setMethodCallHandler { call, _ ->
                when (call.method) {
                    Constants.method_increment_counter -> {
                        count++
                        Toast.makeText(application, "count = $count", Toast.LENGTH_SHORT).show()
                    }
                    Constants.method_request_counter -> {
                    }

//                Constants.method_finish -> {
//                    Toast.makeText(this, "我跳转了", Toast.LENGTH_SHORT).show()
//                }
                }
            }
        }
    }
}