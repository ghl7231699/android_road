//package com.ghl.common.base
//
//import android.os.Bundle
//import android.widget.Toast
//import androidx.annotation.LayoutRes
//import androidx.lifecycle.ViewModelProviders
//import com.ghl.common.net.base.BaseViewModel
//import com.ghl.common.net.getT1
//
//abstract class OldBaseAacActivity<T : BaseViewModel<*>?> : BaseActivity() {
//    protected var mViewModel: T? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(layoutId)
//        setBaseViewModel()
//        initView()
//        subscribeToDefaultEvents()
//        subscribeOnViewModelLiveData()
//        create(savedInstanceState)
//    }
//
//    private fun setBaseViewModel() {
//        val tClass = viewModel
//        if (tClass != null) {
//            mViewModel = ViewModelProviders.of(this).get(tClass)
//            lifecycle.addObserver(mViewModel)
//        }
//    }
//
//    private fun subscribeToDefaultEvents() {
//        if (mViewModel != null) {
////            mViewModel.getMsgLiveData().observe(this, new Observer<String>() {
////                @Override
////                public void onChanged(@Nullable String errorMsg) {
////                    showMessage(errorMsg);
////                }
////            });
//        }
//    }
//
//    /**
//     * 显示吐司
//     *
//     * @param message
//     */
//    protected fun showMessage(message: String?) {
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
//    }
//
//    /**
//     * 显示吐司
//     *
//     * @param resStr
//     */
//    protected fun showMessage(resStr: Int) {
//        Toast.makeText(this, getString(resStr), Toast.LENGTH_LONG).show()
//    }
//
//    public override fun onDestroy() {
//        if (mViewModel != null) {
//            mViewModel!!.onCleared()
//            lifecycle.removeObserver(mViewModel!!)
//            mViewModel = null
//        }
//        super.onDestroy()
//    }
//
//    protected val viewModel: Class<T>?
//        protected get() = getT1(this, 0)
//
//    /**
//     * 获取布局Id
//     *
//     * @return
//     */
//    @get:LayoutRes
//    protected abstract val layoutId: Int
//
//    /**
//     * 初始化视图
//     */
//    protected abstract fun initView()
//
//    /**
//     * 注册LiveData
//     */
//    protected abstract fun subscribeOnViewModelLiveData()
//
//    /**
//     * 自定义初始化
//     *
//     * @param savedInstanceState
//     */
//    protected abstract fun create(savedInstanceState: Bundle?)
//
//    /**
//     * 显示loading
//     */
//    protected abstract fun showLoading(isFull: Boolean)
//
//    /**
//     * 隐藏loading
//     */
//    protected abstract fun hideLoading()
//}