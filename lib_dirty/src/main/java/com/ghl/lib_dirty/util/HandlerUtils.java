package com.xzdz.common.tools;

import android.os.Handler;
import android.os.Looper;

public class HandlerUtils {
    private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

    /**
     * 判断当前线程是否主线程
     */
    public static boolean isMainThread() {
        return Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId();
    }

    /**
     * 获取主线程Handler
     */
    public static Handler getMainHandler() {
        return MAIN_HANDLER;
    }
}
