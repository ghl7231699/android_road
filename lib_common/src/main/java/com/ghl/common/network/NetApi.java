package com.ghl.common.network;

import android.util.Log;
import android.widget.Toast;

import com.ghl.common.AutoApplication;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NetApi {
    private OkHttpClient mClient;

    public NetApi() {
    }

    public OkHttpClient client() {
        synchronized (this) {
            if (mClient == null) {
                mClient = new OkHttpClient();
            }
        }
        return mClient;
    }

    public void get(String url) throws IOException {
        String name = Thread.currentThread().getName();
        if ("main".equals(name)) {
            Toast.makeText(AutoApplication.mContext, "我是主线程，你想造反吗", Toast.LENGTH_SHORT).show();
            return;
        }
        Request request = new Request.Builder().url(url).build();
        //执行同步请求
        Call call = client().newCall(request);

        Response response = call.execute();

        ResponseBody body = response.body();
        Log.i("ghl", body.toString() + name);

    }

    public void post(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        //执行同步请求
        Call call = client().newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("ghl", call.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                Log.i("ghl", body.toString() + Thread.currentThread().getName());
            }
        });
    }
}
