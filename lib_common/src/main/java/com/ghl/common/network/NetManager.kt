package com.ghl.common.network

import com.ghl.common.network.loginterceptor.LogInterceptor
import okhttp3.OkHttpClient

fun initNet() {
    val builder = OkHttpClient.Builder()
    builder.addInterceptor(LogInterceptor())
    RetrofitManager.getInstance().init(builder)
}