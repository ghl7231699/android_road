package com.ghl.base.java.reflect

import java.lang.reflect.Field

data class LuItem(
        @StatisticsVar("luId")
        var luId: String?,
        var showPriceV2: ShowPriceV2?
)


data class ShowPriceV2(
        @StatisticsVar("luPrice")
        var showPrice: String?
)

val caches = mutableMapOf<String, String>()

fun main() {
    val list = mutableListOf<LuItem>()
    for (i in 0..4) {
        val t = LuItem("111$i", showPriceV2 = ShowPriceV2("$100$i"))
        list.add(t)
    }
    for (t in list) {
        val declaredFields = t::class.java.declaredFields
        getFields(declaredFields, t)
        println(caches)
        caches.clear()
    }

}

private fun getFields(declaredFields: Array<Field>, t: Any?) {
    try {
        for (field in declaredFields) {
            field.isAccessible = true
            val annotation = field.getAnnotation(StatisticsVar::class.java)
            val get = field.get(t)
            val declaringClass = field.type
            when (declaringClass.name) {
                String::class.java.name,
                Int::class.java.name,
                Boolean::class.java.name,
                Long::class.java.name,
                Double::class.java.name,
                Float::class.java.name -> {
                    annotation?.apply {
                        val value = annotation.value
                        caches[value] = get as String
                        println("$get\ttype is\t ${field.type}")
                    }
                }
                else -> {
                    getFields(get.javaClass.declaredFields, get)
                }
            }
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun hasAnnotations(c: Class<*>): Boolean {
    val annotation = c.getAnnotation(StatisticsVar::class.java)
    if (annotation != null) {
        println(annotation)
        return true
    }
    return false
}

fun <T : Annotation?> getAnnotation(annotationClass: Class<T>?): Boolean {
    return true
}