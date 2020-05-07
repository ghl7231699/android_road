package com.example.data;


import java.lang.reflect.Field;

public class InnerMain {
    private static String a;

    public static void main(String[] args) {
        try {
            Class<?> target = Class.forName(InnerBean.class.getName());

            Class<?>[] declaredClasses = target.getDeclaredClasses();

            InnerBean instance = (InnerBean) target.newInstance();
            instance.print(1000, instance.mCallBackTest);

//            Method print = target.getDeclaredMethod("print", int.class, InnerBean.CallBackTest.class);
//            print.setAccessible(true);
//            System.out.println(print.getName());
//
//            print.invoke(instance, 2000, new CallBackTest() {
//                @Override
//                public void doSomething() {
//                    System.out.println("hello world");
//                }
//            });


            for (Class c : declaredClasses
            ) {
                System.out.println("class is \t" + c.getName() + c.getTypeName());
            }

            Field[] declaredFields = target.getDeclaredFields();
            for (Field field : declaredFields) {
                System.out.println("Field is \t" + field.getName());
            }


            String a = "test";
            String b = "test";
            String c = "test";


            String x = "abc";
            String y = "def";

            String z = x + y;
            String d = "abc" + "def";


            System.out.println(z=="abcdef");
            System.out.println(d=="abcdef");
            System.out.println(d==z);

            System.out.println(a.equals(b));
            System.out.println(a.equals(c));
            System.out.println(a == c);
            System.out.println(a.hashCode());
            System.out.println(c.hashCode());


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
//        catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
    }
}
