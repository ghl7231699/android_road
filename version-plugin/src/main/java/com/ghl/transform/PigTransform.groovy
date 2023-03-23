package com.ghl.transform

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.ghl.flow.EachEveryone
import com.ghl.flow.TargetCodeScanner
import com.ghl.inject.InjectInfo
import com.ghl.inject.RegistryCodeGenerator

public class PigTransform extends Transform {


    // plugin 所需要的name
    private String mName

    PigTransform(String mName) {
        this.mName = mName
    }

    @Override
    String getName() {
        return mName
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        //固定写法
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        //固定写法
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        // 当前不支持 instance run
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        //常量
        final String targetClass = "com/ghl/router/lib/Router.class";
        final String interfaceClass = "com/ghl/router/lib/RouterClassProvider";
        final String invokingMethodName = "bindClassProvider";

        TargetCodeScanner mScanner = new TargetCodeScanner(targetClass, interfaceClass);

        EachEveryone.each(transformInvocation,
                { jarInput, dest ->
                    if (isNeedScanJar(jarInput)) {
                        mScanner.scanJar(jarInput.file, dest);
                    }
                },
                {
                    file ->
                        if (isNeedScanFile(file)) {
                            mScanner.scanClass(file.newInputStream(), file.absolutePath);
                        }
                }
        )

        // info 生成 start
        InjectInfo info = new InjectInfo();
        //目标类 需要去掉.class
        info.targetClass = targetClass.substring(0, targetClass.lastIndexOf('.'));
        //插入代码的位置 静态代码块
        info.initMethodName = "<clinit>";
        //被实现的接口
        info.interfaceClass = interfaceClass;
        // jar包
        info.targetFile = mScanner.fileHasClass;
        // 所有类
        info.allInter.addAll(mScanner.allNeedInner);
        //注册方法
        info.invokingMethodName = invokingMethodName;
        //父类
        // info 生成 end

        Logger.info("||---目标类，${info.targetClass}\t${info.allInter}");
        //生成代码
        if (info.targetClass && info.allInter.size() > 0) {
            //文件存在 ，并且实现类存在时，才修改字节码
            RegistryCodeGenerator.insertInitCodeTo(info);
            Logger.info("||---找到目标类，开始进行替换。。。。。。");
        }
    }

}