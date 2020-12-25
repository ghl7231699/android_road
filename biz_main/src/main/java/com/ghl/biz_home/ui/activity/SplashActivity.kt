package com.ghl.biz_home.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ghl.common.constants.main.ACTIVITY_HOME
import com.ghl.router.lib.Router
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainScope().launch {
            delay(1000)
            Router.with(this@SplashActivity).target(ACTIVITY_HOME).start()
            finish()
        }
    }
}