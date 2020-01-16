package com.mmc.lamandys.liba_datapick.typeof;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Excutor {

    public static void main(String[] args) {
        IBuy consumer = new Consumer();
//        Shoper shoper = new Shoper(consumer);
//        shoper.buy();

        IBuy instance = (IBuy) new ProxyFactory(consumer).getProxyInstance();
        instance.buy();
    }

    //抽象角色
    public interface IBuy {
        void buy();
    }

    //真实角色
    static class Consumer implements IBuy {
        public void buy() {
            System.out.println("I want to buy a big apple");
        }
    }

    //抽象角色
    static class Shoper implements IBuy {
        private IBuy mIBuy;

        Shoper(IBuy mIBuy) {
            this.mIBuy = mIBuy;
        }

        public void buy() {
            System.out.println("你有新的订单");
            System.out.println("接单");
            mIBuy.buy();
        }
    }


    /**
     * 创建动态代理类
     * 动态代理不需要实现接口，但需要制定特定接口类型
     */
    static class ProxyFactory {
        private Object target;

        public ProxyFactory(Object target) {
            this.target = target;
        }

        /**
         * 生成代理类
         */
        public Object getProxyInstance() {
            if (target == null) {
                return null;
            }
            return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                    target.getClass().getInterfaces(), new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            System.out.println("你有新的订单");
                            System.out.println("你好，我是动态代理，准备接单");
                            Object invoke = method.invoke(target, args);
                            return invoke;
                        }
                    });
        }
    }
}
