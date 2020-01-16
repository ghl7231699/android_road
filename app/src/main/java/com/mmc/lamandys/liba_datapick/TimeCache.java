package com.mmc.lamandys.liba_datapick;

import com.mmc.lamandys.liba_datapick.util.TimeUtil;

import java.util.HashMap;
import java.util.Map;

import static com.mmc.lamandys.liba_datapick.util.TimeUtil.TIMESTAMP;

/**
 * 类描述：计时类，编译器加入指定方法中
 * 创建人：ghl
 * 创建时间：2019/4/3
 *
 * @version v1.0
 */
public class TimeCache {
    private static boolean RESTART = false;
    private static Map<String, Long> sStartTime = new HashMap<>();
    private static Map<String, Long> sEndTime = new HashMap<>();
    private static Map<String, Long> resumeTime = new HashMap<>();
    private static Map<String, Long> restartTime = new HashMap<>();
    private static Map<String, Long> appTime = new HashMap<>();

    public static void setStartTime(String methodName, long time) {
        sStartTime.put(methodName, time);
        System.out.println(methodName + "开始时间" + TimeUtil.formatCurrentTime(time, TIMESTAMP));
    }

    public static void setEndTime(String methodName, long time) {
        sEndTime.put(methodName, time);
        System.out.println(methodName + "结束时间" + TimeUtil.formatCurrentTime(time, TIMESTAMP));
    }

    /**
     * 用于计算app ->onresume  时间
     *
     * @param methodName
     * @param time
     */
    public static void setResumeTime(String methodName, long time) {
        resumeTime.put(methodName, time);
        try {
            long start = appTime.get(AutoApplication.class.getName());
            long end = resumeTime.get(methodName);
            long dex = end - start;
            printLog("启动耗时", methodName, dex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setRestartTime(String methodName, long time) {
//        if (RESTART) {//重启
//            restartTime.put(methodName, time);
//            System.out.println(methodName + "重启时间：" + TimeUtil.formatCurrentTime(time, TIMESTAMP));
//        }
    }

    public static void setAppTime(String methodName, long time) {
        appTime.put(methodName, time);
        System.out.println(methodName + "启动时间：" + TimeUtil.formatCurrentTime(time, TIMESTAMP));
    }

    /**
     * 时长
     *
     * @param methodName
     * @return
     */
    public static String getCostTime(String methodName) {
        long start = sStartTime.get(methodName);
        long end = sEndTime.get(methodName);
        long dex = end - start;
        return "时长：" + methodName + " cost " + dex / 1000f + "s";
    }

    /**
     * 日志打印
     *
     * @param des        功能描述
     * @param methodName 类名
     * @param dex        time
     */
    private static void printLog(String des, String methodName, long dex) {
        System.out.println(des + "===>: " + methodName + "\tcost\t" + dex / 1000f + "s");
    }

}
