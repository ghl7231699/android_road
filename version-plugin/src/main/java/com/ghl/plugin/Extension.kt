package com.ghl.plugin

import org.gradle.kotlin.dsl.DependencyHandlerScope

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
    "implementation"(Libs.android_core)
    "implementation"(Libs.android_appcompat)
    "implementation"(Libs.android_material)
    "implementation"(Libs.rxJava)
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







