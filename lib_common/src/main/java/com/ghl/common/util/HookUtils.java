package com.ghl.common.util;

import android.annotation.SuppressLint;
import android.view.View;


import com.ghl.common.hook.HookedClickListenerProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class HookUtils {
    public static void hookOnclickListener(View view) throws Exception {
        //1.反射获取到ListenerInfo对象
        @SuppressLint("DiscouragedPrivateApi") Method getListenerInfo = View.class.getDeclaredMethod("getListenerInfo");
        getListenerInfo.setAccessible(true);
        Object invoke = getListenerInfo.invoke(view);
        // 2.得到原始的onClickListener事件方法
        @SuppressLint("PrivateApi") Class<?> listenerInfoClz = Class.forName("android.view.View$ListenerInfo");
        Field mOnClickListener = listenerInfoClz.getDeclaredField("mOnClickListener");
        mOnClickListener.setAccessible(true);
        View.OnClickListener origin = (View.OnClickListener) mOnClickListener.get(invoke);
        // 3.用hook代理类 替换原始的OnclickListener
        View.OnClickListener hookListener = new HookedClickListenerProxy(origin);
        mOnClickListener.set(invoke, hookListener);
    }

    public static void reflectNewInstance(String className) {
//        try {
//            Class<?> aClass = Class.forName("com.ghl.biz_home.liba_datapick.activity.MainActivity");
//            Object newInstance = aClass.newInstance();
//            MainActivity activity = (MainActivity) newInstance;
//            System.out.println(activity);
//            activity.setTheme(R.style.Theme_AppCompat_Dialog);
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        }

    }
}
