package com.ghl.base.java.reflect;

public class MyClass {

    private String s;

    private void test(final String t) {
        final B bb = new B();
        Listener listener = new Listener() {
            @Override
            public void onClick(Object a) {
                System.out.println(bb);
                System.out.println(s);
            }
        };
    }


    private void set(final String t) {
        System.out.println("hahaha");
    }


    interface Listener {
        void onClick(Object b);
    }

    class B {

    }
}