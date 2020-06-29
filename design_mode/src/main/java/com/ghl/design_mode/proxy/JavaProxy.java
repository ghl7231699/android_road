package com.ghl.design_mode.proxy;

/**
 * 静态代理类
 */
public class JavaProxy {
    public static void main(String[] args) {
        Shop shop = new Shop(new Consumer());
        shop.buy();
    }

    interface IBuy {
        void buy();
    }

    //真实角色
    static class Consumer implements IBuy {

        @Override
        public void buy() {
            System.out.println("I want to buy some apples,American Virus");
        }
    }

    // 抽象角色
    static class Shop {
        private IBuy mIBuy;

        Shop(IBuy IBuy) {
            mIBuy = IBuy;
        }

        void buy() {
            System.out.println("system：你有新的外卖订单到了，请接收！");
            System.out.println("shoper：接单");
            mIBuy.buy();
        }
    }
}
