@file:Suppress("UNCHECKED_CAST")

package com.ghl.common.net

import java.lang.reflect.ParameterizedType

fun <T> getT1(o: Any, i: Int): Class<T>? {
    try {
        val genType = o.javaClass.genericSuperclass

        (genType as ParameterizedType?)?.actualTypeArguments?.apply {
            return this[i] as Class<T>
        }
        return null
    } catch (e: ClassCastException) {
    }
    return null
}

fun <T> getT(o: Any, i: Int): T? {
    try {
        val type = o.javaClass.genericSuperclass
        if (type is ParameterizedType) {
            return (type.actualTypeArguments[i] as Class<T>)
                    .newInstance()
        } else if (type is Class<*>) {
            val typ = type.genericSuperclass as ParameterizedType?
            return (typ!!.actualTypeArguments[i] as Class<T>).newInstance()
        }
    } catch (e: InstantiationException) {
    } catch (e: IllegalAccessException) {
    } catch (e: ClassCastException) {
    }
    return null
}

/**
 * 判断集合是否为null或者0个元素
 */
fun isNullOrEmpty(c: Collection<*>?): Boolean {
    return null == c || c.isEmpty()
}