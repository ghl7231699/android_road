package com.ghl.biz_login.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.ghl.biz_login.R
import com.ghl.biz_login.viewmodel.LoginViewModel
import com.ghl.common.base.BaseAacActivity
import com.ghl.common.constants.login.LOGIN_MAIN_PAGE
import com.ghl.common.constants.main.ACTIVITY_HOME_V2
import com.ghl.router.lib.Router
import com.ghl.router_annotation.Route

@Route(LOGIN_MAIN_PAGE)
class LoginActivity : BaseAacActivity<LoginViewModel>() {

    override val layoutId: Int = R.layout.activity_login_layout

    private val tvName: TextView by lazy {
        findViewById(R.id.tv_name)
    }

    private val button: Button by lazy {
        findViewById(R.id.get)
    }

    private val article: Button by lazy {
        findViewById(R.id.getArticleList)
    }

    override fun initView() {
        button.setOnClickListener {
            mViewModel?.requestLogin("Derry-vip", "123456")
        }

        article.setOnClickListener {
            Router.with(this).target(ACTIVITY_HOME_V2).start()
        }
    }

    override fun subscribeOnViewModelLiveData() {
        mViewModel?.userLiveData?.observe(this) {
            it.run {
                tvName.text = nickname
            }
            Log.e("ghl", it.toString())
        }

        mViewModel?.articleListData?.observe(this) {
            if (it == null) return@observe
            Log.e("ghl", it.datas.toString())
        }
    }

    override fun create(savedInstanceState: Bundle?) {

    }

    override fun showLoading(isFull: Boolean) {
    }

    override fun hideLoading() {
    }

}

