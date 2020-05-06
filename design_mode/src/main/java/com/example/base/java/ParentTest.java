package com.example.base.java;

public class ParentTest {
    public static void main(String[] args) {
        Child child = new Child();
    }


    class MyClassLoader extends ClassLoader{
        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            return super.loadClass(name, resolve);
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            return super.findClass(name);
        }
    }
}
