package com.ghl.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 *
 * header拦截器，统一配置header信息
 *
 */
class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()
            .removeHeader("User-Agent")
            .removeHeader("Connection")
            .removeHeader("Charset")
            .removeHeader("Accept-Encoding")
            .addHeader("Connection", "Keep-Alive")
            .addHeader("Charset", "UTF-8")
            .addHeader("Accept-Encoding", "gzip, deflate")
            .addHeader("idToken", "idToken")
            .addHeader("deviceToken", "deviceToken")
            .addHeader("traceId", "traceId")
            .addHeader("deviceType", "deviceType")
            .addHeader("version", "version")
            .addHeader("siteId", "siteId")
            .addHeader("deviceId", "deviceId")
            .addHeader("provinceId", "provinceId")
            .addHeader("transTime", "transTime")
        return chain.proceed(builder.build())
    }
}