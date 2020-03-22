package com.example.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * hashMap为什么是线程不安全的
 */
public class HashMapUnSafe {
    private static List<HashMapThread> list = new ArrayList<>();

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            HashMapThread thread = new HashMapThread();
            list.add(thread);
        }

        for (int i = 0; i < list.size(); i++) {
            list.get(i).start();
        }

    }

    /**
     * 需要在jdk1.7中验证
     */
    static class HashMapThread extends Thread {
        private static AtomicInteger atomicInteger = new AtomicInteger();
        private static Map<Integer, Integer> map = new HashMap<>();

        @Override
        public void run() {
            while (atomicInteger.get() < 10000000) {
                map.put(atomicInteger.get(), atomicInteger.get());
                atomicInteger.incrementAndGet();
            }
        }
    }
}
