package com.ghl.net.base.interceptor

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser


internal class GsonFormatter : JSONFormatter() {
    private val gson = GsonBuilder().setPrettyPrinting().create()
    override fun format(source: String?): String {
        return gson.toJson(JsonParser.parseString(source))
    }

    companion object {
        fun buildIfSupported(): JSONFormatter? {
            return try {
                Class.forName("com.google.gson.Gson")
                GsonFormatter()
            } catch (ignore: ClassNotFoundException) {
                null
            }
        }
    }
}

open class JSONFormatter {
    open fun format(source: String?): String {
        return ""
    }

    companion object {
        private val jsonFormatter = findJSONFormatter()
        fun formatJSON(source: String?): String {
            return try {
                jsonFormatter.format(source)
            } catch (e: Exception) {
                ""
            }
        }

        private fun findJSONFormatter(): JSONFormatter {
            val gsonFormatter = GsonFormatter.buildIfSupported()
            return gsonFormatter ?: JSONFormatter()
        }
    }
}