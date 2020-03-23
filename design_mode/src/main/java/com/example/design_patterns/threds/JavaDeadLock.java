package com.example.design_patterns.threds;

/**
 * 模拟死锁产生的原因
 */
public class JavaDeadLock {
    private static final String obj1 = "obj1";
    private static final String obj2 = "obj2";

    public static void main(String[] args) {
        new DeadLockThreadA().start();
        new DeadLockThreadB().start();
    }


    static class DeadLockThreadA extends Thread {
        @Override
        public void run() {
            try {
                String simpleName = this.getClass().getSimpleName();
                System.out.println(simpleName + " is running");
                while (true) {
                    synchronized (JavaDeadLock.obj1) {
                        System.out.println(simpleName + "locked obj1");
                        //获取obj1后先等一会儿，让LockB有足够的时间锁住obj2
                        Thread.sleep(100);
                        System.out.println(simpleName + " trying to lock obj2");
                        synchronized (JavaDeadLock.obj2) {
                            System.out.println(simpleName + "locked obj2");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.run();
        }
    }

    static class DeadLockThreadB extends Thread {
        @Override
        public void run() {
            try {
                String simpleName = this.getClass().getSimpleName();
                System.out.println(simpleName + " is running");
                while (true) {
                    synchronized (JavaDeadLock.obj2) {
                        System.out.println(simpleName + "locked obj2");
                        System.out.println(simpleName + " trying to lock obj1");
                        synchronized (JavaDeadLock.obj1) {
                            System.out.println(simpleName + "locked obj1");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.run();
        }
    }
}
