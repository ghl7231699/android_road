package com.ghl.opt.auto.timer;

import com.ghl.lib_dirty.AutoApplication;
import com.ghl.lib_dirty.util.TimeUtil;

import java.util.HashMap;
import java.util.Map;

import static com.ghl.lib_dirty.util.TimeUtil.TIMESTAMP;

/**
 * 类描述：方法执行时间
 * 创建人：ghl
 * 创建时间：2019/4/2
 * todo 修改
 *
 * @version v1.0
 */
public class TimeExecutor {
    private static Map<String, Long> sStartTime = new HashMap<>();
    private static Map<String, Long> sEndTime = new HashMap<>();
    private static Map<String, Long> resumeTime = new HashMap<>();
    private static Map<String, Long> restartTime = new HashMap<>();
    private static Map<String, Long> appTime = new HashMap<>();
    private static boolean onResume = false;
    private static boolean foucs = false;


    public static void setStartTime(String methodName, long time) {
        sStartTime.put(methodName, time);
        System.out.println(methodName + "开始时间\t" + TimeUtil.formatCurrentTime(time, TIMESTAMP));
    }

    public static void setEndTime(String methodName, long time) {
        sEndTime.put(methodName, time);
        System.out.println(methodName + "结束时间\t" + TimeUtil.formatCurrentTime(time, TIMESTAMP));
    }

    /**
     * 用于计算app ->首页onResume  时间
     * todo 优化 页面多次调用 启动耗时有问题
     *
     * @param methodName
     * @param time
     */
    public static void setResumeTime(String methodName, long time) {
        resumeTime.put(methodName, time);
        try {
            if (restartTime.containsKey(methodName)) {
                long restart = restartTime.get(methodName);
                if (restart != 0) {//执行了onRestart方法（）
                    long end = resumeTime.get(methodName);
                    long dex = end - restart;
                    printLog("热启动耗时", methodName, dex);
                    restartTime.remove(methodName);
                }
            } else {
                long end = resumeTime.get(methodName);
                if (!onResume) {
                    long start1 = sStartTime.get(methodName);
//                    long end = resumeTime.get(methodName);
                    long dex1 = end - start1;
                    printLog("页面加载耗时", methodName, dex1);
                    onResume = true;
//                }
                }
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }

    public static void setRestartTime(String methodName, long time) {
//        if (RESTART) {//重启
        restartTime.put(methodName, time);
        System.out.println(methodName + "\tonRestart重启时间：" + TimeUtil.formatCurrentTime(time,
                TIMESTAMP));
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


    /**
     * app冷启动完成
     *
     * @param name
     * @param end
     */
    public static void setAppCompleteTime(String name, long end) {
        //如果是启动页
        if ("com.mmc.lamandys.liba_datapick.activity.MainActivity".equals(name) && !foucs) {
            long start = appTime.get(AutoApplication.class.getName());
            long dex = end - start;
            printLog("冷启动耗时", name, dex);
            foucs = true;
        }
    }

}
