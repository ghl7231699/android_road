package com.ghl.designmode;

public class Singleton {
    //  //懒汉式 线程不安全
//    private static Singleton instance;
//
//    private Singleton() {
//
//    }
//
//    public static Singleton getInstance() {
//        if (instance == null) {
//            instance = new Singleton();
//        }
//        return instance;
//    }

    //   //懒汉式 线程安全

//    private static Singleton instance;
//
//    private Singleton() {
//
//    }
//
//    public static synchronized Singleton getInstance() {
//        if (instance == null) {
//            instance = new Singleton();
//        }
//        return instance;
//    }


//    // 静态内部类
//    private static class SingletonHolder {
//        private static Singleton instance = new Singleton();
//
//    }
//
//    private Singleton() {
//    }
//
//    public static Singleton getInstance() {
//        return SingletonHolder.instance;
//    }

    //双重校验锁
//    private static Singleton instance;
//    private Singleton() {
//
//    }
//
//    public static Singleton getInstance() {
//        if (instance == null) {
//            synchronized (Singleton.class) {
//                if (instance == null) {
//                    instance = new Singleton();
//                }
//            }
//        }
//        return instance;
//    }

    //枚举方法（线程安全）
    enum SingletonDemo{
        INSTANCE;
        public void otherMethods(){
            System.out.println("Something");
        }
    }

}