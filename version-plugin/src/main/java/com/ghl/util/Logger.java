package com.ghl.util;

import java.util.HashMap;

/**
 * Time：2023/3/22
 * description:
 **/
public class Logger {
    private static boolean debug = true;
    public static HashMap<Integer, String> accCodeMap = new HashMap<>();
    public static HashMap<Integer, String> opCodeMap = new HashMap<>();

    /**
     * 设置是否打印日志
     */
    public static void setDebug(boolean isDebug) {
        debug = isDebug;
    }

    static boolean isDebug() {
        return debug;
    }

    /**
     * 打印日志
     */
    public static void info(Object msg) {
        if (!debug) {
            return;
        }
        try {
            System.out.println((msg));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
