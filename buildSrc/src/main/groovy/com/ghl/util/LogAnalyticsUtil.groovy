package com.ghl.util

import jdk.internal.org.objectweb.asm.Opcodes
import org.objectweb.asm.MethodVisitor

class LogAnalyticsUtil implements Opcodes {
    private static final HashSet<String> targetFragmentClass = new HashSet()
    private static final HashSet<String> targetMenuMethodDesc = new HashSet()
    private static final HashSet<String> targetActivityMethodDesc = new HashSet()
    private static final HashSet<String> targetApplicationMethodDesc = new HashSet<>()
    private static final HashSet<String> targetExceptionMethodDesc = new HashSet<>()
    private static final HashSet<String> targetLoginActivityMethodDesc = new HashSet<>()
    static {
        /**
         * Menu
         */
        targetMenuMethodDesc.add("onContextItemSelected(Landroid/view/MenuItem;)Z")
        targetMenuMethodDesc.add("onOptionsItemSelected(Landroid/view/MenuItem;)Z")
        targetMenuMethodDesc.add("onNavigationItemSelected(Landroid/view/MenuItem;)Z")

        /**
         * Fragment
         */
        targetFragmentClass.add('android/support/v4/app/Fragment')
        targetFragmentClass.add('android/support/v4/app/ListFragment')
        targetFragmentClass.add('android/support/v4/app/DialogFragment')

        /**
         * For AndroidX Fragment
         */
        targetFragmentClass.add('androidx/fragment/app/Fragment')
        targetFragmentClass.add('androidx/fragment/app/ListFragment')
        targetFragmentClass.add('androidx/fragment/app/DialogFragment')

        /**
         * Activity
         */
        targetActivityMethodDesc.add('android/app/Activity')
        targetActivityMethodDesc.add('android/support/v7/app/AppCompatActivity')
        targetActivityMethodDesc.add('com/mmc/lamandys/liba_datapick/base/BaseActivity')

        /**
         * Application
         */

        targetApplicationMethodDesc.add('android/app/Application')
        /**
         * Exception
         */
        targetExceptionMethodDesc.add('java/lang/RuntimeException')
        targetExceptionMethodDesc.add('java/lang/Exception')
        /**
         * 第一个页面
         */
        targetLoginActivityMethodDesc.add("com/mmc/lamandys/liba_datapick/base/BaseActivity")
    }

    static boolean isSynthetic(int access) {
        return (access & ACC_SYNTHETIC) != 0
    }

    static boolean isPrivate(int access) {
        return (access & ACC_PRIVATE) != 0
    }

    static boolean isPublic(int access) {
        return (access & ACC_PUBLIC) != 0
    }

    static boolean isStatic(int access) {
        return (access & ACC_STATIC) != 0
    }

    static boolean isProtected(int access) {
        return (access & ACC_PROTECTED) != 0
    }

    static boolean isTargetMenuMethodDesc(String nameDesc) {
        return targetMenuMethodDesc.contains(nameDesc)
    }

    static boolean isTargetFragmentClass(String className) {
        return targetFragmentClass.contains(className)
    }

    static boolean isInstanceOfFragment(String superName) {
        return targetFragmentClass.contains(superName)
    }

    static boolean isInstanceOfActivity(String superName) {
        return targetActivityMethodDesc.contains(superName)
    }

    static boolean isLauncherActivity(String superName) {
        return targetLoginActivityMethodDesc.contains(superName)
    }

    static boolean isInstanceOfApplication(String superName) {
        return targetApplicationMethodDesc.contains(superName)
    }

    static boolean isInstanceOfException(String superName) {
        return targetExceptionMethodDesc.contains(superName)
    }

    static void visitMethodWithLoadedParams(MethodVisitor methodVisitor, int opcode, String owner, String methodName, String methodDesc, int start, int count, List<Integer> paramOpcodes) {
        for (int i = start; i < start + count; i++) {
            methodVisitor.visitVarInsn(paramOpcodes[i - start], i)
        }
        methodVisitor.visitMethodInsn(opcode, owner, methodName, methodDesc, false)
    }
}
