plugins{
    `kotlin-dsl`
}

gradlePlugin{
    plugins {
        register("versionPlugin"){
            id="version-plugin"
            implementationClass="com.ghl.plugin.VersionPlugin"
        }
    }
}