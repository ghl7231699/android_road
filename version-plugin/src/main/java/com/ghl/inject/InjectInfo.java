package com.ghl.inject;

import java.io.File;
import java.util.ArrayList;

/**
 * Time：2023/3/22
 * description:
 **/
public class InjectInfo {
    /**
     * 注入的目标文件
     * eg."com/xiaozhu/xzdz/router/lib/Router"
     */
    public String targetClass;

    /**
     * {@link #targetClass} 所在Jar包
     */
    public File targetFile;

    /**
     * 修改的方法
     * <clinit> 是静态代码块
     */
    public String initMethodName;

    /**
     * 被实现的接口
     * eg
     */
    public String interfaceClass;

    /**
     * 所有实现{@link #interfaceClass}接口的类
     */
    public ArrayList<String> allInter = new ArrayList<>();

    /**
     * 实现的父类
     */
    public String superClass;

    /**
     * 被调用的方法
     * eg. bindService
     */
    public String invokingMethodName;
}
