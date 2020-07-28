package com.ghl.lib_dirty.net.base

class BaseBean<T> {
    var errorMsg: String? = null
    var errorCode = 0
    var data: T? = null

    fun isOk() = errorCode == 0

}