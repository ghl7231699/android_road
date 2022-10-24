package com.ghl.biz_home.service

import com.ghl.biz_home.bean.ArticleListInfo
import retrofit2.http.*

interface LoginService {

    // 获取首页文章列表
    @GET("/article/list/{page_size}/json")
    suspend fun getArticleList(
        @Path("page_size") page_size: String
    )
            : ArticleListInfo
}