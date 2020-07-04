package com.ghl.biz_home.ui

import retrofit2.Call
import retrofit2.http.GET

data class ResultBean(
        val basic: Basic?,
        val errorCode: Int?,
        val query: String?,
        val translation: List<String>?,
        val web: List<Web>?
)

data class Basic(
        val explains: List<String>?,
        val phonetic: String?
)

data class Web(
        val key: String?,
        val value: List<String>?
)

interface HomeService {
    @GET("openapi.do?keyfrom=Yanzhikai&key=2032414398&type=data&doctype=json&version=1.1&q=car")
    fun getCall(): Call<ResultBean>
}