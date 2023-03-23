package com.ghl.plugin;

import com.android.build.api.transform.JarInput;
import com.android.build.gradle.AppPlugin;
import com.android.build.gradle.BaseExtension;
import com.ghl.EachCallBack;
import com.ghl.flow.EachEveryone;
import com.ghl.flow.TargetCodeScanner;
import com.ghl.inject.InjectInfo;
import com.ghl.inject.RegistryCodeGenerator;
import com.ghl.transform.TempTransform;
import com.ghl.util.CheckUtils;
import com.ghl.util.Logger;

import org.codehaus.groovy.runtime.ResourceGroovyMethods;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.Nullable;

/**
 * Time：2023/3/22
 * description: 路由插件
 **/
public class RouterPlugin implements Plugin<Project> {

    @Override
    public void apply(@Nullable Project project) {
        Logger.info("router ----- start ");
//        //开始为apt设置相应的项目数据
        BaseExtension android;
        if (project == null) {
            return;
        }
        android = (BaseExtension) project.getExtensions().findByName("android");
        if (android == null) {
            return;
        }
        // router lib
        Project routerProject = project.getRootProject().findProject("router");
        if (routerProject != null) {
            project.getDependencies().add("implementation", routerProject);
        }
        // router apt
        Project aptProject = project.getRootProject().findProject("router:router_apt");
        if (aptProject != null) {
            project.getDependencies().add("kapt", aptProject);
        }
        //在android 的 闭包下才能设置
        String inputName = project.getName().replaceAll("[^\\da-zA-Z\u4e00-\u9fa5.，,。？“”]+", "");
        inputName = inputName.substring(0, 1).toUpperCase() + inputName.substring(1);
        android.getDefaultConfig().getJavaCompileOptions().getAnnotationProcessorOptions().argument("project_name", inputName);

        if (project.getPlugins().hasPlugin(AppPlugin.class)) {
            android.registerTransform(new TempTransform("PigRouter", transformInvocation -> {
                final String targetClass = "com/ghl/router/lib/Router.class";
                final String interfaceClass = "com/ghl/router/lib/RouterClassProvider";
                final String invokingMethodName = "bindClassProvider";

                TargetCodeScanner codeScanner = new TargetCodeScanner(targetClass, interfaceClass);
                try {
                    EachEveryone.each(transformInvocation, new EachCallBack() {
                        @Override
                        public void call(JarInput jarInput, File dest) {
                            if (CheckUtils.isNeedScanJar(jarInput)) {
                                try {
                                    codeScanner.scanJar(jarInput.getFile(), dest);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }

                        @Override
                        public void call(File file) {

                        }
                    }, new EachCallBack() {
                        @Override
                        public void call(JarInput jarInput, File dest) {

                        }

                        @Override
                        public void call(File file) {
                            try {
                                BufferedInputStream inputStream = ResourceGroovyMethods.newInputStream(file);
                                if (CheckUtils.isNeedScanFile(file)) {
                                    codeScanner.scanClass(inputStream, file.getAbsolutePath());
                                }
                            } catch (FileNotFoundException e) {
//                                    e.printStackTrace();
                            }
                        }
                    });
                    // info 生成 start
                    InjectInfo info = new InjectInfo();
                    //目标类 需要去掉.class
                    info.targetClass = targetClass.substring(0, targetClass.lastIndexOf('.'));
                    //插入代码的位置 静态代码块
                    info.initMethodName = "<clinit>";
                    //被实现的接口
                    info.interfaceClass = interfaceClass;
                    // jar包
                    info.targetFile = codeScanner.fileHasClass;
                    // 所有类
                    info.allInter.addAll(codeScanner.allNeedInner);
                    //注册方法
                    info.invokingMethodName = invokingMethodName;
                    //父类
                    // info 生成 end
                    Logger.info("||---目标类，" + info.targetClass + "\t" + info.allInter);
                    //生成代码
                    if (info.targetClass != null && info.allInter.size() > 0) {
                        //文件存在 ，并且实现类存在时，才修改字节码
                        RegistryCodeGenerator.insertInitCodeTo(info);
                        Logger.info("||---找到目标类，开始进行替换。。。。。。");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        }

        Logger.info("router ----- end ");
    }
}
