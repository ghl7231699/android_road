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
            id = "app-plugin"
            implementationClass = "com.ghl.plugin.AppPlugin"
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
        register("routerPlugin") {
            id = "router-plugin"
            implementationClass = "com.ghl.plugin.RoutersPlugin"
        }
        register("imcPlugin") {
            id = "imc-plugin"
            implementationClass = "com.ghl.plugin.ImcPlugin"
        }
    }
}