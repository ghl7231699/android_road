package com.ghl.router.lib;

import android.content.Context;

public class Router {
    //方便代码插入
    static {

    }

    public static RouterBuilder with(Context context) {
        return new RouterBuilder(context);
    }

    private static void bindClassProvider(RouterClassProvider provider) {
        RouterUtils.mListProvider.add(provider);
    }

    public static Class<?> getTargetClass(String name) {
        Class<?> clazz = null;
        for (RouterClassProvider provider : RouterUtils.mListProvider) {
            Class<?> c = provider.getPageClass(name);
            if (c != null) {
                clazz = c;
                break;
            }
        }
        return clazz;
    }
}
