package com.ghl.lib_dirty.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.ghl.lib_dirty.net.ClassTUtilKt;
import com.ghl.lib_dirty.net.base.BaseViewModel;

public abstract class BaseAacActivity<T extends BaseViewModel> extends BaseActivity {
    protected T mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setBaseViewModel();
        initView();
        subscribeToDefaultEvents();
        subscribeOnViewModelLiveData();
        create(savedInstanceState);

    }

    private void setBaseViewModel() {
        Class<T> tClass = getViewModel();
        if (tClass != null) {
            mViewModel = ViewModelProviders.of(this).get(tClass);
            getLifecycle().addObserver(mViewModel);
        }
    }

    private void subscribeToDefaultEvents() {
        if (mViewModel != null) {
//            mViewModel.getMsgLiveData().observe(this, new Observer<String>() {
//                @Override
//                public void onChanged(@Nullable String errorMsg) {
//                    showMessage(errorMsg);
//                }
//            });
        }
    }

    /**
     * 显示吐司
     *
     * @param message
     */
    protected void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示吐司
     *
     * @param resStr
     */
    protected void showMessage(int resStr) {
        Toast.makeText(this, getString(resStr), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        if (mViewModel != null) {
            mViewModel.onCleared();
            getLifecycle().removeObserver(mViewModel);
            mViewModel = null;
        }
        super.onDestroy();
    }

    protected Class<T> getViewModel() {
        return ClassTUtilKt.getT1(this, 0);
    }

    /**
     * 获取布局Id
     *
     * @return
     */
    protected abstract @LayoutRes
    int getLayoutId();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 注册LiveData
     */
    protected abstract void subscribeOnViewModelLiveData();

    /**
     * 自定义初始化
     *
     * @param savedInstanceState
     */
    protected abstract void create(Bundle savedInstanceState);

    /**
     * 显示loading
     */
    protected abstract void showLoading(boolean isFull);

    /**
     * 隐藏loading
     */
    protected abstract void hideLoading();


}
