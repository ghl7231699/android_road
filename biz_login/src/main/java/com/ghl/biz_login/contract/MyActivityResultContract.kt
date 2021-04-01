package com.ghl.biz_login.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.ghl.common.constants.login.LOGIN_EDIT_PWD_PAGE
import com.ghl.router.lib.Router

class MyActivityResultContract : ActivityResultContract<String, String>() {
    override fun createIntent(context: Context, input: String?): Intent {
        return Intent(context, Router.getTargetClass(LOGIN_EDIT_PWD_PAGE)).apply {
            putExtra("name", input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        val data = intent?.getStringExtra("result")
        return if (resultCode == Activity.RESULT_OK && data != null) data
        else null
    }

}