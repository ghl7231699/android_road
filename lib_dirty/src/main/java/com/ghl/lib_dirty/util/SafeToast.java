package com.xzdz.common.tools;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaozhu.lib.dirty.R;
import com.xzdz.business.application.AppContext;

import java.lang.reflect.Field;

/**
 * Created by wsy on 01/08/2018
 */
public class SafeToast {

    private static Field sFieldTn;
    private static Field sFieldTnHandler;
    private static Toast sToast;


    static {
        try {
            sFieldTn = Toast.class.getDeclaredField("mTN");
            sFieldTn.setAccessible(true);
            sFieldTnHandler = sFieldTn.getType().getDeclaredField("mHandler");
            sFieldTnHandler.setAccessible(true);
        } catch (Exception e) {

        }
    }

    private SafeToast() {
    }


    /**
     * 指定位置显示字符串资源的Toast
     */
    public static void show(String message, int duration, int gravity) {
        sToast = makeToast(message, duration);
        hook(sToast);
        sToast.setGravity(gravity, 0, 0);
        sToast.show();
    }


    private static Toast makeToast(String msg, int duration) {
        Context appContext = AppContext.getInstance().getAppContext();
        // 创建吐司类
        Toast toast = Toast.makeText(appContext, "", Toast.LENGTH_LONG);
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

    /**
     * 取消Toast
     */
    public static void cancel() {
        if (sToast != null) {
            sToast.cancel();
        }
    }

    private static void hook(Toast toast) {
        try {
            Object tn = sFieldTn.get(toast);
            Handler preHandler = (Handler) sFieldTnHandler.get(tn);
            sFieldTnHandler.set(tn, new SafeHandler(preHandler));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static class SafeHandler extends Handler {
        private Handler impl;

        public SafeHandler(Handler impl) {
            this.impl = impl;
        }

        @Override
        public void dispatchMessage(Message msg) {
            try {
                super.dispatchMessage(msg);
            } catch (Exception e) {
            }
        }

        @Override
        public void handleMessage(Message msg) {
            impl.handleMessage(msg);//需要委托给原Handler执行
        }
    }
}
