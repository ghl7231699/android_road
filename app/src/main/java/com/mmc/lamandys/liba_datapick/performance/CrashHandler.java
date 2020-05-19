package com.mmc.lamandys.liba_datapick.performance;

import android.content.Context;
import android.content.Intent;
import android.os.Process;

import com.mmc.lamandys.liba_datapick.AutoApplication;
import com.mmc.lamandys.liba_datapick.activity.MainActivity;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 在Application中统一捕获异常
 */
public class CrashHandler implements UncaughtExceptionHandler {

    /**
     * CrashHandler实例
     */
    private static CrashHandler instance = null;

    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler mDefaultHandler = null;

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getinstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    /**
     * 初始化, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     */
    public void init() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        handleException(ex);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 关闭应用中各种activity和服务
        if (needRestart(ex)) {
            AutoApplication context = AutoApplication.Companion.getInstance();
            restartAPP(context);
        } else {
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }


    /**
     * 是否需要重启
     *
     * @param ex
     * @return
     */
    private boolean needRestart(Throwable ex) {

        boolean exFlag = false;
        if (ex != null) {
            exFlag = ex instanceof SecurityException;
        }

        boolean causeFlag = false;
        Throwable cause = ex.getCause();
        if (cause != null) {
            causeFlag = cause instanceof SecurityException;
        }
        return exFlag || causeFlag;
    }

    /**
     * 重启APP
     */
    private void restartAPP(Context cx) {
        Intent intent = new Intent(cx, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        cx.startActivity(intent);
        Process.killProcess(Process.myPid());
        System.exit(1);
    }

    private void handleException(Throwable throwable) {
        if (throwable == null) {
            return;
        }

        String message = throwable.getMessage();

        StackTraceElement[] stack = throwable.getStackTrace();
        for (StackTraceElement stackTraceElement : stack) {
            message += "\n" + stackTraceElement.toString();
        }

        for (Throwable cause = throwable.getCause(); cause != null; cause = cause.getCause()) {
            message = "\n" + cause.getMessage();

            StackTraceElement[] causeStack = cause.getStackTrace();
            for (StackTraceElement stackTraceElement : causeStack) {
                message += "\n" + stackTraceElement.toString();
            }
        }
        message = "brand: " + android.os.Build.BRAND +
                ", android: " + android.os.Build.VERSION.RELEASE +
                ", model: " + android.os.Build.MODEL +
                ", message: " + message;

        System.out.println(message);
    }

}