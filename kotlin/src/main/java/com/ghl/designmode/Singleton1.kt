package com.ghl.designmode

class Singleton1
/**
 * 私有化构造方法，好在内部控制创建实例的数目
 */
private constructor() {
//
//    //饿汉式
//    private static Singleton instance = new Singleton();
//
//    private Singleton() {
//
//    }
//
//    public static Singleton getInstance() {
//        return instance;
//    }
//    //静态内部类
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


    //懒汉式
    /**
     * 示意方法，单例可以有自己的操作
     */

    fun singletonOperation() {

//功能处理


    }

    /**
     * 示意方法，让外部通过这些方法来访问属性的值
     *
     * @return 属性的值
     */
    /**
     * 示意属性，单例可以有自己的属性
     */

    val singletonData: String? = null

    companion object {

        /**
         * 定义一个变量来存储创建好的类实例
         */

        private var uniqueInstance: Singleton1? = null//如果没有，就创建一个类实例，并把值赋值给存储类实例的变量

        //如果有值，那就直接使用
//判断存储实例的变量是否有值

        /**
         * 定义一个方法来为客户端提供类实例
         *
         * @return 一个Singleton的实例
         */
        @get:Synchronized
        val instance: Singleton1?
            get() {

                //判断存储实例的变量是否有值

                if (uniqueInstance == null) {
                    //如果没有，就创建一个类实例，并把值赋值给存储类实例的变量
                    uniqueInstance = Singleton1()
                }

                //如果有值，那就直接使用


                return uniqueInstance
            }
    }
}