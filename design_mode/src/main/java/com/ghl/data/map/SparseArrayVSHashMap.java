package com.ghl.data.map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 类描述：SparseArray  vs   HashMap
 * 创建人：ghl
 * 创建时间：2019-12-04
 */
public class SparseArrayVSHashMap {
    public static void main(String[] args) {
        normalInsertMap();

        Set<Short> set = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            set.add((short) i);
            set.remove(i - 1);
        }
        System.out.println(set.size());


        for (short i = 0; i < 100; i++) {
            set.add(i);
            short o = (short) (i - 1);
            set.remove(o);
        }
        System.out.println(set.size());

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
