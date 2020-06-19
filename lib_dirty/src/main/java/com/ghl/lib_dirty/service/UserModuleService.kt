package com.ghl.lib_dirty.service

import android.content.Context

interface UserModuleService {
    fun openPageByUrl(context: Context?, url: String?, params: Map<*, *>?): Boolean
}