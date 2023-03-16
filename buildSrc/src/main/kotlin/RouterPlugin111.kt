//import org.gradle.api.Plugin
//import org.gradle.api.Project
//import java.util.*
//import com.android.build.api.dsl.*
//import com.android.build.gradle.AppPlugin
//import java.io.File
//import com.ghl.util.CheckUtils
//import com.android.build.api.transform.JarInput
//import com.ghl.PigTransform
//
//class RouterPlugin : Plugin<Project> {
//    override fun apply(project: Project) {
//        println("router ----- start ")
//        //加载相应的lib 与 apt
//        val compile = "implementation"
//        val apt = "kapt"
//        // router lib
//        project.rootProject.findProject("router")?.run {
//            project.dependencies.add(compile, this)
//        }
//        // router apt
//        project.rootProject.findProject("router:router_apt")?.run {
//            project.dependencies.add(apt, this)
//        }
//
//        //开始为apt设置相应的项目数据
//        val android = project.extensions.findByName("android")
//        if (android != null) {
//            //在android 的 闭包下才能设置
//            val inputName = project.name.replace("[^0-9a-zA-Z\u4e00-\u9fa5.，,。？“”]+", "")
//            val newInputName =
//                inputName.substring(0, 1).toUpperCase(Locale.ROOT) + inputName.substring(1)
//            if (android is LibraryExtension) {
//                android.defaultConfig.javaCompileOptions.annotationProcessorOptions.argument(
//                    "project_name",
//                    newInputName
//                )
//
//
//                // 只有主项目才会加入transform 插入代码
//                if (project.plugins.hasPlugin(AppPlugin)) {
////                    registerTransform( PigTransform ("PigRouter", { transformInvocation ->
////                        //常量
////                        final String targetClass = "com/ghl/router/lib/Router.class"
////                        final String interfaceClass = "com/ghl/router/lib/RouterClassProvider"
////                        final String invokingMethodName = "bindClassProvider"
////                        TargetCodeScanner mScanner = new TargetCodeScanner(targetClass, interfaceClass)
////                        EachEveryone.each(transformInvocation,
////                            { jarInput, dest ->
////                                if (isNeedScanJar(jarInput)) {
////                                    mScanner.scanJar(jarInput.file, dest)
////                                }
////                            },
////                            { file ->
////                                if (isNeedScanFile(file)) {
////                                    mScanner.scanClass(file.newInputStream(), file.absolutePath)
////                                }
////                            }
////                        )
////
////                        // info 生成 start
////                        InjectInfo info = new InjectInfo()
////                        //目标类 需要去掉.class
////                        info.targetClass = targetClass.substring(0, targetClass.lastIndexOf('.'))
////                        //插入代码的位置 静态代码块
////                        info.initMethodName = "<clinit>"
////                        //被实现的接口
////                        info.interfaceClass = interfaceClass
////                        // jar包
////                        info.targetFile = mScanner.fileHasClass
////                        // 所有类
////                        info.allInter.addAll(mScanner.allNeedInner)
////                        //注册方法
////                        info.invokingMethodName = invokingMethodName
////                        //父类
////                        // info 生成 end
////
////                        Logger.info("||---目标类，${info.targetClass}\t${info.allInter}")
////                        //生成代码
////                        if (info.targetClass && info.allInter.size() > 0) {
////                            //文件存在 ，并且实现类存在时，才修改字节码
////                            RegistryCodeGenerator.insertInitCodeTo(info)
////                            Logger.info("||---找到目标类，开始进行替换。。。。。。")
////                        }
////                    }))
//                }
//            }
//        }
//
//        println("router ----- end ")
//    }
//
//    /**
//     * 对于一些系统包 ，是不需要更新解析的
//     * @param jarInput jar 包
//     * @return 是否需要解析
//     */
//    private fun isNeedScanJar(jarInput: JarInput) = CheckUtils.isNeedScanJar(jarInput)
//
//    private fun isNeedScanFile(file: File) = CheckUtils.isNeedScanFile(file)
//}