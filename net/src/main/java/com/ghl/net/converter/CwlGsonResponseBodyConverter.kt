package com.ghl.net.converter

import android.text.TextUtils
import com.ghl.net.base.ResponseWrapper
import com.ghl.net.exception.CwlExceptionHandle
import com.google.gson.Gson
import com.google.gson.internal.`$Gson$Types`
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException
import java.lang.reflect.Type

class CwlGsonResponseBodyConverter<T>(private val gson: Gson, private val type: Type) :
    Converter<ResponseBody, T?> {
    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        val responseBody = value.string()
        if (TextUtils.isEmpty(responseBody)) {
            value.close()
            return null
        }
        value.use {
            val wrapper = gson.fromJson(responseBody, ResponseWrapper::class.java)
            if (wrapper.errorCode == 0) {
                return fromJson<T>(responseBody, type)
            } else {
                val msg = wrapper.errorMsg ?: ""
                throw CwlExceptionHandle.ServerException(
                    wrapper.errorCode,
                    msg,
                    wrapper.data.toString()
                )
            }
        }

    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> fromJson(`in`: String?, type: Type?): T {
        val rawType = `$Gson$Types`.getRawType(type)
        val (data) = gson.fromJson(`in`, ResponseWrapper::class.java)
        val dataJson = gson.toJson(data)
        if (ResponseWrapper::class.java.isAssignableFrom(rawType)) {
            return gson.fromJson(`in`, type)
        } else if (String::class.java.isAssignableFrom(rawType)) {
            return if (gson.fromJson<Any?>(dataJson, type) == null) {
                "" as T
            } else gson.fromJson<Any>(dataJson, type) as T
        } else {
            if (data != null) {
                return gson.fromJson(dataJson, type)
            }
        }
        return gson.fromJson(dataJson, type)
    }
}