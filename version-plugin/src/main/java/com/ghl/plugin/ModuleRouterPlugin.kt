package com.ghl.plugin

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Time：2023/3/16
 * description:
 **/
class ModuleRouterPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins.run {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<LibraryExtension> {
                namespace = "com.ghl.router.lib"
                compileSdk = Version.compileSdk

                defaultConfig.run {
                    minSdk = Version.minSdk
                    targetSdk = Version.targetSdk
                }

                compileOptions.run {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }

            }
            dependencies {
//                Libs.supportDeps["annotation"]?.let { add("implementation", it) }
//                Libs.supportDeps["appcompat"]?.let { add("implementation", it) }
//                add("api", project(":router:router_annotation"))
                imc()
            }
        }
    }
}