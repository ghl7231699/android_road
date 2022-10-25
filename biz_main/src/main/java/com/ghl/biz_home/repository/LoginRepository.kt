package com.ghl.biz_home.repository

import com.ghl.biz_home.bean.ArticleListInfo
import com.ghl.biz_home.bean.HomeBannerInfo
import com.ghl.biz_home.service.LoginService
import com.ghl.net.repository.BaseRepository


class LoginRepository : BaseRepository<LoginService>() {

    suspend fun getArticleList(
        page_size: String
    ): ArticleListInfo {
        return service.getArticleList(page_size)
    }

    suspend fun getHomeBanner(): List<HomeBannerInfo> {
        return service.getHomeBanner()
    }

}