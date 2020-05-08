package com.example.base.java.equals;

public class BasicJava {
    public static void main(String[] args) {
        equalsValue();
    }

    /**
     * String类型的数据equals时，先比较两个字符串的引用是否相等，也就是两个字符串的引用是否指向同一个对象
     * <p>
     * 如果不是指向的同一个对象，则会将两个字符串中的字符挨个进行吧比较。
     * <p>
     * s1 和 s2是两个不同的对象，所以最终对比的是字符串里的值
     */
    private static void equalsValue() {
        String s1 = "hello";
        String s3 = new String("hello");
        System.out.println(s1.equals(s3));    //true
        boolean xx = s1 == s3;
        System.out.println(xx);    //false

        String a = "test";
        String b = "test";
        String c = "test";


        String x = "abc";
        String y = "def";

        String z = x + y;
        String d = "abc" + "def";


        System.out.println(z == "abcdef");
        System.out.println(d == "abcdef");
        System.out.println(d == z);

        System.out.println(a.equals(b));
        System.out.println(a.equals(c));
        System.out.println(a == c);
        System.out.println(a.hashCode());
        System.out.println(c.hashCode());
    }
}
