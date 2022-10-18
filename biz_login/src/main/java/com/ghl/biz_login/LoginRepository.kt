package com.ghl.biz_login

import com.ghl.biz_login.entity.LoginRegisterResponse
import com.ghl.biz_login.entity.LoginRegisterResponseWrapper
import com.ghl.net.repository.BaseRepository

class LoginRepository : BaseRepository<LoginService>() {

    suspend fun login(
        userName: String,
        userPwd: String
    ): LoginRegisterResponseWrapper<LoginRegisterResponse> {
        return service.login(userName, userPwd)
    }


}