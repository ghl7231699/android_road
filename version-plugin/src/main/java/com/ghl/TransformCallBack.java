package com.ghl;

import com.android.build.api.transform.TransformInvocation;

/**
 * Time：2023/3/22
 * description:
 **/
public interface TransformCallBack {
    void call(TransformInvocation transformInvocation);
}
