package com.example.data;

/**
 * 静态内部类
 */
public class StaticSingleton {
    private StaticSingleton() {
    }

    public static StaticSingleton getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static volatile StaticSingleton instance = new StaticSingleton();
    }
}
