package com.ghl.common.application

import android.app.Application

abstract class BaseApp : Application() {
    //初始化module中的application
    abstract fun createApp(application: Application)
}