package com.ghl.lib_dirty.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private Retrofit mClient;

    private RetrofitManager() {
        mClient = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitManager getInstance() {
        return RetrofitHolder.instance;
    }

    private static class RetrofitHolder {
        private static RetrofitManager instance = new RetrofitManager();
    }

    public <T> T createApi(Class<? extends T> clazz) {
        return mClient.create(clazz);
    }
}
