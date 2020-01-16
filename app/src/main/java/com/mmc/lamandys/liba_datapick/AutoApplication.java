package com.mmc.lamandys.liba_datapick;

import android.app.Application;
import android.content.Context;

import com.mmc.lamandys.liba_datapick.helper.AutoTrackHelper;

/**
 * Author:xishuang
 * Date:2018.03.01
 * Des:全局Context获取
 */
public class AutoApplication extends Application {
    private static Context mContext;
    private static AutoApplication mApp;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mApp = this;
//        AutoTrackHelper.frameDetection();
        AutoTrackHelper.frameDetection2();
        CrashHandler.getinstance().init();
    }

    public static AutoApplication getInstance() {
        return mApp;
    }

    public static Context getApplicationContex() {
        return mContext;
    }

}
