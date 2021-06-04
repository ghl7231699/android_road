//package com.ghl.plugin
//
//import org.gradle.api.Plugin
//import org.gradle.api.Project
//import org.gradle.api.tasks.Exec
//import org.gradle.internal.impldep.org.codehaus.plexus.util.cli.Commandline
//import java.util.*
//
//class HandOverPlugin : Plugin<Project> {
//    override fun apply(target: Project) {
//        println("嗨，你好，我是自动切换插件哦")
//        println(target.name)
//        println(target.path)
//
//        val argus = mutableMapOf<String, Class<*>>().apply {
//            put("type", Exec::class.java)
//        }
//
//
//
////        target.tasks.getByName("preBuild").mustRunAfter()
//
//        target.task( "executeBefore") {
//            val arguments = arrayOf("sh", "build.sh")
//
//            val apply = Commandline().apply {
//                addArguments(arguments)
//            }
//            apply.execute()
//        }
//
//
//
//
//        // 获取当前
//
//        val properties = Properties()
//
//        val findProject = target.findProperty("settings.gradle")
//
//        val buildFile = target.buildFile
//
//        println(buildFile.path)
//
//        if (findProject == null) {
//            println("not  found file ")
//        }
//
//        findProject?.let {
////            println(it.allprojects)
//        }
//    }
//
//}