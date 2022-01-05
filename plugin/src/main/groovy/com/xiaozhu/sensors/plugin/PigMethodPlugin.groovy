package com.ghl.sensors.plugin

import com.android.build.gradle.AppExtension
import InjectLogger
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.reflect.Instantiator
import org.gradle.invocation.DefaultGradle

class PigMethodPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        Instantiator ins = ((DefaultGradle) project.getGradle()).getServices().get(Instantiator)
        def args = [ins] as Object[]
        AutoExtension extension = project.extensions.create("sensorsAnalytics", AutoExtension, args)

        boolean disableSensorsAnalyticsPlugin = false
        boolean disableSensorsAnalyticsMultiThreadBuild = false
        boolean disableSensorsAnalyticsIncrementalBuild = false
        boolean isHookOnMethodEnter = false
        Properties properties = new Properties()
        if (project.rootProject.file('gradle.properties').exists()) {
            properties.load(project.rootProject.file('gradle.properties').newDataInputStream())
            disableSensorsAnalyticsPlugin = Boolean.parseBoolean(properties.getProperty("sensorsAnalytics.disablePlugin", "false")) ||
                    Boolean.parseBoolean(properties.getProperty("disableSensorsAnalyticsPlugin", "false"))
            disableSensorsAnalyticsMultiThreadBuild = Boolean.parseBoolean(properties.getProperty("sensorsAnalytics.disableMultiThreadBuild", "false"))
            disableSensorsAnalyticsIncrementalBuild = Boolean.parseBoolean(properties.getProperty("sensorsAnalytics.disableIncrementalBuild", "false"))
            isHookOnMethodEnter = Boolean.parseBoolean(properties.getProperty("sensorsAnalytics.isHookOnMethodEnter", "false"))
        }
        if (!disableSensorsAnalyticsPlugin) {
            AppExtension appExtension = project.extensions.findByType(AppExtension.class)
            AutoTransformHelper transformHelper = new AutoTransformHelper(extension, appExtension)
            transformHelper.disableSensorsAnalyticsIncremental = disableSensorsAnalyticsIncrementalBuild
            transformHelper.disableSensorsAnalyticsMultiThread = disableSensorsAnalyticsMultiThreadBuild
            transformHelper.isHookOnMethodEnter = isHookOnMethodEnter
            appExtension.registerTransform(new AutoTransform(transformHelper))
        } else {
            InjectLogger.error("------------您已关闭了神策插件--------------")
        }

    }
}