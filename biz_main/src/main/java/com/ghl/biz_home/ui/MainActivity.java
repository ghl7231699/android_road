package com.ghl.biz_home.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ghl.biz_home.R;
import com.ghl.router_annotation.Route;

import static com.ghl.lib_dirty.constants.main.HomeConstantsKt.ACTIVITY_HOME;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
