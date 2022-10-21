package com.ghl.net.coroutine

import com.ghl.net.exception.CwlExceptionHandle
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * 协程异常捕获
 */

class CoroutineBuilder<T> {
    var onRequest: (suspend () -> T)? = null
    var onSuccess: ((T) -> Unit)? = null
    var onError: ((Throwable) -> Unit)? = null
}

fun <T> CoroutineScope.cwlLaunch(init: CoroutineBuilder<T>.() -> Unit) {
    val result = CoroutineBuilder<T>().apply(init)

    val handler = CoroutineExceptionHandler { _, exception ->
        result.onError?.invoke(CwlExceptionHandle.handleException(exception))
    }

    launch(handler) {
        result.onRequest?.invoke()?.run {
            result.onSuccess?.invoke(this)
        }
    }
}