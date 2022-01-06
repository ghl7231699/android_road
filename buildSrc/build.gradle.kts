plugins {
    id("groovy")
    kotlin("jvm") version "1.4.31"
    `kotlin-dsl`
}

dependencies {
    implementation("com.android.tools.build:gradle:7.0.4")
    implementation("commons-io:commons-io:2.4")
    implementation("commons-codec:commons-codec:1.15")
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}