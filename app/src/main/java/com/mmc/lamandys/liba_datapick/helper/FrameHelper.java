package com.mmc.lamandys.liba_datapick.helper;

import android.view.Choreographer;

import com.mmc.lamandys.liba_datapick.performance.FPSFrameCallBack;

/**
 * 类描述：帧率检测
 * 创建人：ghl
 * 创建时间：2019/4/3
 *
 * @version v1.0
 */
public class FrameHelper {
    public static void fpsListener() {
        Choreographer.getInstance().postFrameCallback(new FPSFrameCallBack(System.nanoTime()));
    }
}
