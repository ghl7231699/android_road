package com.ghl.biz_home.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ghl.lib_dirty.constants.main.ACTIVITY_HOME
import com.ghl.lib_dirty.constants.main.ACTIVITY_MAIN
import com.ghl.router.lib.Router

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Router.with(this).target(ACTIVITY_HOME).start()
        finish()
    }
}