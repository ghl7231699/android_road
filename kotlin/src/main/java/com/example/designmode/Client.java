package com.example.designmode;

public class Client {
    public static void main(String[] args) {
        AbsHandler handler1 = new ConcreteHandler1();
        AbsHandler handler2 = new ConcreteHandler2();

        handler1.successor = handler2;
        handler2.successor = handler1;

        handler1.handlerRequest("ConcreteHandler2");


        System.out.println(0.3f+0.6f);
        System.out.println(0.3+0.6);
        System.out.println(0.9);
    }
}
