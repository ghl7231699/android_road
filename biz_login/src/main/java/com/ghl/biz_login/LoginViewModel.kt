package com.ghl.biz_login

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ghl.common.net.base.BaseViewModel
import com.ghl.common.net.base.bizSubscribe
import com.ghl.common.util.ToastUtils

class LoginViewModel(application: Application) : BaseViewModel<LoginRepository>(application) {
    val articleListData: MutableLiveData<ArticleListInfo> by lazy {
        MutableLiveData<ArticleListInfo>()
    }

    fun getList() {
//        mRepository?.getList()
//                ?.gotoUI()
//                ?.subscribe(object : BaseObserver<ArticleListInfo>() {
//                    override fun onNext(t: ArticleListInfo) {
//                        super.onNext(t)
//                        articleListData.postValue(t)
//                    }
//
//                    override fun onError(e: Throwable) {
//                        super.onError(e)
//                    }
//                })

        //TODO  添加conver 转换成BaseBean类型

        mRepository?.getList()
                ?.bizSubscribe(mDisposables, next = {
                    articleListData.value = it
                }, errorBiz = {
                    ToastUtils.show("我是错误")
                })
    }
}