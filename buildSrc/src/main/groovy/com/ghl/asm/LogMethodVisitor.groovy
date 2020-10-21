package com.ghl.asm

import com.ghl.bean.LogMethodCell
import com.ghl.config.LogHookConfig
import com.ghl.util.LogAnalyticsUtil
import com.ghl.util.Logger
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

/**
 * 针对日志采集sdk埋点的方法进行修改
 */
class LogMethodVisitor extends AdviceAdapter {

    public HashSet<String> visitedFragMethods
    public HashSet<String> sActivityMethods
    String methodName
    int access
    MethodVisitor methodVisitor
    String methodDesc
    String superName
    String className
    String[] interfaces
    boolean isAutoTrackViewOnClickAnnotation = false
    boolean isAutoTrackIgnoreTrackOnClick = false
    boolean isHasInstrumented = false
    boolean isHasTracked = false

    LogMethodVisitor(MethodVisitor methodVisitor, int access, String name, String desc,
                     String superName, String className, String[] interfaces, HashSet<String>
                             visitedFragMethods, HashSet<String> sActivityMethods) {
        super(Opcodes.ASM5, methodVisitor, access, name, desc)
        this.methodName = name
        this.access = access
        this.methodVisitor = methodVisitor
        this.methodDesc = desc
        this.superName = superName
        this.className = className
        this.interfaces = interfaces
        this.visitedFragMethods = visitedFragMethods
        this.sActivityMethods = sActivityMethods
    }

    @Override
    void visitEnd() {
        super.visitEnd()
        if (isHasTracked) {
            Logger.info("||Hooked method------------->: ${methodName}${methodDesc}")
        }
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter()

        if (isAutoTrackIgnoreTrackOnClick) {
            return
        }
        if (isHasTracked) {
            return
        }
        /**
         * 在 android.gradle 的 3.2.1 版本中，针对 view 的 setOnClickListener 方法 的 lambda 表达式做特殊处理。
         */
        if (methodName.trim().startsWith('lambda$') && LogAnalyticsUtil.isPrivate(access) && LogAnalyticsUtil.isSynthetic(access)) {
            LogMethodCell logMethodCell = LogHookConfig.sLambdaMethods.get(desc)
            if (logMethodCell != null) {
                int paramStart = logMethodCell.paramsStart
                if (LogAnalyticsUtil.isStatic(access)) {
                    paramStart = paramStart - 1
                }
                LogAnalyticsUtil.visitMethodWithLoadedParams(methodVisitor, Opcodes.INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE,
                        logMethodCell.agentName, logMethodCell.agentDesc,
                        paramStart, logMethodCell.paramsCount, logMethodCell.opcodes)
                isHasTracked = true
                return
            }
        }
        /**
         * 只处理public static protected 方法
         */
        if (!(LogAnalyticsUtil.isPublic(access) && !LogAnalyticsUtil.isStatic(access))
                && !LogAnalyticsUtil.isProtected(access)) {
            return
        }

        /**
         * Method 描述信息
         */
        String methodNameDesc = methodName + methodDesc

        /**
         * Fragment
         * 目前支持 android/support/v4/app/ListFragment 和 android/support/v4/app/Fragment
         */
        if (LogAnalyticsUtil.isInstanceOfFragment(superName)) {
            LogMethodCell logMethodCell = LogHookConfig.sFragmentMethods.get(methodNameDesc)
            if (logMethodCell != null) {
                visitedFragMethods.add(methodNameDesc)
                LogAnalyticsUtil.visitMethodWithLoadedParams(methodVisitor, Opcodes.INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE, logMethodCell.agentName, logMethodCell.agentDesc, logMethodCell.paramsStart, logMethodCell.paramsCount, logMethodCell.opcodes)
                isHasTracked = true
                return
            }
        }

        /**
         * Activity
         */
        if (LogAnalyticsUtil.isInstanceOfActivity(superName)) {
            LogMethodCell activityMethod = LogHookConfig.sActivityMethods.get(methodNameDesc)
            if (activityMethod != null) {
                if (sActivityMethods != null) {
                    sActivityMethods.add(methodNameDesc)
                }
                LogAnalyticsUtil.visitMethodWithLoadedParams(methodVisitor, Opcodes.INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE, activityMethod.agentName, activityMethod.agentDesc, activityMethod.paramsStart, activityMethod.paramsCount, activityMethod.opcodes)
                isHasTracked = true
                return
            }
        }

        /**
         * Application
         */
        if (LogAnalyticsUtil.isInstanceOfApplication(superName)) {
            LogMethodCell appMethod = LogHookConfig.sApplicationMethods.get(methodNameDesc)
            if (appMethod != null) {
                LogAnalyticsUtil.visitMethodWithLoadedParams(methodVisitor, Opcodes.INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE, appMethod.agentName, appMethod.agentDesc, appMethod.paramsStart, appMethod.paramsCount, appMethod.opcodes)
                isHasTracked = true
                return
            }
        }

//        /**
//         * activity onCreate
//         */
//        if (methodNameDesc == 'onCreate(Landroid/os/Bundle;)V') {
//            Logger.info("符合onCreate条件：" + methodNameDesc)
//            methodVisitor.visitVarInsn(ALOAD, 0)
//            methodVisitor.visitMethodInsn(INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE, "startMethod",
//                    "(Ljava/lang/Object;)V", false)
//            isHasTracked = true
//            return
//        }
//        /**
//         * activity onDestroy
//         */
//        if (methodNameDesc == 'onDestroy()V') {
//            Logger.info("符合onDestroy条件：" + methodNameDesc)
//            methodVisitor.visitVarInsn(ALOAD, 0)
//            methodVisitor.visitMethodInsn(INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE,
//                    "endMethod", "(Ljava/lang/Object;)V", false)
//            isHasTracked = true
//            return
//        }

        /**
         * Menu
         * 目前支持 onContextItemSelected(MenuItem item)、onOptionsItemSelected(MenuItem item)
         */
        if (LogAnalyticsUtil.isTargetMenuMethodDesc(methodNameDesc)) {
            methodVisitor.visitVarInsn(ALOAD, 0)
            methodVisitor.visitVarInsn(ALOAD, 1)
            methodVisitor.visitMethodInsn(INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE, "trackMenuItem", "(Ljava/lang/Object;Landroid/view/MenuItem;)V", false)
            isHasTracked = true
            return
        }

        if (methodNameDesc == 'onDrawerOpened(Landroid/view/View;)V') {
            methodVisitor.visitVarInsn(ALOAD, 1)
            methodVisitor.visitMethodInsn(INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE, "trackDrawerOpened", "(Landroid/view/View;)V", false)
            isHasTracked = true
            return
        } else if (methodNameDesc == 'onDrawerClosed(Landroid/view/View;)V') {
            methodVisitor.visitVarInsn(ALOAD, 1)
            methodVisitor.visitMethodInsn(INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE, "trackDrawerClosed", "(Landroid/view/View;)V", false)
            isHasTracked = true
            return
        }

        if (className == 'android/databinding/generated/callback/OnClickListener') {
            if (methodNameDesc == 'onClick(Landroid/view/View;)V') {
                methodVisitor.visitVarInsn(ALOAD, 1)
                methodVisitor.visitMethodInsn(INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE, "trackViewOnClick", "(Landroid/view/View;)V", false)
                isHasTracked = true
                return
            }
        }

        if (className.startsWith('android') || className.startsWith('androidx')) {
            return
        }

        if (methodNameDesc == 'onItemSelected(Landroid/widget/AdapterView;Landroid/view/View;IJ)V' || methodNameDesc == "onListItemClick(Landroid/widget/ListView;Landroid/view/View;IJ)V") {
            methodVisitor.visitVarInsn(ALOAD, 1)
            methodVisitor.visitVarInsn(ALOAD, 2)
            methodVisitor.visitVarInsn(ILOAD, 3)
            methodVisitor.visitMethodInsn(INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE, "trackListView", "(Landroid/widget/AdapterView;Landroid/view/View;I)V", false)
            isHasTracked = true
            return
        }

        if (isAutoTrackViewOnClickAnnotation) {
            if (methodDesc == '(Landroid/view/View;)V') {
                methodVisitor.visitVarInsn(ALOAD, 1)
                methodVisitor.visitMethodInsn(INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE, "trackViewOnClick", "(Landroid/view/View;)V", false)
                isHasTracked = true
                return
            }
        }

        if (interfaces != null && interfaces.length > 0) {
            LogMethodCell logMethod = LogHookConfig.sInterfaceMethods.get(methodNameDesc)
            if (logMethod != null && interfaces.contains(logMethod.parent)) {
                LogAnalyticsUtil.visitMethodWithLoadedParams(methodVisitor, Opcodes.INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE
                        , logMethod.agentName, logMethod.agentDesc, logMethod.paramsStart, logMethod.paramsCount, logMethod.opcodes)
                isHasTracked = true
            }
        }

        if (!isHasTracked) {
            if (methodNameDesc == 'onClick(Landroid/view/View;)V') {
                methodVisitor.visitVarInsn(ALOAD, 1)
                methodVisitor.visitMethodInsn(INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE, "trackViewOnClick", "(Landroid/view/View;)V", false)
                isHasTracked = true
            }
        }
        if (methodNameDesc == 'divided()V') {//
            Label l0 = new Label()
            l1 = new Label()
            l2 = new Label()
            methodVisitor.visitTryCatchBlock(l0, l1, l2, "java/lang/Exception")
            methodVisitor.visitLabel(l0)

        }
    }
    Label l1, l2

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode)
        methodVisitor.visitLabel(l1)
        Label l3 = new Label()
        mv.visitJumpInsn(GOTO, l3)
        mv.visitLabel(l2)
        mv.visitVarInsn(ASTORE, 1)
    }
/**
 * 该方法是当扫描器扫描到类注解声明时进行调用
 * @param s 注解的类型。它使用的是（“L” + “类型路径” + “;”）形式表述
 * @param b 表示的是，该注解是否在 JVM 中可见
 * 1.RetentionPolicy.SOURCE：声明注解只保留在 Java 源程序中，在编译 Java 类时注解信息不会被写入到 Class。如果使用的是这个配置 ASM 也将无法探测到这个注解。
 * 2.RetentionPolicy.CLASS：声明注解仅保留在 Class 文件中，JVM 运行时并不会处理它，这意味着 ASM 可以在 visitAnnotation 时候探测到它，但是通过Class 反射无法获取到注解信息。
 * 3.RetentionPolicy.RUNTIME：这是最常用的一种声明，ASM 可以探测到这个注解，同时 Java 反射也可以取得注解的信息。所有用到反射获取的注解都会用到这个配置，就是这个原因。
 * @return
 */
    @Override
    AnnotationVisitor visitAnnotation(String s, boolean b) {
//        if (s == 'Lcom/mmc/lamandys/liba_datapick/AutoTrackDataViewOnClick;') {
//            isAutoTrackViewOnClickAnnotation = true
//            Logger.info("||发现 ${methodName}${methodDesc} 有注解 @AutoTrackDataViewOnClick")
//        }
//
//        if (s == 'Lcom/mmc/lamandys/liba_datapick/AutoIgnoreTrackDataOnClick;') {
//            isAutoTrackIgnoreTrackOnClick = true
//            Logger.info("||发现 ${methodName}${methodDesc} 有注解 @AutoIgnoreTrackDataOnClick")
//        }
//
//        if (s == 'Lcom/mmc/lamandys/liba_datapick/AutoDataInstrumented;') {
//            isHasInstrumented = true
//        }
        return super.visitAnnotation(s, b)
    }

//    @Override
//    void visitInsn(int opcode) {
//        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
////            methodVisitor.visitFieldInsn(GETSTATIC, owner, "timer", "J")
////            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/System",
////                    "currentTimeMillis", "()J")
////            methodVisitor.visitInsn(LADD)
////            methodVisitor.visitFieldInsn(PUTSTATIC, owner, "timer", "J")
//        }
//        methodVisitor.visitInsn(opcode)
//    }

    /**
     * 获取try catch 代码块
     * @param start try的开始位置
     * @param end try的结束位置
     * @param handler catch块的开始位置
     * @param type 异常类型
     */
//    @Override
//    void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
//
//        if (LogAnalyticsUtil.isInstanceOfException(type)) {
////            Logger.info(String.format("输出结果start=%s\tend=%s\thandler=%s\ttype=%s\t", start, end,
////                    handler, type))
////            this.type = type
//
//            methodVisitor.visitVarInsn(ALOAD, 2)
//            methodVisitor.visitMethodInsn(INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE,
//                    "printThisStackTraces", "(Ljava/lang/Object;)V", false)
//            isHasTracked = true
//        }
//    }
}

