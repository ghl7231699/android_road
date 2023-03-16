plugins {
    id("groovy")
    kotlin("jvm") version "1.4.31"
    `kotlin-dsl`
}

tasks.compileGroovy {
    val compileGroovy = tasks.compileKotlin.get()
    dependsOn(compileGroovy)
    classpath += files(compileGroovy.destinationDir)
    tasks.classes.get().dependsOn(this)
}

dependencies {
    implementation("com.android.tools.build:gradle:7.0.4")
    implementation("commons-io:commons-io:2.4")
    implementation("commons-codec:commons-codec:1.15")
}

gradlePlugin {
    plugins {
        register("moduleServicePlugin") {
            id = "moduleService-plugin"
            implementationClass = "com.ghl.plugin.ModuleServicePlugin"
        }
    }
}




repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}