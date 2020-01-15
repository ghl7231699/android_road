package com.example.designmode;

public class ConcreteHandler2 extends AbsHandler {
    @Override
    public void handlerRequest(String condition) {
        if ("ConcreteHandler2".equals(condition)) {
            System.out.println("我是ConcreteHandler2，是我的菜，我处理了");
            return;
        }
        System.out.println("我是ConcreteHandler2，不是我的菜，我不处理");
        successor.handlerRequest(condition);
    }
}
