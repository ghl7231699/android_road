package com.ghl.common.service

import android.content.Context

interface LoginModuleService {
    fun openPageByUrl(context: Context?, url: String?, params: Map<*, *>?): Boolean
}