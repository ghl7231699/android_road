include(
    ":app",
    ":biz_main",
    ":biz_login",
    ":biz_track",
    ":biz_user",
    ":lib_common",
    ":opt_startup",
    ":opt_track",
    ":imc",
    ":androidservice",
    ":router",
    ":router:router_annotation",
    ":router:router_apt"
)
rootProject.name = "android-road"

//include ":kotlin"
//include ":flutter_mall"
//include ":design_mode"
//include ":hot_fix"


//setBinding(new Binding([gradle: this]))
//evaluate(new File(
//        settingsDir,
//        "/flutter_mall/.android/include_flutter.groovy"
//))