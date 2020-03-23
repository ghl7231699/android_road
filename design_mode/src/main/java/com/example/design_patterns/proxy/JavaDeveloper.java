package com.example.design_patterns.proxy;

public class JavaDeveloper implements Developer {
    private String name;

    public JavaDeveloper(String name) {
        this.name = name;
    }

    @Override
    public void coding() {
        System.out.println(this.name + "is coding Java");
    }

    @Override
    public void debug() {
        System.out.println(this.name + "is debuging Java");
    }
}
