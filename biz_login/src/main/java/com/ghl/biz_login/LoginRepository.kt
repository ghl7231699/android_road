package com.ghl.biz_login

import com.ghl.biz_login.entity.LoginRegisterResponse
import com.ghl.net.repository.BaseRepository

class LoginRepository : BaseRepository<LoginService>() {

    suspend fun login(
        userName: String,
        userPwd: String
    ): LoginRegisterResponse {
        return service.login(userName, userPwd)
    }


}