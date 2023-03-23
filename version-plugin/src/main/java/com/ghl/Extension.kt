package com.ghl.plugin

import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

/**
 * Time：2023/3/14
 * description:
 **/
fun DependencyHandlerScope.coroutines(tag: String = "implementation") {
    Libs.coroutines.forEach { (_, value) ->
        tag(value)
    }
}

fun DependencyHandlerScope.appUi() {
    Libs.app_implementation.forEach { (_, value) ->
        "implementation"(value)
    }

    Libs.rxJava.forEach { (_, value) ->
        "implementation"(value)
    }
    "implementation"(Libs.android_core)
    "implementation"(Libs.android_appcompat)
    "implementation"(Libs.android_material)
}

fun DependencyHandlerScope.supportDeps(tag: String = "implementation") {
    Libs.supportDeps.forEach { (_, value) ->
        tag(value)
    }
}

fun DependencyHandlerScope.retrofit(tag: String = "implementation") {
    Libs.retrofit.forEach { (_, value) ->
        tag(value)
    }
}

fun DependencyHandlerScope.okHttp(tag: String = "implementation") {
    Libs.okHttp.forEach { (_, value) ->
        tag(value)
    }
}

fun DependencyHandlerScope.test() {
    Libs.test.forEach { (_, value) ->
        "implementation"(value)
    }
}

fun DependencyHandlerScope.imc() {
    Libs.supportDeps["annotation"]?.let { "implementation"(it) }
    Libs.supportDeps["appcompat"]?.let { "implementation"(it) }
    "api"(project(":router:router_annotation"))
}

fun DependencyHandlerScope.coil() {
    "implementation"(Libs.coil)
}







