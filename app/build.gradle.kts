plugins {
    id("app-plugin")
}
//configurations {
//    create(" profileImplementation")
//}
//
//android {
//    compileSdk = Version.compileSdkVersion
//
//    defaultConfig {
//        applicationId = "com.gh"
//        minSdk = 21
//        targetSdk = 31
//        versionCode = 1
//        versionName = "1.0"
//    }
//
////    signingConfigs {
////        create("release") {
////            keyAlias = "xzdz"
////            keyPassword = "xxxxxxxx"
////            storeFile = File("xxxxx")
////            storePassword = "xxxxxxx"
////        }
////
////        getByName("debug") {
////            keyAlias = "xzdz"
////            keyPassword = "xxxxxxxx"
////            storeFile = File("xxxxx")
////            storePassword = "xxxxxxx"
////        }
////    }
//
//    buildTypes {
//        getByName("release") {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
//            )
//        }
//
//        getByName("debug") {
//            isMinifyEnabled = false
//            isShrinkResources = false
//            isDebuggable = true
//            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
//        }
//    }
//
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
//    buildFeatures {
//        viewBinding = true
//    }
//
////    androidComponents {
////        onVariants { variant ->
////            if (variant.buildType != "debug") {
////                variant.getPackageApplicationProvider().get().outputDirectory =
////                    new File (project.rootDir.absolutePath + "/apk")
////            }
////            // Assigns a different version code for each output APK
////            // other than the universal APK.
////            variant.outputs.forEach { output ->
////                val name = output.filters.find { it.filterType == ABI }?.identifier
////
////                // Stores the value of abiCodes that is associated with the ABI for this variant.
////                val baseAbiCode = abiCodes[name]
////                // Because abiCodes.get() returns null for ABIs that are not mapped by ext.abiCodes,
////                // the following code does not override the version code for universal APKs.
////                // However, because we want universal APKs to have the lowest version code,
////                // this outcome is desirable.
////                if (baseAbiCode != null) {
////                    // Assigns the new version code to output.versionCode, which changes the version code
////                    // for only the output APK, not for the variant itself.
////                    output.versionCode.set(baseAbiCode * 1000 + (output.versionCode.get() ?: 0))
////                }
////            }
////        }
////    }
//
//    val abiCodes = mapOf("armeabi-v7a" to 32, "arm64-v8a" to 64)
//    applicationVariants.all {
//        val name = buildType.name
//        val flavor = flavorName
//        val version = versionName
//        packageApplicationProvider.get().outputDirectory.dir(project.rootDir.absolutePath + "/apk")
//        outputs.all {
//            if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
//                if (name != "debug") {
//                    val filter =
//                        getFilter(com.android.build.api.variant.FilterConfiguration.FilterType.ABI.name)
//                    outputFileName =
//                        filter + "/" + flavor + name + "_" + abiCodes[filter] + "_" + version + "_" + Deploy.releaseTime() + ".apk"
//                }
//            }
//
//        }
//    }
//
//    // 多渠道配置
////    flavorDimensions("code")
////    productFlavors {
////        create("google")
////        create("baidu")
////        create("xiaomi")
////    }
////
////    productFlavors.all {
////        manifestPlaceholders["CHANNEL_VALUE"] = name
////    }
//
//}