package com.example.data.map;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：SparseArray  vs   HashMap
 * 创建人：ghl
 * 创建时间：2019-12-04
 */
public class SparseArrayVSHashMap {
    public static void main(String[] args) {
        normalInsertMap();
    }


    private static void normalInsertMap() {
        Map<Integer, String> map = new HashMap<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            map.put(i, String.valueOf(i));
        }

        long memory = Runtime.getRuntime().totalMemory();
        long end = System.currentTimeMillis();
        System.out.println("HashMap插入消耗的时间为" + (end - start) + "\tHashMap占用的内存是" + memory);
    }


}
