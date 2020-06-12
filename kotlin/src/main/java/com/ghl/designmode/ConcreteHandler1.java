package com.ghl.designmode;

public class ConcreteHandler1 extends AbsHandler {
    @Override
    public void handlerRequest(String condition) {
        if ("ConcreteHandler1".equals(condition)) {
            System.out.println("我是ConcreteHandler1，是我的菜，我处理了");
            return;
        }
        System.out.println("我是ConcreteHandler1，不是我的菜，我不处理");
        successor.handlerRequest(condition);
    }
}
