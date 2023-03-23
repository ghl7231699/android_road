package com.ghl;

import com.android.build.api.transform.JarInput;

import java.io.File;

/**
 * Time：2023/3/22
 * description:
 **/
public interface EachCallBack {
    void call(JarInput jarInput, File dest);

    void call(File file);
}
