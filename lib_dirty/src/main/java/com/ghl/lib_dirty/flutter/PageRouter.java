package com.ghl.lib_dirty.flutter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ghl.router.lib.Router;
import com.idlefish.flutterboost.containers.BoostFlutterActivity;

import java.util.HashMap;
import java.util.Map;

public class PageRouter {

    public final static Map<String, String> pageName = new HashMap<String, String>() {{
        put("first", "first");
        put("second", "second");
        put("tab", "tab");
        put("sample://flutterPage", "flutterPage");
        put("/", "/");
    }};

    public static final String NATIVE_PAGE_URL = "sample://nativePage";
    public static final String FLUTTER_PAGE_URL = "sample://flutterPage";
    public static final String FLUTTER_FRAGMENT_PAGE_URL = "sample://flutterFragmentPage";

    public static boolean openPageByUrl(Context context, String url, Map<String, ?> params) {
        return openPageByUrl(context, url, params, 0);
    }

    public static boolean openPageByUrl(Context context, String url, Map<String, ?> params, int requestCode) {

        String path = url.split("\\?")[0];

        Log.i("openPageByUrl", path);

        try {
            if (pageName.containsKey(path)) {
                String value = pageName.get(path);
                if (value != null) {
                    Intent intent = BoostFlutterActivity.withNewEngine().url(value).params(params)
                            .backgroundMode(BoostFlutterActivity.BackgroundMode.opaque).build(context);
                    context.startActivity(intent);
                }

            } else if (url.startsWith(FLUTTER_FRAGMENT_PAGE_URL)) {
                Router.with(context).target("FlutterFragmentPageActivity").start();
                return true;
            } else if (url.startsWith(NATIVE_PAGE_URL)) {
                Router.with(context).target("NativePageActivity").start();
                return true;
            } else {
                return false;
            }
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
        return false;
    }
}
