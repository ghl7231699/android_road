package com.example.base.java;

public class Child extends Parent {

    {
        System.out.println("Hello Child");
    }

    public Child() {
        System.out.println("Child Constructor");
    }

    static {
        System.out.println("Child Static");
    }
}
