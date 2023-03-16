package com.ghl.biz_home.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.ghl.biz_home.R
import com.ghl.biz_home.viewmodel.HomeViewModel
import com.ghl.common.base.BaseAacActivity
import com.ghl.common.constants.main.ACTIVITY_HOME_V2
import com.ghl.common.intent.WebViewContract
import com.ghl.router_annotation.Route

@Route(ACTIVITY_HOME_V2)
class HomeActivity : BaseAacActivity<HomeViewModel>() {

    private var mResult = registerForActivityResult(WebViewContract()) {
        println("我是返回的值?${it}")
    }

    private var mAdapter: HomeAdapter? = null
    private val rv: RecyclerView by lazy {
        findViewById(R.id.rv_home_page)
    }

    private val bannerRv: RecyclerView by lazy {
        findViewById(R.id.tv_home_page_banner)
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


        bannerRv.run {
            layoutManager =
                LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
            PagerSnapHelper().attachToRecyclerView(this)
        }
    }

    override fun subscribeOnViewModelLiveData() {
        mViewModel?.articleListData?.observe(this) { it ->
            it?.run {
                mAdapter?.run {
                    itemClick = {
                        mResult.launch(it)
                    }
                    notify(it.datas.toMutableList())
                }
            }
        }
        mViewModel?.homeBannerLiveData?.observe(this) { it ->
            it?.run {
                bannerRv.adapter = HomeBannerAdapter().apply {
                    itemClick = {
                        mResult.launch(it)
                    }
                    notify(it)

                }
            }
        }
    }

    override fun create(savedInstanceState: Bundle?) {
        mViewModel?.getHomeBanner()
        mViewModel?.getArticleList("0")?.run {
            mAdapter?.run {
                itemClick = {
                    mResult.launch(it)
                }
                notify(datas.toMutableList())
            }
        }
        mViewModel?.setListener(url = "https://www.baidu.com", callBack = {code,content->
            if (code==0){
                println("result is $content")
            }

        })


        mViewModel?.apply {  }
    }

    override fun showLoading(isFull: Boolean) {
//        showLoading(true)
    }

    override fun hideLoading() {
//        hideLoading()
    }
}