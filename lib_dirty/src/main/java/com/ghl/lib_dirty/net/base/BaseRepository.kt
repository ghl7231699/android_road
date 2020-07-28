package com.ghl.lib_dirty.net.base

import com.ghl.lib_dirty.network.RetrofitManager

open class BaseRepository {
    protected fun <T> createService(clazz: Class<T>): T {
        return RetrofitManager.getInstance().createApi(clazz)
    }
}