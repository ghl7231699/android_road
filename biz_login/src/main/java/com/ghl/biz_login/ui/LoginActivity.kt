package com.ghl.biz_login.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ghl.biz_login.LoginViewModel
import com.ghl.biz_login.R
import com.ghl.common.base.BaseAacActivity
import com.ghl.common.constants.login.LOGIN_MAIN_PAGE
import com.ghl.router_annotation.Route

@Route(LOGIN_MAIN_PAGE)
class LoginActivity : BaseAacActivity<LoginViewModel>() {

    override val layoutId: Int = R.layout.activity_login_layout

    override fun initView() {
    }

    override fun subscribeOnViewModelLiveData() {
        mViewModel?.userLiveData?.observe(this) {
            Log.e("ghl", it.data.toString())
        }

    }

    override fun create(savedInstanceState: Bundle?) {
        mViewModel?.requestLogin("Derry-vip", "123456")
    }

    override fun showLoading(isFull: Boolean) {
    }

    override fun hideLoading() {
    }

}


