package com.ghl.lib_dirty.net.base

import com.ghl.lib_dirty.net.exception.ExceptionHandle
import com.ghl.lib_dirty.util.ToastUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


private const val STATUS_FAIL_OVERDUE = 4203 //账号登录信息过期，请重新登录

private const val STATUS_FAIL_CAPTCHA_INTERCEPT = 6000 // 需要验证码激活

abstract class BaseObserver<T> : DisposableObserver<T>() {

    protected var mComplete: (() -> Unit)? = null
    protected var mNext: ((T) -> Unit)? = null
    protected var mErrorBiz: ((ExceptionHandle.ResponseThrowable) -> Unit)? = null
    override fun onComplete() {
        mComplete?.invoke()
    }

    override fun onNext(t: T) {
        mNext?.invoke(t)
    }

    private fun doOnError(e: ExceptionHandle.ResponseThrowable) {
        mErrorBiz?.invoke(e)
    }

    override fun onError(e: Throwable) {
        if (e is ExceptionHandle.ResponseThrowable) {
            if (!isGlobalInterceptError(e.result) && !onInterceptError(e.result, e.msg)) {
                ToastUtils.show(e.msg)
            }
            doOnError(e)
        }
    }

    protected open fun onInterceptError(errorCode: Int, errorMsg: String?): Boolean {
        return false
    }

    private fun isGlobalInterceptError(errorCode: Int): Boolean {
        return errorCode == STATUS_FAIL_OVERDUE || errorCode ==
                STATUS_FAIL_CAPTCHA_INTERCEPT || errorCode == ExceptionHandle.ERROR.UNKNOWN
    }
}

class FinalObserver<T> constructor(next: ((T) -> Unit)? = null, errorBiz: ((ExceptionHandle.ResponseThrowable) -> Unit)? = null,
                                   complete: (() -> Unit)? = null) : BaseObserver<T>() {
    init {
        this.mComplete = complete
        this.mNext = next
        this.mErrorBiz = errorBiz
    }
}

fun <T> Observable<T>.subscribe(compositeDisposable: CompositeDisposable, observable: BaseObserver<T>): BaseObserver<T> {
    compositeDisposable.add(observable)
    return compose(RxScheduler.IO_MAIN<T>())
            .onErrorResumeNext(RxScheduler.HandlerException<T>())
            .subscribeWith(observable)
}

fun <T> Observable<T>.bizSubscribe(compositeDisposable: CompositeDisposable, next: ((T) -> Unit)? = null, errorBiz: ((ExceptionHandle.ResponseThrowable) -> Unit)? = null) {
    subscribe(compositeDisposable, FinalObserver(next, errorBiz, null))
}

fun <T> Observable<T>.gotoUI(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}



