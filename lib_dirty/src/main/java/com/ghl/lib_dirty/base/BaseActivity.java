package com.ghl.lib_dirty.base;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setBaseConfig();
        super.onCreate(savedInstanceState);
        mActivity = this;

    }
    private void setBaseConfig() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}