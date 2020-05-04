package com.example.data;

/**
 * 测试内部类编译之后的文件
 * javac 绝对路径
 */
public class InnerBean {

    public CallBackTest mCallBackTest = new CallBackTest() {
        @Override
        public void doSomething() {
            System.out.println("hello java kotlin ");
        }
    };

    private int x = 10;

    public void print(int a, CallBackTest backTest) {
        debug(backTest);
    }


    private void debug(CallBackTest backTest) {
//        A a = new A(backTest);
//        a.doSomething();
    }


    public void doSomething(final int i, final B bb) {
        final B b;
        print(0, new CallBackTest() {
            @Override
            public void doSomething() {
//                x = 0;
                x = i;
                doSomething();
            }
        });
    }


    private class A implements CallBackTest {
        private CallBackTest mCallBackTest;

        public A(CallBackTest callBackTest) {
            mCallBackTest = callBackTest;
        }

        @Override
        public void doSomething() {
            mCallBackTest.doSomething();
        }
    }

    class B {
        String name;
    }


    public interface CallBackTest {
        void doSomething();
    }

}
