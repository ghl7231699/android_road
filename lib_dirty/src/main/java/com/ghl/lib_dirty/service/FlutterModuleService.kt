package com.ghl.lib_dirty.service

import android.content.Context

interface FlutterModuleService {
    fun openPageByUrl(context: Context?, url: String?, params: Map<String?, *>?): Boolean
    fun getPageTag(key: String): String
}