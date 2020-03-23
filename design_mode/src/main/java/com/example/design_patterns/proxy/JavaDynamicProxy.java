package com.example.design_patterns.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理类
 * <p>
 * https://www.jianshu.com/p/95970b089360
 */
public class JavaDynamicProxy {
    public static void main(String[] args) {
        final JavaDeveloper jack = new JavaDeveloper("Jack");
        /*
          loder，选用的类加载器。因为代理的是jack，所以一般都会用加载jack的类加载器。
          interfaces，被代理的类所实现的接口，这个接口可以是多个。
          h，绑定代理类的一个方法。
         */
        Developer jackProxy = (Developer) Proxy.newProxyInstance(jack.getClass().getClassLoader(), jack.getClass().getInterfaces(), new InvocationHandler() {

            /**
             * @param proxy     代理后的实例对象
             * @param method   对象被调用的方法
             * @param args  方法调用时的参数
             * @return return method.invoke()则会继续调用原来的方法，
             * @throws Throwable   exception
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if ("coding".equals(method.getName())) {
                    System.out.println("jack is praying for the code");
                    return method.invoke(jack, args);
                } else if ("debug".equals(method.getName())) {
                    System.out.println("Jack have no bug！No need to debug");
//                    return method.invoke(jack,args);
                    return null;
                }
                return null;
            }
        });
        jackProxy.coding();
        jackProxy.debug();
    }
}
