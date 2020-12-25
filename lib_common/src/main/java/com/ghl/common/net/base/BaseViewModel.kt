package com.ghl.common.net.base

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ghl.common.net.exception.ExceptionHandle
import com.ghl.common.net.getT
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable

fun <T : ViewModel> createViewModelFragment(fragment: Fragment, clazz: Class<T>): T {
    return ViewModelProvider(fragment.viewModelStore, fragment.defaultViewModelProviderFactory).get(clazz)
}

open class BaseViewModel<T : BaseRepository>(application: Application) : AndroidViewModel(application), LifecycleObserver {
     val mDisposables: CompositeDisposable = CompositeDisposable()
    var mRepository: T? = null

    init {
        mRepository = getT(o = this, i = 0)
    }

    public override fun onCleared() {
        super.onCleared()
        mDisposables.clear()
    }

    protected fun <T> createObserver(next: ((T) -> Unit)?, errorBiz: ((ExceptionHandle.ResponseThrowable) -> Unit)? = null, complete: (() -> Unit)? = null): Observer<T> {
        val observer = FinalObserver(next, errorBiz, complete)
        mDisposables.add(observer)
        return observer
    }
}