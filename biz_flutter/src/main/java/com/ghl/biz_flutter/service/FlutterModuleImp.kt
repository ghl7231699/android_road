package com.ghl.biz_flutter.service

import android.content.Context
import com.ghl.biz_flutter.ui.PageRouter
import com.ghl.imc.AbsModuleService
import com.ghl.lib_dirty.service.FlutterModuleService

class FlutterModuleImp : AbsModuleService(), FlutterModuleService {
    override fun openPageByUrl(context: Context?, url: String?, params: Map<*, *>?): Boolean {
        return PageRouter.openPageByUrl(context, url, params)
    }
}