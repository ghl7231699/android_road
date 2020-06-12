package com.ghl.design_patterns.proxy.dynamic;

import com.ghl.design_patterns.proxy.Developer;
import com.ghl.design_patterns.proxy.JavaDeveloper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理类
 * <p>
 */
public class JavaDynamicProxy {
    public static void main(String[] args) {
        final JavaDeveloper jack = new JavaDeveloper("Jack");
        /*
          classloader，选用的类加载器。因为代理的是jack，所以一般都会用加载jack的类加载器。
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
                System.out.println("method name is " + method.getName());
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
        jackProxy.print("sssssss");
        jackProxy.print(11111);


        Class<?> aClass = null;
        try {
            aClass = Class.forName("com.ghl.design_patterns.proxy.JavaDeveloper");
            final Method methodA = aClass.getMethod("print", int.class);
            System.out.println("方法名为" + methodA.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }


        String s;
        s = "21321321";
        s = "sfdsfsf";

        System.out.println(s);
    }
}
