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
class NetPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins.run {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.kapt")
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
                supportDeps("api")
                retrofit("api")
                okHttp("api")
                coroutines("api")
                add("implementation", Libs.gson)
            }
        }
    }
}