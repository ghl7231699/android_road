package com.ghl.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具
 */
public class TimeUtil {
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String TIMESTAMP = "yyyy-MM-dd HH:mm:ss";

    /**
     * 根据指定的格式格式化当前系统时间
     *
     * @param format 要格式化成的格式
     * @return 格式化成功后的时间字符串
     */
    public static String formatCurrentTime(String format) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.SIMPLIFIED_CHINESE);
        return dateFormat.format(date);
    }

    /**
     * 根据指定的格式格式化当前系统时间
     *
     * @param format 要格式化成的格式
     * @param time   时间
     * @return 格式化成功后的时间字符串
     */
    public static String formatCurrentTime(long time, String format) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.SIMPLIFIED_CHINESE);
        return dateFormat.format(date);
    }
}
