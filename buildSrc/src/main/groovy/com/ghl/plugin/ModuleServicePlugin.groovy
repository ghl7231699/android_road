package com.ghl.plugin

import com.android.build.gradle.AppPlugin
import com.ghl.inject.InjectInfo
import org.gradle.api.Plugin
import org.gradle.api.Project

public class ModuleServicePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "imc --- start"

        //加载相应的lib 与 apt
        def compile = "implementation"
        def apt = "annotationProcessor"
        // router lib
        Project routerProject = project.rootProject.findProject("imc")
        project.dependencies.add(compile, routerProject)

        def android = project.extensions.findByName("android")
        if (android) {
            //在android 的 闭包下才能设置
            String inputName = project.name.replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5.，,。？“”]+", "")
            inputName = inputName.substring(0, 1).toUpperCase() + inputName.substring(1)
            android.defaultConfig.javaCompileOptions.annotationProcessorOptions.argument("project_name", inputName)
        }

        // 只有主项目才会加入transform 插入代码
        if (project.plugins.hasPlugin(AppPlugin)) {
            android.registerTransform(new com.ghl.PigTransform("PigModuleService", { transformInvocation ->
                final String targetClass = "com/xiaozhu/xzdz/imc/ModuleServiceManager.class"
                final String interfaceClass = "com/xiaozhu/xzdz/imc/ModuleService"
                final String superClass = "com/xiaozhu/xzdz/imc/AbsModuleService"

                com.ghl.flow.TargetCodeScanner codeScanner = new com.ghl.flow.TargetCodeScanner(targetClass, interfaceClass, superClass)

                com.ghl.flow.EachEveryone.each(transformInvocation, { jarInput, dest ->
                    if (com.ghl.util.CheckUtils.isNeedScanJar(jarInput)) {
                        codeScanner.scanJar(jarInput.file, dest)
                    }
                }, { file ->
                    if (com.ghl.util.CheckUtils.isNeedScanFile(file)) {
                        codeScanner.scanClass(file.newInputStream(), file.absolutePath)
                    }
                })

                InjectInfo info = new InjectInfo()
                //目标类 需要去掉.class
                info.targetClass = targetClass.substring(0, targetClass.lastIndexOf('.'))
                //插入代码的位置 静态代码块
                info.initMethodName = "<clinit>"
                //被实现的接口
                info.interfaceClass = interfaceClass
                // jar包
                info.targetFile = codeScanner.fileHasClass
                // 所有类
                info.allInter.addAll(codeScanner.allNeedInner)
                //注册方法
                info.invokingMethodName = "bindService"
                //父类
                info.superClass = superClass
                println "rain === ${info.targetClass}"
                if (info.targetClass && info.allInter.size() > 0) {
                    //文件存在 ，并且实现类存在时，才修改字节码
                    com.ghl.inject.RegistryCodeGenerator.insertInitCodeTo(info)
                }
            }))
        }
    }
}