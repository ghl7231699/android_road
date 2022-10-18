//package com.ghl.biz_login.ui
//
//import android.os.Bundle
//import androidx.lifecycle.Observer
//import com.ghl.biz_login.LoginViewModel
//import com.ghl.biz_login.R
//import com.ghl.common.base.BaseAacActivity
//import com.ghl.common.constants.login.LOGIN_TEST_PAGE
//import com.ghl.common.util.ToastUtils
//import com.ghl.router_annotation.Route
//
//@Route(LOGIN_TEST_PAGE)
//class LoginTestActivity : BaseAacActivity<LoginViewModel>() {
//    override fun getLayoutId(): Int {
//        return R.layout.activity_login_test_layout
//    }
//
//    override fun initView() {
//    }
//
//    override fun subscribeOnViewModelLiveData() {
//        mViewModel.articleListData.observe(this, Observer {
//            val toString = it.toString()
//            ToastUtils.show(toString)
//        })
//    }
//
//    override fun create(savedInstanceState: Bundle?) {
//        mViewModel.getList()
//    }
//
//    override fun showLoading(isFull: Boolean) {
//    }
//
//    override fun hideLoading() {
//    }
//}