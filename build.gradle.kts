apply(from = "./buildConfig/app_config.gradle")

//buildscript {
//    repositories {
//        mavenLocal()
//        mavenCentral()
//        google()
//    }
//    dependencies {
//        classpath("com.android.tools.build:gradle:${Version.gradle_version}")
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin_plugin_version}")
//    }
//}
//
//allprojects {
//    repositories {
//        google()
//        mavenCentral()
//        maven {
//            url = uri("../snapshotRepo")
//        }
//    }
//}


tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}