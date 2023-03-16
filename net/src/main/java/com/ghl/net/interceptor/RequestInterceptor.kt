package com.ghl.net.interceptor

import okhttp3.*
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.util.*

/**
 * 拼接公参
 */
class RequestInterceptor : Interceptor {
    private val DOMAIN_API = "Domain-Name"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (request.method.equals("GET", ignoreCase = true)) {
            request = addGetParams(request)
        } else if (request.method.equals("POST", ignoreCase = true)) {
            request = addPostParams3(request)
        }
        return chain.proceed(request)
    }

    private fun addGetParams(r: Request): Request {
        //添加公共参数
        var request = r
        var httpUrl = request.url
        httpUrl = httpUrl.newBuilder().apply {
            // builder.setQueryParameter(key, value);
        }.build()
        request = request.newBuilder().url(httpUrl).build()
        return request
    }

    @Throws(UnsupportedEncodingException::class)
    private fun addPostParams3(r: Request): Request {
        var request = r
        val requestBody = request.body
        if (requestBody is FormBody) {
            val bodyBuilder = FormBody.Builder()
            val formBody = requestBody as FormBody?
            var httpUrl = request.url.newBuilder()
                .build()
            val builder = httpUrl.newBuilder()

            // 将body转成url的参数
            val paramsInPostList = request.headers(PARAMS_IN_POST)
            for (i in 0 until formBody!!.size) {
                val key = formBody.encodedName(i)
                val value = URLDecoder.decode(formBody.encodedValue(i), "UTF-8")
                if (paramsInPostList.contains(key)) {
                    bodyBuilder.addEncoded(key, formBody.encodedValue(i))
                } else {
                    builder.setQueryParameter(key, value)
                }
            }

//            builder.setQueryParameter(stringStringEntry.getKey(), stringStringEntry.getValue());
            httpUrl = builder.build()
            request = request.newBuilder().url(httpUrl).post(bodyBuilder.build()).build()
        } else if (requestBody is MultipartBody) {
            val urlBuilder = request.url.newBuilder()
            //urlBuilder.setQueryParameter(stringStringEntry.getKey(), stringStringEntry.getValue());
            request = request.newBuilder().url(urlBuilder.build()).build()
        } else if (requestBody != null) {
            try {
                // post 请求方式，Content-Type: application/json 情景下添加公参
                if (requestBody.contentType() != null
                    && "application".equals(requestBody.contentType()?.type, ignoreCase = true)
                    && "json".equals(requestBody.contentType()?.subtype, ignoreCase = true)
                ) {
                    val urlBuilder = request.url.newBuilder()
                    //urlBuilder.setQueryParameter(stringStringEntry.getKey(), stringStringEntry.getValue());
                    request = request.newBuilder().url(urlBuilder.build()).build()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return request
    }

    companion object {
        const val PARAMS_IN_POST = "bodyParams"
        const val POST_BLOCK_URL = "postBlockUrl"
        private const val HEADER_USER_AGENT = "User-Agent"
    }
}