package com.ghl.lib_dirty.service

import android.app.Application
import android.content.Context

interface FlutterModuleService {
    fun openPageByUrl(context: Context?, url: String?, params: Map<String?, *>?): Boolean
    fun getPageTag(key: String): String
    fun initFlutterBoost(application: Application)
    fun initFlutter(application: Application)
}