package com.ghl.design_mode.proxy;

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

    @Override
    public void print(String msg) {
        System.out.println(this.name + "is printing message");
    }

    @Override
    public void print(int msg) {
        System.out.println(this.name + "is printing message");
    }


}
