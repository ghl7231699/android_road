package com.ghl.net.converter

import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.internal.bind.TypeAdapters.newFactory
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

/**
 * Desc: String null 解析为空字符，避免空指针
 */

private val STRING: TypeAdapter<String> = object : TypeAdapter<String>() {
    @Throws(IOException::class)
    override fun read(input: JsonReader): String {
        val peek = input.peek()
        if (peek == JsonToken.NULL) {
            input.nextNull()
            return ""
        }
        /* coerce booleans to strings for backwards compatibility */
        return if (peek == JsonToken.BOOLEAN) {
            java.lang.Boolean.toString(input.nextBoolean())
        } else if (peek == JsonToken.NUMBER) {
            // 解决int解析成string时出现.0的问题
            val value = input.nextDouble()
            val lngValue = value.toLong()
            if (lngValue.toDouble() == value) lngValue.toString() else value.toString()
        } else input.nextString()
    }

    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: String?) {
        out.value(value)
    }
}

private val STRING_FACTORY: TypeAdapterFactory = newFactory(String::class.java, STRING)

fun getStringFactory(): TypeAdapterFactory {
    return STRING_FACTORY
}





