package com.ghl.common.base

import android.content.Context
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    protected var mActivity: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setBaseConfig()
        super.onCreate(savedInstanceState)
        mActivity = this
    }

    private fun setBaseConfig() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
    }

}