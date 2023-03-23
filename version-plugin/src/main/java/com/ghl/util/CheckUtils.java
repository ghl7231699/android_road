package com.ghl.util;

import com.android.build.api.transform.JarInput;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Time：2023/3/22
 * description:
 **/
public class CheckUtils {
    //需要忽略的包
    public static Set<String> excludeJar = new HashSet<>() {
        {
            add("com.android.support");
            add("android.arch.");
            add("androidx.");
        }
    };

    /**
     * 对于相应的jar class 判断是否需要scan
     *
     * @return true 需要
     */
    public static boolean isNeedScanJar(JarInput jarInput) {
        boolean isScanJar = true;
        for (String it : excludeJar) {
            String name = jarInput.getName();
            boolean isScan = name.contains(it);
            if (isScan) {
                return false;
            }
        }
        return isScanJar;
    }

    public static boolean isNeedScanFile(File targetFile) {
        return targetFile.isFile() && !(targetFile.getAbsolutePath().contains("R.class")
                || targetFile.getAbsolutePath().contains("R" + '$'));
    }
}
