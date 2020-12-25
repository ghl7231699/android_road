package com.ghl.common.databus

import androidx.lifecycle.MutableLiveData

object LiveDataBus {
    private var bus = mutableMapOf<String?, MutableLiveData<Any>>()
    //注册订阅

    @Synchronized
    fun <T> with(key: String?, type: Class<T>?): MutableLiveData<Any>? {
        if (!bus.containsKey(key)) {
            bus[key] = MutableLiveData()
        }
        return bus[key]
    }
}