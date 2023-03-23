//package com.ghl.plugin
//
//import com.android.build.api.transform.JarInput
//import com.android.build.gradle.AppPlugin
//import com.android.build.gradle.BaseExtension
//import com.ghl.plugin.java.follow.EachEveryone
//import com.ghl.plugin.java.follow.TargetCodeScanner
//import com.ghl.plugin.java.transform.ModuleTransform
//import com.ghl.plugin.java.util.CheckUtils
//import groovy.lang.Closure
//import org.codehaus.groovy.runtime.ResourceGroovyMethods
//import org.gradle.api.Plugin
//import org.gradle.api.Project
//import java.io.File
//import java.io.FileNotFoundException
//import java.io.IOException
//
///**
// * Time：2023/3/21
// * description:
// */
//class ModuleServicePlugin : Plugin<Project> {
//    override fun apply(target: Project) {
//        println("imc -- start")
//        val android = target.extensions.findByName("android") as BaseExtension?
////        if (android != null) {
//        //在android 的 闭包下才能设置
////            var inputName = target.name.replace("[^\\da-zA-Z\u4e00-\u9fa5.，,。？“”]+".toRegex(), "")
////            inputName = inputName.substring(0, 1).toUpperCase() + inputName.substring(1)
////            //
////            android.annotationProcessorOptions.argument("project_name", inputName);
////            android.
////        }
//        // 只有主项目才会加入transform 插入代码
//        if (target.plugins.hasPlugin(AppPlugin::class.java)) {
//            android?.registerTransform(
//                ModuleTransform(
//                    "ModuleService"
//                ) { transformInvocation ->
//                    val targetClass = "com/ghl/imc/ModuleServiceManager.class"
//                    val interfaceClass = "com/ghl/imc/ModuleService"
//                    val superClass = "com/ghl/imc/AbsModuleService"
//                    val codeScanner =
//                        TargetCodeScanner(targetClass, interfaceClass, superClass)
//                    try {
//                        EachEveryone.each(
//                            transformInvocation,
//                            object : Closure<JarInput>(null, null) {
//                                override fun call(vararg args: Any): JarInput {
//                                    val jarInput = args[0] as JarInput
//                                    val dest = args[1] as File
//                                    if (CheckUtils.isNeedScanJar(jarInput)) {
//                                        try {
//                                            codeScanner.scanJar(
//                                                jarInput.file,
//                                                dest
//                                            )
//                                        } catch (e: IOException) {
//                                            throw RuntimeException(e)
//                                        }
//                                    }
//                                    return super.call(*args)
//                                }
//                            },
//                            object : Closure<File>(null, null) {
//                                override fun call(file: Any): File {
//                                    if (file is File) {
//                                        try {
//                                            val inputStream =
//                                                ResourceGroovyMethods.newInputStream(
//                                                    file
//                                                )
//                                            if (CheckUtils.isNeedScanFile(file)) {
//                                                codeScanner.scanClass(
//                                                    inputStream,
//                                                    file.absolutePath
//                                                )
//                                            }
//                                        } catch (e: FileNotFoundException) {
//                                            throw RuntimeException(e)
//                                        }
//                                    }
//                                    return super.call(file)
//                                }
//                            })
//                    } catch (e: IOException) {
//                        throw RuntimeException(e)
//                    }
//                }
//            )
//        }
//    }
//}