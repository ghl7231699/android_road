package com.ghl.net.base.interceptor

import android.text.TextUtils
import com.ghl.net.base.interceptor.JSONFormatter.Companion.formatJSON
import okhttp3.*
import okhttp3.internal.http.HttpHeaders
import okhttp3.internal.platform.Platform
import okio.Buffer
import java.io.EOFException
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.charset.UnsupportedCharsetException
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class LogInterceptor @JvmOverloads constructor(
    private val logger: Logger = object : Logger {
        override fun log(message: String?) {
            Platform.get().log(Platform.WARN, message, null)
        }
    }
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val id = ID_GENERATOR.incrementAndGet()
        // request 处理
        run {
            val logPrefix = "[$id request]"
            val requestBody = request.body()
            val hasRequestBody = requestBody != null
            val connection = chain.connection()
            val protocol = if (connection != null) connection.protocol() else Protocol.HTTP_1_1
            var requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' '

            // post请求参数打印
            var requestString = ""
            if (hasRequestBody && "POST" == request.method()) {
                val buffer = Buffer()
                try {
                    requestBody!!.writeTo(buffer)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                var charset = StandardCharsets.UTF_8
                val contentType = requestBody!!.contentType()
                if (contentType != null) {
                    charset = contentType.charset(charset)
                    if (charset == null) {
                        charset = StandardCharsets.UTF_8
                    }
                }
                requestString = buffer.readString(charset)
                requestStartMessage += " RequestParams:\n$requestString\n"
            }
            requestStartMessage += protocol
            logger.log(logPrefix + requestStartMessage)
            if (!TextUtils.isEmpty(requestString)) {
                val formatStr = formatJSON(requestString)
                if (!TextUtils.isEmpty(formatStr)) {
                    logger.log("""$logPrefix$formatStr""".trimIndent())
                }
            }
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody!!.contentType() != null) {
                    logger.log(logPrefix + "Content-Type: " + requestBody.contentType())
                }
                if (requestBody.contentLength() != -1L) {
                    logger.log(logPrefix + "Content-Length: " + requestBody.contentLength())
                }
            }
            val headers = request.headers()
            var i = 0
            val count = headers.size()
            while (i < count) {
                val name = headers.name(i)
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equals(
                        name,
                        ignoreCase = true
                    ) && !"Content-Length".equals(name, ignoreCase = true)
                ) {
                    logger.log(logPrefix + name + ": " + headers.value(i))
                }
                i++
            }
            if (!hasRequestBody) {
                logger.log(logPrefix + "--> END " + request.method())
            } else if (bodyEncoded(request.headers())) {
                logger.log(logPrefix + "--> END " + request.method() + " (encoded body omitted)")
            } else {
                val buffer = Buffer()
                var charset = UTF8
                val contentType = requestBody!!.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }
                if (isPlaintext(buffer) && charset != null) {
                    val bufferString = buffer.readString(charset)
                    logger.log(logPrefix + bufferString)
                    if (contentType != null && "json" == contentType.subtype()) {
                        logger.log("""$logPrefix${formatJSON(bufferString)}""".trimIndent())
                    }
                    logger.log(
                        logPrefix + "--> END " + request.method()
                                + " (" + requestBody.contentLength() + "-byte body)"
                    )
                } else {
                    logger.log(
                        logPrefix + "--> END " + request.method() + " (binary "
                                + requestBody.contentLength() + "-byte body omitted)"
                    )
                }
            }
        }
        run {
            val logPrefix = "[$id response]"
            val startNs = System.nanoTime()
            val response: Response = try {
                chain.proceed(request)
            } catch (e: Exception) {
                logger.log("$logPrefix<-- HTTP FAILED: $e")
                throw e
            }
            val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
            val responseBody = response.body() ?: return response
            val contentLength = responseBody.contentLength()
            logger.log(
                logPrefix + "<-- " + response.code() + ' ' + response.message() + ' ' + response.request()
                    .url() + " (" + tookMs + "ms" + "" + ')'
            )
            val headers = response.headers()
            var i = 0
            val count = headers.size()
            while (i < count) {
                logger.log(logPrefix + headers.name(i) + ": " + headers.value(i))
                i++
            }
            if (!HttpHeaders.hasBody(response)) {
                logger.log("$logPrefix<-- END HTTP")
            } else if (bodyEncoded(response.headers())) {
                logger.log("$logPrefix<-- END HTTP (encoded body omitted)")
            } else {
                val source = responseBody.source()
                source.request(Long.MAX_VALUE) // Buffer the entire body.
                val buffer = source.buffer
                var charset = UTF8
                val contentType = responseBody.contentType()
                if (contentType != null) {
                    charset = try {
                        contentType.charset(UTF8)
                    } catch (e: UnsupportedCharsetException) {
                        logger.log(logPrefix + "")
                        logger.log(logPrefix + "Couldn't decode the response body; charset is likely malformed.")
                        logger.log("$logPrefix<-- END HTTP")
                        return response
                    }
                }
                if (!isPlaintext(buffer)) {
                    logger.log(logPrefix + "<-- END HTTP (binary " + buffer.size() + "-byte body omitted)")
                    return response
                }
                if (contentLength != 0L && charset != null) {
                    val bufferString = buffer.clone().readString(charset)
                    logger.log(logPrefix + bufferString)
                    if (contentType != null && "json" == contentType.subtype()) {
                        logger.log(
                            """
    $logPrefix
    ${formatJSON(bufferString)}
    """.trimIndent()
                        )
                    }
                }
                logger.log(logPrefix + "<-- END HTTP (" + buffer.size() + "-byte body)")
            }
            return response
        }
    }

    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"]
        return contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true)
    }

    interface Logger {
        fun log(message: String?)
    }

    companion object {
        private val UTF8 = StandardCharsets.UTF_8
        private val ID_GENERATOR = AtomicInteger(0)
        fun isPlaintext(buffer: Buffer): Boolean {
            return try {
                val prefix = Buffer()
                val byteCount = if (buffer.size() < 64) buffer.size() else 64
                buffer.copyTo(prefix, 0, byteCount)
                for (i in 0..15) {
                    if (prefix.exhausted()) {
                        break
                    }
                    val codePoint = prefix.readUtf8CodePoint()
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false
                    }
                }
                true
            } catch (e: EOFException) {
                false
            }
        }
    }
}