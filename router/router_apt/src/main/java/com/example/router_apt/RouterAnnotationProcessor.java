package com.example.router_apt;

import com.example.router_annotation.Router;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

@SupportedSourceVersion(SourceVersion.RELEASE_8)//java版本支持
@SupportedAnnotationTypes({//标注注解处理器支持的注解类型
        "com.example.router_annotation.Router"
})
public class RouterAnnotationProcessor extends AbstractProcessor {

    /**
     * 根据 gradle 跟配置的 project_name 来获得相应的名称
     * 影响到类名生成
     */
    private String projectName;

    /**
     * log 所用
     */
    private Messager mMessager;

    /**
     * 写文件
     */
    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        Map<String, String> options = processingEnv.getOptions();

        projectName = options.get("project_name");

        mMessager = processingEnvironment.getMessager();

        mFiler = processingEnvironment.getFiler();

    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * @return 支持的注解类型
     * 即是这个处理器是需要注册在哪几个注解上的, 也可以通过@SupportedAnnotationTypes来指定
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        LinkedHashSet<String> types = new LinkedHashSet<>();
        types.add(Router.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//        if (projectName == null || projectName.length() == 0) {
//            return false;
//        } else {
//            log("你好啊");
//        }

        log("---------------------->Start Processor");

        StringBuilder className = new StringBuilder(projectName + "RouterData");

        //生成类
        TypeSpec.Builder clazz = TypeSpec.classBuilder(className.toString())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addSuperinterface(ClassName.get("com.example.router", "RouterClassProvider"));

        //生成方法

        MethodSpec.Builder method = MethodSpec.methodBuilder("getPageClass")
                .addParameter(String.class, "page")
                .addModifiers(Modifier.PUBLIC)
                .returns(Class.class)
                .addAnnotation(Override.class)
                .addCode("switch(page){\n");

        Set<TypeElement> typeElements = ElementFilter.typesIn(roundEnvironment.getElementsAnnotatedWith(Router.class));

        for (TypeElement element : typeElements
        ) {
            String[] value = element.getAnnotation(Router.class).value();
            className.append(value[0]);
            for (String page : value
            ) {
                method.addCode("case $S :\n", page);
            }
            method.addCode("\treturn $L.class;\n ", element);
        }

        method.addCode("}\n").addCode("return null;\n");
        clazz.addMethod(method.build());

        //生成java文件

        try {
            JavaFile javaFile = JavaFile.builder("com.mmc.lamandys.liba_datapick", clazz.build()).build();
            javaFile.writeTo(mFiler);

            log("--------------------------------------->complete Processor");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void log(String content) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, content);
    }
}
