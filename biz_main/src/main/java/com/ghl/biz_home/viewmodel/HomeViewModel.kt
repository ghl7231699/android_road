package com.ghl.biz_home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ghl.biz_home.bean.ArticleListInfo
import com.ghl.biz_home.bean.HomeBannerInfo
import com.ghl.biz_home.repository.LoginRepository
import com.ghl.net.coroutine.cwlLaunch
import com.ghl.net.viewmodel.BaseViewModel

class HomeViewModel : BaseViewModel<LoginRepository>() {
    val articleListData: MutableLiveData<ArticleListInfo> by lazy {
        MutableLiveData<ArticleListInfo>()
    }
    val homeBannerLiveData: MutableLiveData<List<HomeBannerInfo>> by lazy {
        MutableLiveData<List<HomeBannerInfo>>()
    }

    fun getArticleList(pageSize: String) {
        viewModelScope.cwlLaunch<ArticleListInfo> {
            onRequest = {
                mRepository.getArticleList(pageSize)
            }

            onSuccess = {
                articleListData.value = it
            }

            onError = {
                println(it.stackTraceToString())
            }
        }
    }

    fun getHomeBanner() {
        viewModelScope.cwlLaunch<List<HomeBannerInfo>> {
            onRequest = {
                mRepository.getHomeBanner()
            }

            onSuccess = {
                homeBannerLiveData.value = it
            }

            onError = {
                println(it.stackTraceToString())
            }
        }
    }

}