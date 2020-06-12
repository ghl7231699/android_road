package com.ghl.router.lib;

import java.util.ArrayList;

public class RouterUtils {

    /**
     * 所有链表保存的地方
     */
    static ArrayList<RouterClassProvider> mListProvider = new ArrayList<>();

    public static Class<?> getPageClass(String page) {
        for (RouterClassProvider provider : mListProvider) {
            Class<?> target = provider.getPageClass(page);
            if (target != null) {
                return target;
            }
        }
        return null;
    }
}
