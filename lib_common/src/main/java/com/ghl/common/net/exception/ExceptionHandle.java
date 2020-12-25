/*
 * Copyright 2018 location
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ghl.common.net.exception;

import android.net.ParseException;
import android.text.TextUtils;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

public class ExceptionHandle {
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponseThrowable handleException(Throwable e) {
        ResponseThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseThrowable(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.msg = "网络错误: " + httpException.code();
                    break;
            }
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponseThrowable(resultException, resultException.result);
            ex.content = resultException.content;
            ex.msg = resultException.msg;
        } else if (e instanceof ResponseThrowable) {
            ex = (ResponseThrowable) e;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponseThrowable(e, ERROR.PARSE_ERROR);
            ex.msg = "解析错误";
        } else if (e instanceof ConnectException) {
            ex = new ResponseThrowable(e, ERROR.NETWORK_ERROR);
            ex.msg = "网络连接异常，请检查您的网络状态";
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponseThrowable(e, ERROR.SSL_ERROR);
            ex.msg = "证书验证失败";
        } else if (e instanceof ConnectTimeoutException) {
            ex = new ResponseThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.msg = "网络连接超时，请检查您的网络状态，稍后重试";
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new ResponseThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.msg = "网络连接超时，请检查您的网络状态，稍后重试";
        } else if (e instanceof UnknownHostException) {
            ex = new ResponseThrowable(e, ERROR.UNKNOWN_HOST);
            ex.msg = "网络连接异常，请检查您的网络状态";
        } else {
            ex = new ResponseThrowable(e, ERROR.UNKNOWN);
            Throwable cause = e.getCause();
            if (cause != null) {
                String message = cause.getMessage();
                if (TextUtils.isEmpty(message)) {
                    message = cause.getLocalizedMessage();
                }
                ex.msg = message;
            } else {
                ex.msg = "未知错误";
            }

        }
        return ex;
    }

    public static class ResponseThrowable extends Exception {

        public int result;
        public String msg;
        public String content;

        public ResponseThrowable(Throwable throwable, int result) {
            super(throwable);
            this.result = result;

        }
    }

    public static class ServerException extends RuntimeException {

        public int result;
        public String msg;
        public String content;

        public ServerException(int code, String msg, String content) {
            this.result = code;
            this.msg = msg;
            this.content = content;
        }
    }

    public static class ERROR {
        public static final int UNKNOWN = 1000;
        public static final int PARSE_ERROR = 1001;
        public static final int NETWORK_ERROR = 1002;
        public static final int HTTP_ERROR = 1003;
        public static final int SSL_ERROR = 1005;
        public static final int TIMEOUT_ERROR = 1006;
        public static final int UNKNOWN_HOST = 1007;
    }
}

