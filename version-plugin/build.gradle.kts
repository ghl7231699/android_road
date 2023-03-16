plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("com.android.tools.build:gradle:7.1.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
}

gradlePlugin {
    plugins {
        register("versionPlugin") {
            id = "version-plugin"
            implementationClass = "com.ghl.plugin.VersionPlugin"
        }
        register("libPlugin") {
            id = "library-plugin"
            implementationClass = "com.ghl.plugin.LibPlugin"
        }
        register("netPlugin") {
            id = "net-plugin"
            implementationClass = "com.ghl.plugin.NetPlugin"
        }
        register("moduleServicePlugin") {
            id = "moduleService-plugin"
            implementationClass = "com.ghl.plugin.ModuleServicePlugin"
        }
    }
}