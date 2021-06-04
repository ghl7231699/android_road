package com.ghl.biz_login

import android.app.Application
import android.util.Log

open class LoginApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.e("ghl", "login module  initialization!")
    }
}