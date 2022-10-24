package com.ghl.net.api

import com.ghl.net.converter.CwlGsonConverterFactory
import com.ghl.net.interceptor.HeaderInterceptor
import com.ghl.net.interceptor.LogInterceptor
import com.ghl.net.interceptor.RequestInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * 访问api接口的客户端
 *
 */
class ApiClient {

    // 单例
    private object Holder {
        val INSTANCE = ApiClient()
    }

    companion object {
        val instance = Holder.INSTANCE
    }

    var connectTimeOut = 10000L
    var readTimeout = 10000L
    var writeTimeout = 10000L

    var baseUrl = "https://www.wanandroid.com"

    lateinit var mClient: Retrofit

    fun init() {
        // 设置okHttpClient
        val client = OkHttpClient()
            .newBuilder()
            .onlyApply {
                connectTimeout(connectTimeOut, TimeUnit.SECONDS)// 连接超时
                readTimeout(readTimeout, TimeUnit.SECONDS)// 读超时
                writeTimeout(writeTimeout, TimeUnit.SECONDS)// 写超时
                addInterceptor(HeaderInterceptor())
                addInterceptor(RequestInterceptor())
                addInterceptor(LogInterceptor())
            }.build()

        mClient = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(CwlGsonConverterFactory.create())
            .build()
    }

    fun <T> crate(clazz: Class<T>): T {
        return mClient.create(clazz)
    }

}

fun <T> T.onlyApply(block: T.() -> Unit): T {
    block()
    return this
}