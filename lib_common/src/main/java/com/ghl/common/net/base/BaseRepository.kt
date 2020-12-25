package com.ghl.common.net.base

import com.ghl.common.network.RetrofitManager

open class BaseRepository {
    protected fun <T> createService(clazz: Class<T>): T {
        return RetrofitManager.getInstance().createApi(clazz)
    }
}