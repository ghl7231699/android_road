package com.xzdz.common.base;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.ParameterizedType;


public abstract class BaseAacFragment<T extends BaseViewModel> extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void lazyLoadData() {

    }

    protected T viewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseViewModel(getTClass());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void setBaseViewModel(@NonNull Class<T> modelClass) {
        if (getActivity() != null) {
            viewModel = ViewModelProviders.of(this).get(modelClass);
            getLifecycle().addObserver(viewModel);
        }
    }

    private Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void onDestroy() {
        viewModel.onCleared();
        getLifecycle().removeObserver(viewModel);
        viewModel = null;
        super.onDestroy();
    }
}
