package com.ghl.plugin


object Version {
    const val compileSdk = 32
    const val minSdk = 21
    const val targetSdk = 32
    const val versionCode = 1
    const val versionName = "1.0"


    // ----------Android 官方-------------
    const val version_core = "1.7.0"
    const val version_appcompat = "1.4.1"
    const val version_material = "1.5.0"

    // test库
    const val version_junit = "4.13.2"
    const val version_test_ext = "1.1.3"
    const val version_espresso = "3.4.0"

    // Retrofit库

    const val version_retrofit = "2.9.0"
    const val version_retrofit_mock = "2.7.2"
    const val version_converter_gson = "2.7.2"
    const val version_converter_scalars = "2.7.2"
    const val version_adapter_rxjava = "2.7.2"

    // Gson库
    const val version_gson = "2.8.9"

    const val version_okHttp = "4.9.2"
    const val version_rxandroid = "2.1.1"

}


object Libs {

    val android_core = "androidx.core:core-ktx:${Version.version_core}"
    val android_appcompat = "androidx.appcompat:appcompat:${Version.version_appcompat}"
    val android_material = "com.google.android.material:material:${Version.version_material}"

    // Gson库
    const val gson = "com.google.code.gson:gson:${Version.version_gson}"

    val app_implementation = mapOf(
        "annotation" to "androidx.annotation:annotation:1.1.0",
        "lifecycle-extensions" to "androidx.lifecycle:lifecycle-extensions:2.0.0",
        "constraintlayout" to "androidx.constraintlayout:constraintlayout:1.1.3",
        "coordinatorlayout" to "androidx.coordinatorlayout:coordinatorlayout:1.1.0",
        "recyclerView" to "androidx.recyclerview:recyclerview:1.1.0",
    )

    val test = mapOf(
        "testImplementation" to "junit:junit:${Version.version_junit}",
        "androidTestImplementation" to "androidx.test.ext:junit:${Version.version_test_ext}",
        "androidTestImplementation" to "androidx.test.espresso:espresso-core:${Version.version_espresso}"
    )

    // 协程库
    val coroutines = mapOf(


        "kotlin-stdlib" to "org.jetbrains.kotlin:kotlin-stdlib:1.5.0",
        "coroutines-core" to "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0",
        "coroutines-android" to "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2"
    )

    val supportDeps = mapOf(
        "multiDex" to "androidx.multidex:multidex:2.0.1",
        "lifecycle" to "androidx.lifecycle:lifecycle-extensions:2.2.0",
        "viewmodel" to "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0",
    )

    val okHttp = mapOf(
        "okhttp" to "com.squareup.okhttp3:okhttp:${Version.version_okHttp}",
        "logger" to "com.squareup.okhttp3:logging-interceptor:${Version.version_okHttp}"
    )

    val retrofit = mapOf(
        "retrofit" to "com.squareup.retrofit2:retrofit:${Version.version_retrofit}",
        "rxAdapter" to "com.squareup.retrofit2:adapter-rxjava2:${Version.version_adapter_rxjava}",
        "gsonConverter" to "com.squareup.retrofit2:converter-gson:${Version.version_converter_gson}",
        "mock" to "com.squareup.retrofit2:retrofit-mock:${Version.version_converter_gson}",
    )


    val rxJava = mapOf(
        "rxAndroid" to "io.reactivex.rxjava2:rxandroid:${Version.version_rxandroid}"
    )

}