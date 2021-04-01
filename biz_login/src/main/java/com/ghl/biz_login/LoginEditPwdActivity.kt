package com.ghl.biz_login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.ghl.common.constants.login.LOGIN_EDIT_PWD_PAGE
import com.ghl.router_annotation.Route

@Route(LOGIN_EDIT_PWD_PAGE)
class LoginEditPwdActivity : AppCompatActivity() {
    private val mConfirm: CheckBox by lazy {
        findViewById<CheckBox>(R.id.cb_pwd_login_agreement)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_edit_layout)

        mConfirm.setOnLongClickListener {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra("result", "Hello  依然范特西，我是回来的数据！")
            })
            finish()
            true
        }
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra("result", "Hello  依然范特西，我是回来的数据！")
        })
        super.onBackPressed()
    }
}


