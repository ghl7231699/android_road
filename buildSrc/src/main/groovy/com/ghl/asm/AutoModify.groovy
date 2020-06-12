package com.ghl.asm

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

/**
 * Des:修改字节码
 */
class AutoModify {

    static byte[] modifyClasses(String className, byte[] srcByteCode) {
        byte[] classBytesCode = null
        try {
            classBytesCode = modifyClass(srcByteCode)
            return classBytesCode
        } catch (Exception e) {
            e.printStackTrace()
        }
        if (classBytesCode == null) {
            classBytesCode = srcByteCode
        }
        return classBytesCode
    }
    /**
     * 真正修改类中方法字节码
     */
    private static byte[] modifyClass(byte[] srcClass) throws IOException {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS)
        ClassVisitor adapter = new AutoClassVisitor(classWriter)
        ClassReader cr = new ClassReader(srcClass)
//        try {
        cr.accept(adapter, ClassReader.EXPAND_FRAMES)
//        } catch (Exception e) {
//            Logger.info("------------->accept error " + e.getMessage())
//        }
        return classWriter.toByteArray()
    }
    /**
     * 查看修改字节码后的方法
     */
    private static void seeModifyMethod(byte[] srcClass) throws IOException {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS)
        ClassVisitor visitor = new AutoClassVisitor(classWriter)
        visitor.seeModifyMethod = true
        ClassReader cr = new ClassReader(srcClass)
        if (visitor != null) {
            cr.accept(visitor, ClassReader.EXPAND_FRAMES)
        }
    }
}