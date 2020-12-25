package com.ghl.opt_track.service

import android.util.Log
import com.ghl.imc.AbsModuleService
import com.ghl.common.service.AutoModuleService
import com.ghl.opt_track.helper.AutoTrackHelper
import com.ghl.opt_track.performance.CrashHandler

class AutoModuleImp : AbsModuleService(), AutoModuleService {
    override fun init() {
        Log.i("ghl", "init 方法执行了")
        AutoTrackHelper.frameDetection()
        AutoTrackHelper.frameDetection2()
        CrashHandler.getinstance().init()
    }
}