package com.ghl.common.intent

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.ghl.common.constants.web.PAGE_WEB_VIEW
import com.ghl.router.lib.Router

class WebViewContract : ActivityResultContract<String, String>() {
    override fun createIntent(context: Context, input: String): Intent {
        val clazz = Router.getTargetClass(PAGE_WEB_VIEW)
        return Intent(context, clazz).apply {
            putExtra("url", input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String {
        return if (resultCode == RESULT_OK) intent?.getStringExtra("url") ?: "" else "www.baidu.com"
    }
}