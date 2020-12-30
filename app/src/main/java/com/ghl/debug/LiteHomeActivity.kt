package com.ghl.debug

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ghl.R
import com.ghl.common.constants.main.ACTIVITY_HOME
import com.ghl.router.lib.Router

/**
 * 组合模式下 程序入口
 */
class LiteHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lite_home)

        findViewById<Button>(R.id.btn_main).setOnClickListener {
            Router.with(this@LiteHomeActivity)
                    .target(ACTIVITY_HOME)
                    .start()
        }
    }
}