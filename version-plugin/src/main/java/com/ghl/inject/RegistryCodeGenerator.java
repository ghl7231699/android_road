package com.ghl.inject;

import com.ghl.util.Logger;

import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

/**
 * Time：2023/3/22
 * description: 字节码修改类
 **/

public class RegistryCodeGenerator {
    public InjectInfo extension;

    private RegistryCodeGenerator(InjectInfo extension) {
        this.extension = extension;
    }

    public static void insertInitCodeTo(InjectInfo extension) throws IOException {
        if (extension != null && !extension.allInter.isEmpty()) {
            Logger.info("||---找到目标类，开始进行注入。。。。。。");
            RegistryCodeGenerator processor = new RegistryCodeGenerator(extension);
            File file = extension.targetFile;
            if (file.getName().endsWith(".jar")) {
                processor.generateCodeIntoJarFile(file);
            } else {
                processor.generateCodeIntoClassFile(file);
            }

        }
    }

    boolean isInitClass(String entryName) {
        if ((entryName == null) || !entryName.endsWith(".class")) {
            return false;
        }
        if (extension.targetClass != null) {
            entryName = entryName.substring(0, entryName.lastIndexOf('.'));
            return extension.targetClass.equals(entryName);
        }
        return false;
    }

    //处理jar包中的class代码注入
    private File generateCodeIntoJarFile(File jarFile) throws IOException {
        if (jarFile != null) {
            File optJar = new File(jarFile.getParent(), jarFile.getName() + ".opt");
            if (optJar.exists()) {
                optJar.delete();
            }
            JarFile file = new JarFile(jarFile);
            Enumeration enumeration = file.entries();
            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(optJar));
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) enumeration.nextElement();
                String entryName = jarEntry.getName();
                ZipEntry zipEntry = new ZipEntry(entryName);
                InputStream inputStream = file.getInputStream(jarEntry);
                ;
                jarOutputStream.putNextEntry(zipEntry);
                if (isInitClass(entryName)) {
                    Logger.info("generate code into:" + entryName);
                    byte[] bytes = doGenerateCode(inputStream);
                    jarOutputStream.write(bytes);
                } else {
                    jarOutputStream.write(IOUtils.toByteArray(inputStream));
                }
                inputStream.close();
                jarOutputStream.closeEntry();
            }
            jarOutputStream.close();
            file.close();

            if (jarFile.exists()) {
                jarFile.delete();
            }
            optJar.renameTo(jarFile);
        }
        return jarFile;
    }

    /**
     * 处理class的注入
     *
     * @param file class文件
     * @return 修改后的字节码文件内容
     */
    private byte[] generateCodeIntoClassFile(File file) throws IOException {
        File optClass = new File(file.getParent(), file.getName() + ".opt");

        FileInputStream inputStream = new FileInputStream(file);
        FileOutputStream outputStream = new FileOutputStream(optClass);

        byte[] bytes = doGenerateCode(inputStream);
        outputStream.write(bytes);
        inputStream.close();
        outputStream.close();
        if (file.exists()) {
            file.delete();
        }
        optClass.renameTo(file);
        return bytes;
    }

    private byte[] doGenerateCode(InputStream inputStream) throws IOException {
        ClassReader cr = new ClassReader(inputStream);
        ClassWriter cw = new ClassWriter(cr, 0);
        ClassVisitor cv = new MyClassVisitor(Opcodes.ASM5, cw);
        cr.accept(cv, ClassReader.EXPAND_FRAMES);
        return cw.toByteArray();
    }


    class MyClassVisitor extends ClassVisitor {

        MyClassVisitor(int api, ClassVisitor cv) {
            super(api, cv);
        }

        public void visit(int version, int access, String name, String signature,
                          String superName, String[] interfaces) {
            super.visit(version, access, name, signature, superName, interfaces);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc,
                                         String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
            if (Objects.equals(name, extension.initMethodName)) { //注入代码到指定的方法之中
                boolean _static = (access & Opcodes.ACC_STATIC) > 0;
                mv = new MyMethodVisitor(Opcodes.ASM5, mv, _static);
            }
            return mv;
        }
    }

    class MyMethodVisitor extends MethodVisitor {
        boolean _static;

        public MyMethodVisitor(int api, MethodVisitor mv, boolean _static) {
            super(api, mv);
            this._static = _static;
        }

        @Override
        public void visitInsn(int opcode) {
            if ((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN)) {
                for (String name : extension.allInter) {
                    if (!_static) {
                        //加载this
                        mv.visitVarInsn(Opcodes.ALOAD, 0);
                    }
                    // 接口名称
                    String paramTypeInterface;

                    //用无参构造方法创建一个组件实例
                    mv.visitLdcInsn("");
                    mv.visitTypeInsn(Opcodes.NEW, name);
                    mv.visitInsn(Opcodes.DUP);
                    mv.visitMethodInsn(Opcodes.INVOKESPECIAL, name, "<init>", "()V", false);
                    paramTypeInterface = extension.interfaceClass;
                    int methodOpcode = _static ? Opcodes.INVOKESTATIC : Opcodes.INVOKESPECIAL;
                    //调用注册方法将组件实例注册到组件库中
                    mv.visitMethodInsn(methodOpcode
                            , extension.targetClass
                            , extension.invokingMethodName
                            , "(L" + paramTypeInterface + ";)V"
                            , false);
                }
            }
            super.visitInsn(opcode);
        }

        @Override
        public void visitMaxs(int maxStack, int maxLocals) {
            super.visitMaxs(maxStack + 4, maxLocals);
        }
    }

}
