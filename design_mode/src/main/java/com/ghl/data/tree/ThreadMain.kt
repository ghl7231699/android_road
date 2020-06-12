package com.ghl.data.tree

object ThreadMain {

    @JvmStatic
    fun main(args: Array<String>) {
        val myThread = MyThread("run")
        myThread.start()
        myThread.run()
//        myThread.start();
        //        myThread.start();
        MyThread("start").start()


        //        EnumSingleton instance = EnumSingleton.INSTANCE;
//        instance.doSomeThing();
//        System.out.println(instance);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                EnumSingleton instance = EnumSingleton.INSTANCE;
//                System.out.println(instance);
//            }
//        }).start();
//
//        Constructor<EnumSingleton> constructor = null;
//        try {
//            constructor = EnumSingleton.class.getDeclaredConstructor();
//            constructor.setAccessible(true);
//
//            EnumSingleton enumSingletonDemo1 = constructor.newInstance();
//            EnumSingleton enumSingletonDemo2 = constructor.newInstance();
//            System.out.println(enumSingletonDemo1);
//            System.out.println(enumSingletonDemo2);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    internal class MyThread(private val type: String) : Thread() {
        override fun run() {
            super.run()
            println("我是run方法,当前线程：" + currentThread().name + "\t我的名字是" + type)
        }

    }
}