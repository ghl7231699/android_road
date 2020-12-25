package com.ghl.biz_login.service

import android.content.Context
import com.ghl.imc.AbsModuleService
import com.ghl.common.service.LoginModuleService

class LoginModuleImp : AbsModuleService(), LoginModuleService {
    override fun openPageByUrl(context: Context?, url: String?, params: Map<*, *>?): Boolean {
        return false
    }
}