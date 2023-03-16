package com.ghl.common.util;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ghl.common.AutoApplication;
import com.ghl.lib_common.R;

/**
 * author: zhy
 * desc: toast的工具类
 */
public class ToastUtils {
    private static Toast mToast;
    private static String mTips = "";

    private ToastUtils() {
    }

    /**
     * desc 取消弹层
     */
    public static void cancel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            SafeToast.cancel();
        } else if (mToast != null) {
            mToast.cancel();
        }
    }

    /**
     * desc {@link #show(String, int, int)} 主线程中展示，居中，3s时长
     *
     * @param msg toast内容
     */
    public static void show(String msg) {
        show(msg, Toast.LENGTH_LONG);
    }

    /**
     * desc {@link #show(String, int, int)} 主线程中展示，居中，3s时长
     *
     * @param msg toast内容
     */
    public static void show(String msg, int duration) {
        show(msg, duration, Gravity.CENTER);
    }

    /**
     * desc 是否主线程展示，展示时长，是否居中
     *
     * @param msg      toast内容
     * @param duration 展示时长 {@link Toast}
     * @param gravity  是否居中 {@link Gravity}
     */
    public static void show(final String msg, final int duration, final int gravity) {
        if (!TextUtils.isEmpty(msg)) {
            if (HandlerUtils.isMainThread()) {
                showRun(msg, duration, gravity);
            } else {
                HandlerUtils.getMainHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        showRun(msg, duration, gravity);
                    }
                });
            }
        }
    }

    private static void showRun(String msg, int duration, int gravity) {

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
            SafeToast.show(msg, duration, gravity);
        } else {
            if (mToast == null) {
                mTips = msg;
                mToast = makeToast(msg, duration);
            } else {
                if (mTips.equals(msg)) {
                    mTips = "";
                    return;
                }
                mToast.cancel();
                mToast = makeToast(msg, duration);
            }
            mToast.setGravity(gravity, 0, 0);
            mToast.show();
        }
    }

    public static Toast makeToast(String msg, int duration) {
        Context appContext = AutoApplication.mContext;
        // 创建吐司类
        Toast toast = new Toast(appContext);
        toast.setDuration(duration);
        // 创建自定布局
        LayoutInflater inflate = (LayoutInflater) appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflate != null) {
            View v = inflate.inflate(R.layout.toast_layout, null);
            TextView tv = v.findViewById(R.id.tv_msg);
            tv.setText(msg);

            // 设置布局
            toast.setView(v);
        }

        return toast;
    }
}
