plugins {
    `kotlin-dsl`
    id("groovy")
    kotlin("jvm") version "1.4.31"
}

dependencies {
    implementation("com.android.tools.build:gradle:7.1.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
    implementation("commons-io:commons-io:2.4")
    implementation("commons-codec:commons-codec:1.15")
}

gradlePlugin {
    plugins {
        register("appPlugin") {
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
        register("moduleRouterPlugin") {
            id = "module-router-plugin"
            implementationClass = "com.ghl.plugin.ModuleRouterPlugin"
        }
        register("imcPlugin") {
            id = "imc-plugin"
            implementationClass = "com.ghl.plugin.ImcPlugin"
        }
        register("moduleServicePlugin") {
            id = "moduleService-plugin"
            implementationClass = "com.ghl.plugin.ModuleServicePlugin"
        }
        register("routerPlugin") {
            id = "router-plugin"
            implementationClass = "com.ghl.plugin.RouterPlugin"
        }
    }
}