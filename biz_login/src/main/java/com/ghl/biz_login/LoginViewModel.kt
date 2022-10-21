package com.ghl.biz_login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ghl.biz_login.entity.LoginRegisterResponse
import com.ghl.net.viewmodel.BaseViewModel
import kotlinx.coroutines.launch


class LoginViewModel : BaseViewModel<LoginRepository>() {
    val articleListData: MutableLiveData<ArticleListInfo> by lazy {
        MutableLiveData<ArticleListInfo>()
    }

    var userLiveData = MutableLiveData<LoginRegisterResponse>()


    fun requestLogin(username: String, userPwd: String) {
        viewModelScope.launch {  // viewModelScope是main线程，这个是特殊点，其他的默认是Default
            // 左边：主线程                  右边：异步线程
            userLiveData.value = mRepository.login(username, userPwd)
        }
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

//        mRepository?.getList()
//                ?.bizSubscribe(mDisposables, next = {
//                    articleListData.value = it
//                }, errorBiz = {
//                    ToastUtils.show("我是错误")
//                })
    }
}