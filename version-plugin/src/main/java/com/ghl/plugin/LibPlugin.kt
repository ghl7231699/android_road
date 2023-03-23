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
class LibPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins.run {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.kapt")
                apply("moduleService-plugin")
                apply("router-plugin")
            }
            extensions.configure<LibraryExtension> {
                namespace = "com.ghl.${target.name}"
                compileSdk = Version.compileSdk

                defaultConfig.run {
                    minSdk = Version.minSdk
                    targetSdk = Version.targetSdk
                }

                compileOptions.run {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }

                buildFeatures.run {
                    viewBinding = true
                }
            }
            dependencies {
                supportDeps()
                appUi()
                okHttp()
                retrofit()
                coil()
                "implementation"(project(":imc"))
                "implementation"(project(":router"))
                "kapt"(project(":router:router_apt"))
                "implementation"(project(":net"))
                if (name != "lib_common") {
                    "implementation"(project(":lib_common"))
                }
            }
        }
    }
}