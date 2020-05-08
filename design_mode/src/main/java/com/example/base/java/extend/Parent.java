package com.example.base.java.extend;

public class Parent {
    {
        System.out.println("Hello Parent");
    }

    public Parent() {
        System.out.println("Parent Constructor");
    }

    static {
        System.out.println("Parent Static");
    }
}
