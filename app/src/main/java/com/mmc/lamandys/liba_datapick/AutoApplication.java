package com.mmc.lamandys.liba_datapick;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.mmc.lamandys.liba_datapick.helper.AutoTrackHelper;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;

import static com.mmc.lamandys.liba_datapick.Constanats.CACHED_FLUTTER_ENGINE;

@SuppressLint("StaticFieldLeak")
public class AutoApplication extends Application {
    private static Context mContext;
    private static AutoApplication mApp;
    public FlutterEngine mFlutterEngine;

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

        // Somewhere in your app, before your FlutterFragment is needed, like in the
        // Application class ...
        // Instantiate a FlutterEngine.
        mFlutterEngine = new FlutterEngine(mContext);
        // Start executing Dart code in the FlutterEngine.
        mFlutterEngine.getDartExecutor().executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        );

        // Cache the pre-warmed FlutterEngine to be used later by FlutterFragment.
        FlutterEngineCache.getInstance().put(CACHED_FLUTTER_ENGINE, mFlutterEngine);
    }

    public static AutoApplication getInstance() {
        return mApp;
    }

    public static Context getApplicationContex() {
        return mContext;
    }

}
