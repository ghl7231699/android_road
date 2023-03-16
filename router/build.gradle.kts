plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = Version.compileSdkVersion
    defaultConfig {
        minSdk = Version.minSdkVersion
        targetSdk = Version.targetSdkVersion
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

}

dependencies {
    implementation(
        fileTree(
            mapOf(
                "dir" to "libs", "include" to listOf("*.jar", "aar")
            )
        )
    )
    Libs.supportDeps["annotation"]?.let { implementation(it) }
    Libs.supportDeps["appcompat"]?.let { implementation(it) }
    api(project(":router:router_annotation"))
}