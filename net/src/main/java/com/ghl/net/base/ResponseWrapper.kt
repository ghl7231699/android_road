package com.ghl.net.base

data class ResponseWrapper<T>(val data: T?, val errorCode: Int, val errorMsg: String?)