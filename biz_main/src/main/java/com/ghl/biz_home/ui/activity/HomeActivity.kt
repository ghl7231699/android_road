package com.ghl.biz_home.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ghl.biz_home.R
import com.ghl.biz_home.viewmodel.HomeViewModel
import com.ghl.common.base.BaseAacActivity
import com.ghl.common.base.WebViewActivity
import com.ghl.common.constants.main.ACTIVITY_HOME_V2
import com.ghl.router_annotation.Route

@Route(ACTIVITY_HOME_V2)
class HomeActivity : BaseAacActivity<HomeViewModel>() {

    private var mAdapter: HomeAdapter? = null
    private val rv: RecyclerView by lazy {
        findViewById(R.id.rv_home_page)
    }

    override val layoutId: Int
        get() = R.layout.fragment_home

    override fun initView() {
        rv.run {
            layoutManager =
                LinearLayoutManager(this@HomeActivity, LinearLayoutManager.VERTICAL, false)
            mAdapter = HomeAdapter()
            adapter = mAdapter
        }
    }

    override fun subscribeOnViewModelLiveData() {
        mViewModel?.articleListData?.observe(this) {
            it?.run {
                mAdapter?.run {
                    itemClick = {
//                        Router.with(this@HomeActivity).target()
                        startActivity(Intent(this@HomeActivity, WebViewActivity::class.java).apply {
                            putExtra(WebViewActivity.KEY_URL, it)
                        })
                    }
                    notify(it.datas.toMutableList())
                }
            }
        }
    }

    override fun create(savedInstanceState: Bundle?) {
        mViewModel?.getArticleList("0")
    }

    override fun showLoading(isFull: Boolean) {
//        showLoading(true)
    }

    override fun hideLoading() {
//        hideLoading()
    }
}