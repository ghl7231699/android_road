package com.example.androidservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.mmc.sample.IMyAidlInterface;

import androidx.annotation.Nullable;

public class ServerService extends Service {
    private static final String TAG = "ghl";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind\t" + intent);
        return new MyBinder();
    }

    private static class MyBinder extends IMyAidlInterface.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int sendData(String data) throws RemoteException {
            Log.i(TAG, "sendData\t" + data);
            return 10;
        }

        @Override
        public String getData() throws RemoteException {
            Log.i(TAG, "sendData\t");
            return "Hello Binder AIDL";
        }
    }

}
