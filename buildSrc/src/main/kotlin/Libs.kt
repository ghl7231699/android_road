//import com.ghl.plugin.RouterPlugin
//import com.ghl.plugin.ModuleServicePlugin
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project

object Libs {
    val app_implementation = mapOf(
        "annotation" to "androidx.annotation:annotation:1.0.2",
        "lifecycle-extensions" to "androidx.lifecycle:lifecycle-extensions:2.0.0",
        "constraintlayout" to "androidx.constraintlayout:constraintlayout:1.1.3",
        "appcompat" to "androidx.appcompat:appcompat:1.1.0",
        "legacy-support" to "androidx.legacy:legacy-support-v4:1.0.0",
        "recyclerview" to "androidx.recyclerview:recyclerview:1.1.0",
        "cardview" to "androidx.cardview:cardview:1.0.0",
        "gson" to "com.google.code.gson:gson:2.8.5",
        "eventbus" to "org.greenrobot:eventbus:3.1.1",
        "viewpager2" to "androidx.viewpager2:viewpager2:1.0.0",
        "kotlin" to "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Version.kotlin_version}",
        "navigation-fragment" to "androidx.navigation:navigation-fragment:2.2.2",
        "navigation-ui" to "androidx.navigation:navigation-ui:2.2.2",
        "navigation-fragment-ktx" to "androidx.navigation:navigation-fragment-ktx:2.2.2",
        "navigation-ui-ktx" to "androidx.navigation:navigation-ui-ktx:2.2.2",
        "material" to "com.google.android.material:material:1.1.0",
        "activity" to "androidx.activity:activity:1.2.0-alpha05",
        "kotlinx-coroutines-core" to "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2"
    )


    val supportDeps = mapOf(
        "design" to "com.google.android.material:material:1.1.0",
        "multiDex" to "androidx.multidex:multidex:2.0.1",
        "appcompat" to "androidx.appcompat:appcompat:1.1.0",
        "annotation" to "androidx.annotation:annotation:1.1.0",
        "lifecycle" to "androidx.lifecycle:lifecycle-extensions:2.2.0",
        "cardView" to "androidx.cardview:cardview:1.0.0",
        "recyclerView" to "androidx.recyclerview:recyclerview:1.1.0",
        "constraintLayout" to "androidx.constraintlayout:constraintlayout:1.1.3",
        "coordinatorlayout" to "androidx.coordinatorlayout:coordinatorlayout:1.1.0",
        "flexbox" to "com.google.android:flexbox:2.0.1",
    )

    val okHttp = mapOf(
        "okhttp" to "com.squareup.okhttp3:okhttp:${Version.version_okHttp}",
        "logger" to "com.squareup.okhttp3:logging-interceptor:${Version.version_okHttp}"
    )

    val retrofit = mapOf(
        "retrofit" to "com.squareup.retrofit2:retrofit:${Version.version_retrofit}",
        "rxAdapter" to "com.squareup.retrofit2:adapter-rxjava2:${Version.version_retrofit}",
        "gsonConverter" to "com.squareup.retrofit2:converter-gson:${Version.version_retrofit}",
    )

    val rxJava = mapOf(
        "rxAndroid" to "io.reactivex.rxjava2:rxandroid:${Version.version_rxandroid}"
    )

    /**
     * lib 统一依赖的方法  TODO
     */
    fun androidLibraryBiz(
        projectLibrary: Project
    ) {
        projectLibrary.run {
            apply {
                plugin("com.android.application")
                plugin("org.jetbrains.kotlin.android")
                plugin("org.jetbrains.kotlin.kapt")
//                plugin(RouterPlugin::class.java)
//                plugin(ModuleServicePlugin::class.java)
                from("../buildConfig/upload.gradle")
            }

            app_implementation.forEach { (_, v) ->
                dependencies.add("implementation", v)
            }

            okHttp.forEach { (_, v) ->
                dependencies.add("implementation", v)
            }

            retrofit.forEach { (_, v) ->
                dependencies.add("implementation", v)
            }

            rxJava.forEach { (_, v) ->
                dependencies.add("implementation", v)
            }

            dependencies.add(
                "implementation",
                fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar")))
            )

            extensions.getByName("android").run {
//                if (this is ApplicationExtension) {
                if (this is LibraryExtension) {

                    compileSdk = Version.compileSdkVersion

                    defaultConfig.run {
                        minSdk = Version.minSdkVersion
                        targetSdk = Version.targetSdkVersion
                    }

                    compileOptions.run {
                        sourceCompatibility = JavaVersion.VERSION_1_8
                        targetCompatibility = JavaVersion.VERSION_1_8
                    }

                    buildFeatures.run {
                        viewBinding = true
                    }
                }
            }

            if (name != "lib_common") {
                dependencies.add("implementation", project(":lib_common"))
            }
        }

    }

}