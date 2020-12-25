package com.ghl.common.service

import android.content.Context

interface UserModuleService {
    fun openPageByUrl(context: Context?, url: String?, params: Map<*, *>?): Boolean
}