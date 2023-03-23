pluginManagement {
    includeBuild("version-plugin")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

include(
    ":app",
    ":biz_main",
    ":biz_login",
    ":biz_user",
    ":lib_common",
//    ":opt_track",
    ":imc",
//    ":androidservice",
    ":router",
    ":router:router_annotation",
    ":router:router_apt",
    ":net",
    ":login"
)
rootProject.name = "android-road"

//include(":kotlin")
//include ":flutter_mall"
//include ":design_mode"
//include ":hot_fix"


//setBinding(new Binding([gradle: this]))
//evaluate(new File(
//        settingsDir,
//        "/flutter_mall/.android/include_flutter.groovy"
//))