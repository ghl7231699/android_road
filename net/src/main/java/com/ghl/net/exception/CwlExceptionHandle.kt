package com.ghl.net.exception

import android.net.ParseException
import com.google.gson.JsonParseException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

object CwlExceptionHandle {
    private const val HTTP_TEMP_REDIRECT = 307
    private const val UNAUTHORIZED = 401
    private const val FORBIDDEN = 403
    private const val NOT_FOUND = 404
    private const val REQUEST_TIMEOUT = 408
    private const val INTERNAL_SERVER_ERROR = 500
    private const val BAD_GATEWAY = 502
    private const val SERVICE_UNAVAILABLE = 503
    private const val GATEWAY_TIMEOUT = 504
    fun handleException(e: Throwable): ResponseThrowable {
        return when (e) {
            is HttpException -> {
                ResponseThrowable(
                    e,
                    if (e.code() == HTTP_TEMP_REDIRECT) HTTP_TEMP_REDIRECT else ERROR.HTTP_ERROR
                ).apply {
                    msg = "网络错误: " + e.code()
                }
            }
            is ServerException -> {
                ResponseThrowable(e, e.result).apply {
                    content = e.content
                    msg = e.msg
                }
            }
            is ResponseThrowable -> {
                e
            }
            is JsonParseException, is JSONException, is ParseException -> {
                ResponseThrowable(e, ERROR.PARSE_ERROR).apply {
                    msg = "解析错误"
                }
            }
            is ConnectException -> {
                ResponseThrowable(e, ERROR.NETWORK_ERROR).apply {
                    msg = "网络连接异常，请检查您的网络状态"
                }
            }
            is SSLHandshakeException -> {
                ResponseThrowable(e, ERROR.SSL_ERROR).apply {
                    msg = "证书验证失败"
                }
            }
            is ConnectTimeoutException, is SocketTimeoutException -> {
                ResponseThrowable(e, ERROR.TIMEOUT_ERROR).apply {
                    msg = "网络连接超时，请检查您的网络状态，稍后重试"
                }
            }
            is UnknownHostException -> {
                ResponseThrowable(e, ERROR.UNKNOWN_HOST).apply {
                    msg = "网络连接异常，请检查您的网络状态"
                }
            }
            else -> {
                ResponseThrowable(e, ERROR.UNKNOWN).apply {
                    msg = if (e.cause != null) {
                        e.cause?.run {
                            if (message?.isEmpty() == true) localizedMessage else message
                        }
                    } else {
                        "未知错误"
                    }
                }
            }
        }
    }

    class ResponseThrowable(throwable: Throwable?, var result: Int) : Exception(throwable) {
        var msg: String? = null
        var content: String? = null
    }

    class ServerException(var result: Int, var msg: String, var content: String) :
        RuntimeException()

    object ERROR {
        const val UNKNOWN = 1000
        const val PARSE_ERROR = 1001
        const val NETWORK_ERROR = 1002
        const val HTTP_ERROR = 1003
        const val SSL_ERROR = 1005
        const val TIMEOUT_ERROR = 1006
        const val UNKNOWN_HOST = 1007
    }
}