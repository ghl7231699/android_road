package com.example.design_patterns.threds;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Lock的使用
 */
public class JavaLock {
    public static void main(String[] args) {
        LockThread lt = new LockThread();
        new Thread(() -> lt.lock("A")).start();
        new Thread(() -> lt.lock("B")).start();

        final Queue queue = new Queue();
        //启动10个线程  5个读线程 5个写线程

        for (int i = 0; i < 5; i++) {
            new Thread() {
                @Override
                public void run() {
                    queue.get();
                }
            }.start();

            new Thread() {
                @Override
                public void run() {
                    queue.put(new Random().nextInt(10000));
                }
            }.start();
        }
    }

    static class LockThread {
        //可重入锁
        Lock mLock = new ReentrantLock();

        public void lock(String name) {
            //获取锁
            mLock.lock();
            try {
                System.out.println(name + " get the lock");
            } finally {
                //释放锁
                mLock.unlock();
                System.out.println(name + " release the lock");
            }
        }
    }

    /**
     * 读写锁
     * <p>
     * ReadWriteLock 维护了一对相关的锁，一个用于只读操作，另一个用于写入操作。
     * 只要没有 writer，读取锁可以由多个 reader 线程同时保持，而写入锁是独占的。
     */
    static class Queue {
        //共享数据，只有一个线程能写该数据，但可以有多个线程同时去读数据
        private Object data = null;

        ReadWriteLock mLock = new ReentrantReadWriteLock();

        //读数据
        public void get() {
            //加读锁
            mLock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + " be ready to read data!");
                Thread.sleep((long) (Math.random() * 1000));
                System.out.println(Thread.currentThread().getName() + " have read data :" + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //释放读锁
                mLock.readLock().unlock();
            }
        }

        //写数据
        public void put(Object obj) {
            //加写锁
            mLock.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + " be ready to write data!");
                Thread.sleep((long) (Math.random() * 1000));
                data = obj;
                System.out.println(Thread.currentThread().getName() + " have write data: " + data);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mLock.writeLock().unlock();
            }
        }
    }
}
