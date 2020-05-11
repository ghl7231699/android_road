package com.example.base.java.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Params<T1, T2> {

    class A {
    }

    class B extends A {

    }

    private Class<T1> entityClass;

    public Params() {
        Type type = getClass().getGenericSuperclass();
        System.out.println("getClass is\t" + getClass() + "\ntype is\t" + type);

        Type[] arguments = ((ParameterizedType) type).getActualTypeArguments();
        System.out.println("type[] is\t" + arguments);

        Type argument = arguments[0];
        System.out.println("trueType is\t" + argument);

        this.entityClass = (Class<T1>) argument;
        System.out.println("entityClass = " + entityClass);

        B t = new B();
        type = t.getClass().getGenericSuperclass();

        System.out.println("A is B's super class :" + ((ParameterizedType) type).getActualTypeArguments().length);

    }
}
