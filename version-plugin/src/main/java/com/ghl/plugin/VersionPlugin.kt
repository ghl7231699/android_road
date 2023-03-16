package com.ghl.plugin

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

class VersionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins.run {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.kapt")
            }
            extensions.configure<ApplicationExtension> {
                namespace = "com.ghl.demo"
                compileSdk = Version.compileSdk
                configurations.create("profileImplementation")
//                configurations {
//                    create(" profileImplementation")
//                }
                defaultConfig {
                    applicationId = "com.ghl.demo"
                    minSdk = Version.minSdk
                    targetSdk = Version.targetSdk
                    versionCode = Version.versionCode
                    versionName = Version.versionName

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                buildTypes {
                    getByName("debug") {
                        isMinifyEnabled = false
                        isShrinkResources = false
                        isDebuggable = true
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro",
                        )
                    }

                    getByName("release") {
                        isMinifyEnabled = true
                        isShrinkResources = true
                        isDebuggable = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }
//                buildFeatures {
//                    viewBinding = true
//                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }
                kotlinOptions {
                    jvmTarget = "1.8"
                }
            }

            dependencies {
                dependencies.add(
                    "implementation",
                    fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar")))
                )
//                appUi()
//                supportDeps()
//                retrofit()
//                okHttp()
//                coroutines()
//                test()
                add("implementation", Libs.gson)
                add(
                    "implementation",
                    "androidx.lifecycle:lifecycle-livedata-core-ktx:2.4.1"
                )
                add("implementation", "com.google.android.flexbox:flexbox:3.0.0")

                val bizModules = arrayOf(
                    ":biz_main",
                    ":biz_login",
                    ":biz_user",
                    ":opt_track",
                    ":lib_common",
                    ":opt_startup"
                )

                bizModules.forEach { biz ->
                    add("implementation", project(biz))
                }
            }
        }
    }
}

fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}