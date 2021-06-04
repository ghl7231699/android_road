package com.ghl.imc;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 组件间通信管理类
 */
public class ModuleServiceManager {

    /**
     * 保存所有模块数据服务
     * 涉及到put 操作 线程安全
     * key 为 {@link Class#getSimpleName()}
     * value 从上层的各模块中拿到的 已经实例化的Service
     *
     * @see #mTempSaveList
     * @see #getClassTarget(Class)
     * @see #bindService(String, ModuleService)
     */
    private static final ConcurrentHashMap<String, ModuleService> mServiceMap = new ConcurrentHashMap<>();

    /**
     * 当前ASM调用两个参数的方法不会 list为兼容方案
     * 当前顺序 与service 没有任何关系
     * 涉及到多线程的遍历操作 线程安全
     *
     * @see #bindService(ModuleService)
     * @see #getClassTarget(Class)
     * @see #mServiceMap
     */
    public static final List<ModuleService> mTempSaveList = new Vector<>();

    static {
        //空实现 方便asm查入 ，不能删
        //当前插入语句
        //ModuleServiceManager.bindService(new LoginImpl());
        //希望实现的方式
        //ModuleServiceManager.getInstance().bindService("loginService",new LoginImpl());
        //未实现理由 key的值不好取 ，ASM插入调用复杂语句，比较难
    }

    private ModuleServiceManager() {
        //私有实例方法
    }


    /**
     * 找到相应类的实现类，已实例化好的
     * eg. 传入LoginService 返回的是已经new 出来的LoginImpl
     * 查找 先看{@link #mServiceMap} 中有没有 有则 赋值
     * 如果没有 在根据 {@link #mTempSaveList}遍历寻找(根据api {@link Class#isAssignableFrom(Class)})来判断
     * 找到后 赋值 {@link #bindService(String, ModuleService)}加入{@link #mServiceMap} 中
     *
     * @param clazz 目标类
     */
    public static <T> T getClassTarget(@NonNull Class<T> clazz) {
        T t = null;
        String key = clazz.getCanonicalName();
        if (mServiceMap.containsKey(key)) {
            ModuleService service = mServiceMap.get(key);
            t = (T) service;
        } else {
            for (ModuleService service : mTempSaveList) {
                if (clazz.isAssignableFrom(service.getClass())) {
                    t = (T) service;
                    bindService(key, service);
                }
            }
        }
        return t;
    }

    /**
     * 为了注入相应的Service
     * 只在静态代码块调用 所以静态
     * 不可删除
     *
     * @param service 已经实例化的类
     */
    private static void bindService(ModuleService service) {
        if (!mTempSaveList.contains(service)) {
            mTempSaveList.add(service);
        }
    }

    /**
     * @param name    {@link Class#getSimpleName()}
     * @param service 从各模块拿的服务
     */
    private static void bindService(String name, ModuleService service) {
        if (service == null) {
            return;
        }
        mServiceMap.put(name, service);
    }

    public static void createApp(Application app) {
        for (ModuleService service : mTempSaveList) {
            Log.e("ghl", service.getClass().getCanonicalName());
            service.onCreateApp(app);
        }
    }
}


