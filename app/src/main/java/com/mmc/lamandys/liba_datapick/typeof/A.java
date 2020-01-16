package com.mmc.lamandys.liba_datapick.typeof;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class A {

    public static void main(String[] args) {
        C<String> c = new C<>();
        c.next();
        D d = new D();
        d.next();

        min(10, 12);
//        min(d, c);

        C<Man> c1 = new C<>();
        System.out.println(c1.getClass() == c.getClass());
        System.out.println(c1.getClass().getSimpleName());
//        C<String>[] array;
//        array = new C[10];

        //? extends
        Pair<Fruit> p = new Pair<>(new Fruit());
        Pair<Apple> p1 = new Pair<>(new Apple());
        print(p);
        print(p1);

        //? super
        Pair<Apple> apple = new Pair<>();
        Pair<Fruit> fruit = new Pair<>();
        Pair<HongFuShi> hongFuShi = new Pair<>();
        printSuper(apple);
        printSuper(fruit);
//        printSuper(hongFuShi);
        apple.setData(new Apple());
//        apple.setData(new Fruit());
        apple.setData(new HongFuShi());

        Pair pair = new Pair();
        Object data = pair.getData();

//        Class<? extends Pair> aClass = p.getClass();
//        System.out.println(aClass);
//
//        Type genericSuperclass = aClass.getGenericSuperclass();
//        System.out.println(genericSuperclass);
//
//        ParameterizedType type = (ParameterizedType) genericSuperclass;
//        Type[] types = type.getActualTypeArguments();
//        for (Object clazz : types) {
//            System.out.println(clazz.toString());
//        }

    }


//    private String method(List<Integer> list) {
//        System.out.println("List");
//        return "OK";
//    }

    private String method(List<String> list) {
        System.out.println("List");
        return "OK";
    }

    public static <T extends Comparable> T min(T a, T b) {
        if (a.compareTo(b) > 0) {
            return b;
        } else {
            return a;
        }
    }

    private static <T> T getInstance() {
        return null;
    }

    public static <T extends Comparable & Serializable> T max(T a, T b) {
        if (a.compareTo(b) > 0) {
            return a;
        } else {
            return b;
        }
    }

    public static <K, V extends Comparable & Serializable> void add(V a, V b) {
        System.out.println(a.toString() + b.toString());
    }

    private static void print(Pair<? extends Fruit> p) {
        Fruit data = p.getData();
        System.out.println(data.getColor());
    }

    private static void printSuper(Pair<? super Apple> p) {
        System.out.println(p.getData());
    }
}

class Man {

}

class Problem extends Exception {
    public <T extends Throwable> void doWork(T t) {
        try {

        } catch (Exception e) {

        }
    }
}

class C<T> implements B<T> {
    private T data;

    @Override
    public T next() {
        return null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    //错误
//    private static  T getInstance() {
//        return null;
//    }
}

class D implements B<String> {
    @Override
    public String next() {
        System.out.println();
        return "Hello World";
    }
}

class E extends D {

}

class Pair<T> {
    private T data;

    public Pair(T data) {
        this.data = data;
    }

    public Pair() {

    }

    public T next() {
        return null;
    }

    T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

class Fruit {

    String getColor() {
        return "红橙黄绿蓝靛紫";
    }
}

class Apple extends Fruit {
    protected String getName() {
        return "My name is apple!";
    }
}

class HongFuShi extends Apple {
    protected String getName() {
        return "My name is HongFuShi!";
    }
}

class Orange extends Fruit {
}




