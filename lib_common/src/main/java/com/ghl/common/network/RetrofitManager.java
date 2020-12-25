package com.ghl.common.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static com.ghl.common.net.BaseConstantsKt.BASE_URL;

public class RetrofitManager {
    private Retrofit mClient;
    private OkHttpClient okHttpClient;

    private RetrofitManager() {

    }

    public void init(OkHttpClient.Builder builder) {
        okHttpClient = builder.build();
        mClient = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(RoadGsonConverterFactory.create())
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
