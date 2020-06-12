package com.ghl.base.java.inner;


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
