package com.mmc.lamandys.liba_datapick;

import com.ghl.lib_dirty.util.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 使用asm读取class
 */
public class ASMReadClass {
    public static void main(String[] args) {
//        new File(".source/");
        try {
            File file = new File("logapp/build/intermediates/classes/debug/com/mmc/lamandys" +
                    "/liba_datapick/activity/MainActivity.class");
            File dir = new File(".");
            transformClassFile(dir, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static File transformClassFile(File dir, File sourceFile) throws IOException {
        String className = sourceFile.getName();
        //得到class文件二进制流
        FileInputStream fis = new FileInputStream(sourceFile);
        byte[] sourceClassBytes = IOUtils.toByteArray(fis);
        //定义ClassWriter 用于修改后的二进制文件的输出
//        new ClassWri
        return null;
    }
}
