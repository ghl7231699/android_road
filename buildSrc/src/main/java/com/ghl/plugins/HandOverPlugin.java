package com.ghl.plugins;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.tasks.ProcessApplicationManifest;

import org.codehaus.groovy.runtime.ResourceGroovyMethods;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.provider.Property;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import groovy.util.Node;
import groovy.util.XmlParser;
import groovy.xml.XmlUtil;

class HandOverPlugin implements Plugin<Project> {

    private String mainActivity;
    private boolean singleCompile;

    @Override
    public void apply(Project target) {
        System.out.println("嗨，你好，我是自动切换插件哦");
        System.out.println(target.getName());
        System.out.println(target.getPath());

        Properties property = new Properties();
        File gradle = target.file("gradle.properties");
        if (gradle.exists()) {
            try {
                property.load(ResourceGroovyMethods.newDataInputStream(gradle));
                mainActivity = property.getProperty("main_activity");
                singleCompile = Boolean.parseBoolean(property.getProperty("single_compile"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!singleCompile) {
            return;
        }

//        execGradleTask(target);
        // 如果是AppPlugin，则修改注册清单文件
        handleManifestXml(target);
    }

    /**
     * 修改build.gradle 属性
     *
     * @param target project
     */
    private void execGradleTask(Project target) {
        target.exec(execSpec -> {
            System.out.println("---------------sh start-----------------");
            String path = target.getRootDir().getPath();
            System.out.println("--------------" + path);
            execSpec.commandLine("sh", path + File.separator + "build.sh", target.getName());
            System.out.println("---------------sh end-------------------");
        });
    }

    /**
     * 修改manifest文件，增加程序入口
     *
     * @param target project
     */
    private void handleManifestXml(Project target) {
        target.afterEvaluate(project -> {
            System.out.println("---------获取变体---------");
            AppExtension extension = (AppExtension) project.getExtensions().getByName("android");
            extension.getApplicationVariants().all(variant -> {
                String name = variant.getBuildType().getName();
                System.out.println("---------build type " + name +
                        "---------");
                variant.getOutputs().all(output -> {
                    //获取 processManifestTask（对Manifest进行处理的task）
                    ProcessApplicationManifest manifestProcessorTask = (ProcessApplicationManifest) output.getProcessManifestProvider().get();
                    Property<File> mainManifest = manifestProcessorTask.getMainManifest();
                    File file = mainManifest.get();
                    System.out.println("ProcessApplicationManifest:" + mainManifest.get().getPath());
                    manifestProcessorTask.doFirst(task -> {
                        System.out.println("do First start ");
                        if (mainManifest.get().canRead()) {
                            try {
                                String manifestContent = ResourceGroovyMethods.getText(file);
                                if (manifestContent.contains("android.intent.action.MAIN")) {
                                    if (name.toLowerCase().contains("release")) {
                                        String replace = manifestContent.replace(
                                                "      <intent-filter>\n" +
                                                        "          <category android:name=\"android.intent.category.LAUNCHER\" />\n" +
                                                        "          <action android:name=\"android.intent.action.MAIN\" />\n" +
                                                        "      </intent-filter>\n" +
                                                        "        </activity>\n", "");
                                        ResourceGroovyMethods.write(file, manifestContent);
                                    }
                                    return;
                                }

                                Node node = new XmlParser(false, false).parseText(manifestContent);
                                List<Node> list = node.depthFirst();
                                if (list != null && !list.isEmpty()) {
                                    list.forEach(app -> {
                                        if (String.valueOf(app.name()).equals("application")) {// 解析到activity节点
                                            List<Node> children = app.children();
                                            System.out.println(children.size());
                                            for (Node child : children) {
                                                System.out.println("childName:  " + "\t" + child.toString());
                                                if (child.toString().contains(mainActivity)) {// 找到debug的入口 todo  assembleProfile
                                                    if (name.toLowerCase().contains("debug")) {
                                                        Node filter = new Node(child, "intent-filter");
                                                        new Node(filter, "action android:name=\"android.intent.action.MAIN\" ");
                                                        new Node(filter, "category android:name=\"android.intent.category.LAUNCHER\" ");
                                                    }
                                                    break;
                                                }
                                            }
                                        }
                                    });
                                }

                                String serialize = XmlUtil.serialize(node);
                                System.out.println("serialize:" + serialize);
                                ResourceGroovyMethods.write(file, serialize);
//                                String old = String.format("<activity android:name=\"%s\" />", mainActivity);
//                                String latest = String.format("<activity android:name=\"%s\" >     \n" +
//                                        "      <intent-filter>\n" +
//                                        "          <category android:name=\"android.intent.category.LAUNCHER\" />\n" +
//                                        "          <action android:name=\"android.intent.action.MAIN\" />\n" +
//                                        "      </intent-filter>\n" +
//                                        "        </activity>\n", mainActivity);
//
//                                manifestContent = manifestContent.replace(old, latest);
//                                System.out.println("manifestContent:" + manifestContent);
//                                ResourceGroovyMethods.write(file, serialize);
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("失败了");
                            } catch (SAXException | ParserConfigurationException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                });
            });
        });
    }
}
