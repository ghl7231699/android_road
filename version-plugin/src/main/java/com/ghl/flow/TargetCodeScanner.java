package com.ghl.flow;

import com.ghl.util.Logger;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * Time：2023/3/21
 * description: 扫描目标代码
 **/
public class TargetCodeScanner {
    /**
     * 需要注入代码的类
     */
    private final String TARGET_CLASS;

    /**
     * 被找到的代码所实现的接品类
     */
    private final String TARGET_INTERFACE;

    /**
     * 需要继承的父类
     */
    private final String TARGET_SUPER_CLASS;

    /**
     * {@link #TARGET_CLASS} 所在jar包
     */
    public File fileHasClass;
    /**
     * 所有需要注入的类
     */
    public ArrayList<String> allNeedInner = new ArrayList<>();

    public TargetCodeScanner(String targetClass, String targetInterface) {
        this(targetClass, targetInterface, null);
    }

    public TargetCodeScanner(String targetClass, String targetInterface, String superClass) {
        this.TARGET_CLASS = targetClass;
        this.TARGET_INTERFACE = targetInterface;
        this.TARGET_SUPER_CLASS = superClass;
    }

    /**
     * @param jarFile  源文件
     * @param destFile transform 后的jar文件
     */
    public void scanJar(File jarFile, File destFile) throws IOException {
        if (!jarFile.exists()) {
            return;
        }
        try (JarFile file = new JarFile(jarFile)) {
            //jar内的属性
            Enumeration<JarEntry> enumeration = file.entries();
            // 遍历jar内的文件
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = enumeration.nextElement();
                String entryName = jarEntry.getName();
                Logger.info("||---类详情：目标类为\tentryName= " + entryName);
                if (entryName.startsWith("android/support") || !entryName.endsWith(".class")) {
                    //support 不扫描
                    Logger.info("||---类详情：不扫描-------------\tentryName= " + entryName);
                    break;
                }
                if (Objects.equals(TARGET_CLASS, entryName)) {
                    //找到需要注入代码的目标类
                    fileHasClass = destFile;
                    Logger.info("||---类详情：找到目标类为-------------\tentryName= " + entryName);
                } else {
                    //如果不是目标类 则通过ASM来查找是否实现接口
                    scanClass(file.getInputStream(jarEntry), jarFile.getAbsolutePath());
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * 通过ASM 完成相应类数据数据处理 查找父类与实现类
     *
     * @param inputStream File级别的inputStream
     * @param filePath    相对路径
     */
    public void scanClass(InputStream inputStream, String filePath) {
        try {
            ClassReader cr = new ClassReader(inputStream);
            ClassWriter cw = new ClassWriter(cr, 0);
            ScanClassVisitor cv = new ScanClassVisitor(Opcodes.ASM5, cw, filePath, TARGET_INTERFACE);
            cr.accept(cv, ClassReader.EXPAND_FRAMES);
            inputStream.close();
        } catch (Exception ignored) {
            Logger.info("||---类详情：出现异常-------------\tfilePath= " + filePath);
        }
    }


    class ScanClassVisitor extends ClassVisitor {
        private String filePath;
        String interfaceClass;

        ScanClassVisitor(int api, ClassVisitor cv, String filePath, String interfaceClass) {
            super(api, cv);
            this.filePath = filePath;
            this.interfaceClass = interfaceClass;
        }

        boolean is(int access, int flag) {
            return (access & flag) == flag;
        }

        @Override
        public void visit(int version, int access, String name, String signature,
                          String superName, String[] interfaces) {
            super.visit(version, access, name, signature, superName, interfaces);
            //抽象类、接口、非public等类无法调用其无参构造方法
            if (is(access, groovyjarjarasm.asm.Opcodes.ACC_ABSTRACT)
                    || is(access, groovyjarjarasm.asm.Opcodes.ACC_INTERFACE)
                    || !is(access, groovyjarjarasm.asm.Opcodes.ACC_PUBLIC)
            ) {
                return;
            }
            if (Objects.equals(name, TARGET_SUPER_CLASS)) {
                //如果是目标父类，就不往下走了
                return;
            }
            if (Objects.equals(superName, TARGET_SUPER_CLASS)) {
                allNeedInner.add(name);
            } else {
                //遍历所有接口，如果有则 目标接口，则把该类加入数组里面
                for (String anInterface : interfaces) {
                    if (Objects.equals(anInterface, interfaceClass)) {
                        allNeedInner.add(name);
                    }
                }
            }
        }
    }

}
