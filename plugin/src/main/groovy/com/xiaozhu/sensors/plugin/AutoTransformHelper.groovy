package com.ghl.sensors.plugin

import com.android.build.gradle.AppExtension

class AutoTransformHelper {

    AutoExtension extension
    AppExtension android
    RN_STATE rnState = RN_STATE.NOT_FOUND
    String rnVersion = ""
    AutoSDKHookConfig sensorsAnalyticsHookConfig
    boolean disableSensorsAnalyticsMultiThread
    boolean disableSensorsAnalyticsIncremental
    boolean isHookOnMethodEnter
    HashSet<String> exclude = new HashSet<>(['com.sensorsdata.analytics.android.sdk', 'android.support', 'androidx', 'com.qiyukf', 'android.arch', 'com.google.android', "com.tencent.smtt"])
    HashSet<String> include = new HashSet<>(['butterknife.internal.DebouncingOnClickListener',
                                             'com.jakewharton.rxbinding.view.ViewClickOnSubscribe',
                                             'com.facebook.react.uimanager.NativeViewHierarchyManager'])
    /** 将一些特例需要排除在外 */
    public static final HashSet<String> special = ['android.support.design.widget.TabLayout$ViewPagerOnTabSelectedListener',
                                                   'com.google.android.material.tabs.TabLayout$ViewPagerOnTabSelectedListener',
                                                   'android.support.v7.app.ActionBarDrawerToggle',
                                                   'androidx.appcompat.app.ActionBarDrawerToggle',
                                                   'androidx.appcompat.widget.ActionMenuPresenter$OverflowMenuButton',
                                                   'android.widget.ActionMenuPresenter$OverflowMenuButton',
                                                   'android.support.v7.widget.ActionMenuPresenter$OverflowMenuButton']
    URLClassLoader urlClassLoader

    AutoTransformHelper(AutoExtension extension, AppExtension android) {
        this.extension = extension
        this.android = android
    }

    File androidJar() throws FileNotFoundException {
        File jar = new File(getSdkJarDir(), "android.jar")
        if (!jar.exists()) {
            throw new FileNotFoundException("Android jar not found!")
        }
        return jar
    }

    private String getSdkJarDir() {
        String compileSdkVersion = android.getCompileSdkVersion()
        return String.join(File.separator, android.getSdkDirectory().getAbsolutePath(), "platforms", compileSdkVersion)
    }

    void onTransform() {
        println("sensorsAnalytics {\n" + extension + "\n}")
        ArrayList<String> excludePackages = extension.exclude
        if (excludePackages != null) {
            exclude.addAll(excludePackages)
        }
        ArrayList<String> includePackages = extension.include
        if (includePackages != null) {
            include.addAll(includePackages)
        }
        createSensorsAnalyticsHookConfig()
    }

    private void createSensorsAnalyticsHookConfig() {
        sensorsAnalyticsHookConfig = new AutoSDKHookConfig()
        List<MetaProperty> metaProperties = AutoSDKExtension.getMetaClass().properties
        for (it in metaProperties) {
            if (it.name == 'class') {
                continue
            }
            if (extension.sdk."${it.name}") {
                sensorsAnalyticsHookConfig."${it.name}"(it.name)
            }
        }
    }

    AutoMatchUtils analytics(String className) {
        AutoMatchUtils classNameAnalytics = new AutoMatchUtils(className)
        if (classNameAnalytics.isSDKFile()) {
            def cellHashMap = sensorsAnalyticsHookConfig.methodCells
            cellHashMap.each {
                key, value ->
                    def methodCellList = value.get(className.replace('.', '/'))
                    if (methodCellList != null) {
                        classNameAnalytics.methodCells.addAll(methodCellList)
                    }
            }
            if (classNameAnalytics.methodCells.size() > 0 || classNameAnalytics.isSensorsDataAPI) {
                classNameAnalytics.isShouldModify = true
            }
        } else if (!classNameAnalytics.isAndroidGenerated()) {
            for (pkgName in special) {
                if (className.startsWith(pkgName)) {
                    classNameAnalytics.isShouldModify = true
                    return classNameAnalytics
                }
            }
            if (extension.useInclude) {
                for (pkgName in include) {
                    if (className.startsWith(pkgName)) {
                        classNameAnalytics.isShouldModify = true
                        break
                    }
                }
            } else {
                classNameAnalytics.isShouldModify = true
                if (!classNameAnalytics.isLeanback()) {
                    for (pkgName in exclude) {
                        if (className.startsWith(pkgName)) {
                            classNameAnalytics.isShouldModify = false
                            break
                        }
                    }
                }
            }
        }
        return classNameAnalytics
    }

    enum RN_STATE{
        NOT_FOUND, NO_VERSION, HAS_VERSION
    }
}

