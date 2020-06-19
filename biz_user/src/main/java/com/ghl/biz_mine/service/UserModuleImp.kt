package com.ghl.biz_mine.service

import android.content.Context
import com.ghl.imc.AbsModuleService
import com.ghl.lib_dirty.service.UserModuleService

class UserModuleImp : AbsModuleService(), UserModuleService {
    override fun openPageByUrl(context: Context?, url: String?, params: Map<*, *>?): Boolean {
        return false
    }
}