package com.ghl.router_apt;

import com.ghl.router_annotation.Ignore;
import com.ghl.router_annotation.Route;
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
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

@SupportedSourceVersion(SourceVersion.RELEASE_8)//java版本支持
@SupportedAnnotationTypes({//标注注解处理器支持的注解类型
        "com.xiaozhu.xzdz.router.annotation.Ignore"
})
public class AnnotationProcessor extends AbstractProcessor {

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
        Map<String, String> op = processingEnv.getOptions();
        projectName = op.get("project_name");
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
        types.add(Route.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//        if (projectName == null || projectName.length() == 0) {
//            return false;
//        } else {
//            log(projectName);
//        }
        log("---------------------->Start Processor");
        final String className = "IgnoreFragment";
        //类生成
        TypeSpec.Builder tb = TypeSpec.classBuilder(className).addModifiers(PUBLIC, FINAL);

        //方法生成
        MethodSpec.Builder getClass = MethodSpec.methodBuilder("ignoreFragment")
                .returns(void.class)
                .addModifiers(PUBLIC, STATIC);

        for (TypeElement typeElement : ElementFilter.typesIn(roundEnvironment.getElementsAnnotatedWith(Ignore.class))) {
            String pageNames = typeElement.getQualifiedName().toString();
            getClass.addCode("\tSystem.out.println($S);", pageNames);
        }
        tb.addMethod(getClass.build());
        try {
            JavaFile javaFile = JavaFile.builder("com.mmc.lamandys.liba_datapick", tb.build()).build();// 生成源代码
            javaFile.writeTo(mFiler);// 在 app module/build/generated/source/apt 生成一份源代码
            log("---------------------->end Processor");
        } catch (IOException e) {
//            e.printStackTrace();
//            log( "warning");
            log("---------------------->Error Processor");
        }

        return true;
    }

    private void log(String content) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, content);
    }
}
