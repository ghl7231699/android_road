package com.example.design_mode.singleton;

/**
 * 类描述：DCL 双重检查锁
 */
public class DCLSingleton {
    private static volatile DCLSingleton instance;

    private DCLSingleton() {
    }

    public static DCLSingleton getInstance() {
        if (instance == null) {// 线程 1 2 3执行到这里
            synchronized (DCLSingleton.class) {// 线程1开始执行同步代码块， 2 3 等待
                if (instance == null) {// 线程1到达这里发现instance为空，继续执行if代码块，执行完成后退出同步代码区域，
                    //然后线程2开始进入，如果这里不进行判断的话，线程2会重新实例化一次，生成新的instance，加入了判断之后，则会直接返回跳过该判断，返回instance
                    instance = new DCLSingleton();
                }
            }
        }
        return instance;
    }
}
