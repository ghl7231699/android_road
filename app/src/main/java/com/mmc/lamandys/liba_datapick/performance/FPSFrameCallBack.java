package com.mmc.lamandys.liba_datapick.performance;

import android.os.Build;
import androidx.annotation.RequiresApi;
import android.view.Choreographer;

/**
 * 自定义帧率监听回调
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class FPSFrameCallBack implements Choreographer.FrameCallback {
    private long mLastFrameTimeNanos;
    private long mFrameIntervalNanos;

    public FPSFrameCallBack(long mLastFrameTimeNanos) {
        this.mLastFrameTimeNanos = mLastFrameTimeNanos;
        mFrameIntervalNanos = (long) (1000000000 / 60.0);
    }

    @Override
    public void doFrame(long frameTimeNanos) {
        if (mLastFrameTimeNanos == 0) {
            mLastFrameTimeNanos = frameTimeNanos;
        }
        final long jitterNanos = frameTimeNanos - mLastFrameTimeNanos;
        if (jitterNanos >= mFrameIntervalNanos) {
            final long skippedFrames = jitterNanos / mFrameIntervalNanos;
//            if (skippedFrames > 30.0) {
            System.out.println("渲染了： " + skippedFrames + " frames!  "
                    + "The application may be doing too much work on its main thread.");
//            }
        }
        mLastFrameTimeNanos = frameTimeNanos;
        //注册下一帧回调
        Choreographer.getInstance().postFrameCallback(this);
    }
}
