package com.ghl.common.network

import com.ghl.common.network.loginterceptor.LogInterceptor
import com.ghl.net.api.ApiClient
import okhttp3.OkHttpClient

fun initNet() {
    val builder = OkHttpClient.Builder()
    builder.addInterceptor(LogInterceptor())
    RetrofitManager.getInstance().init(builder)

    ApiClient.instance.init()
}