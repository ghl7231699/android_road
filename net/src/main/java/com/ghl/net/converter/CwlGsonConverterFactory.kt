package com.ghl.net.converter

import com.ghl.net.converter.CwlGsonRequestBodyConverter
import com.ghl.net.converter.CwlGsonResponseBodyConverter
import com.ghl.net.converter.getStringFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 *  转换器
 */
class CwlGsonConverterFactory(private val gson: Gson) : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return CwlGsonResponseBodyConverter<Any>(gson, type)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody> {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return CwlGsonRequestBodyConverter(gson, adapter)
    }

    companion object {
        @JvmOverloads
        fun create(
            gson: Gson? = GsonBuilder().registerTypeAdapterFactory(getStringFactory()).create()
        ): CwlGsonConverterFactory {
            if (gson == null) throw NullPointerException("gson == null")
            return CwlGsonConverterFactory(gson)
        }
    }
}