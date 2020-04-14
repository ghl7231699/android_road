package com.example.data;

public class InnerBean {

//    public CallBackTest mCallBackTest = new CallBackTest() {
//        @Override
//        public void doSomething() {
//            System.out.println("hello java kotlin ");
//        }
//    };

    private int x = 10;

    public void print(int a, CallBackTest backTest) {
        debug(backTest);
    }


    private void debug(CallBackTest backTest) {
//        A a = new A(backTest);
//        a.doSomething();
    }

    public void doSomething() {
        print(0, new CallBackTest() {
            @Override
            public void doSomething() {
                x = 0;
            }
        });
    }


//    private class A implements CallBackTest {
//        private CallBackTest mCallBackTest;
//
//        public A(CallBackTest callBackTest) {
//            mCallBackTest = callBackTest;
//        }
//
//        @Override
//        public void doSomething() {
//            mCallBackTest.doSomething();
//        }
//    }


    public interface CallBackTest {
        void doSomething();
    }

}
