package com.ghl.biz_login.service

import com.ghl.biz_login.entity.ArticleListInfo
import com.ghl.biz_login.entity.LoginRegisterResponse
import io.reactivex.Observable
import retrofit2.http.*

interface LoginService {
    @GET("article/list/1/json")
    fun getList(): Observable<ArticleListInfo>


    @POST("/user/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    )
            : LoginRegisterResponse

    // 获取首页文章列表
    @GET("/article/list/{page_size}/json")
    suspend fun getArticleList(
        @Path("page_size") page_size: String
    )
            : ArticleListInfo
}