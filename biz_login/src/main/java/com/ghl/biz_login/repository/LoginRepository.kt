package com.ghl.biz_login.repository

import com.ghl.biz_login.entity.ArticleListInfo
import com.ghl.biz_login.entity.LoginRegisterResponse
import com.ghl.biz_login.service.LoginService
import com.ghl.net.repository.BaseRepository

class LoginRepository : BaseRepository<LoginService>() {

    suspend fun login(
        userName: String,
        userPwd: String
    ): LoginRegisterResponse {
        return service.login(userName, userPwd)
    }


    suspend fun getArticleList(
        page_size: String
    ): ArticleListInfo {
        return service.getArticleList(page_size)
    }

}