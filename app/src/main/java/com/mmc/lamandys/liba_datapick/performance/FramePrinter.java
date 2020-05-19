package com.mmc.lamandys.liba_datapick.performance;

import android.util.Printer;

public class FramePrinter implements Printer {
    private static final String DISPATCHING = ">>>>> Dispatching to ";
    private static final String FINISHED = "<<<<< Finished to ";
    private long start;
    private long end;
    //view正常刷新是每秒16帧，所以如果handler的分发时间大于1s 则可视为卡顿
    private static final int THRESHOLD = 1000;

    @Override
    public void println(String x) {
        if (x.startsWith(DISPATCHING)) {//handler开始分发消息
            start = System.currentTimeMillis();
        } else if (x.startsWith(FINISHED)) {////handler消息分发完成
            end = System.currentTimeMillis();
        } else {
            start = 0;
            end = 0;
        }
        long abs = Math.abs((end - start));
        if (start != 0 && end != 0 && (abs > THRESHOLD)) {
            System.out.println(String.format("执行时间为%s%s", "有点卡了", abs));
            //todo 记录下堆栈信息 保存到本地
//            Debug.startMethodTracing("xiaozhu");
        }
    }
}
