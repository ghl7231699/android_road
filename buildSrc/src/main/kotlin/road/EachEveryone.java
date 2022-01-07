package road;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import groovy.lang.Closure;

public class EachEveryone {
    static void each(TransformInvocation transformInvocation, Closure jarClosure, Closure dirFileClosure) throws IOException {
        //常量值 4
        final int size = ".jar".length();
        for (TransformInput input : transformInvocation.getInputs()) {
            //jar 包遍历
            for (JarInput jarInput : input.getJarInputs()) {
                String jarName = jarInput.getName();
                String md5Name = DigestUtils.md5Hex(jarInput.getFile().getAbsolutePath());
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - size);
                }
                //得到复制后的文件
                File dest = transformInvocation.getOutputProvider().getContentLocation(jarName + md5Name,
                        jarInput.getContentTypes(), jarInput.getScopes(), Format.JAR);
                jarClosure.call(jarInput, dest);
                //所有的transform 后的文件都需要进行输出
                FileUtils.copyFile(jarInput.getFile(), dest);
            }
            // 文件夹遍历
            for (DirectoryInput directoryInput : input.getDirectoryInputs()) {
                //得到复制后FILE 实例
                File dest = transformInvocation.getOutputProvider().getContentLocation(directoryInput.getName(),
                        directoryInput.getContentTypes(), directoryInput.getScopes(), Format.DIRECTORY);
                // 遍历文件夹中的所有文件
                File[] files = directoryInput.getFile().listFiles();
                if (files != null) {
                    for (File file : files) {
                        dirFileClosure.call(file);
                    }
                }
                //所有的transform 后的文件都需要进行输出
                FileUtils.copyDirectory(directoryInput.getFile(), dest);
            }
        }
    }

    interface CallBack {
        void call(JarInput jarInput);
    }
}
