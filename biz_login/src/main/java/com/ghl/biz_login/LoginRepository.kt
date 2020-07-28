package com.ghl.biz_login

import com.ghl.lib_dirty.net.base.BaseRepository
import io.reactivex.Observable

class LoginRepository : BaseRepository() {
    private val mService = createService(LoginService::class.java)

    fun getList(): Observable<ArticleListInfo> {
        return mService.getList()
    }

}