package com.mmc.lamandys.liba_datapick.base;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.Window;

public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
