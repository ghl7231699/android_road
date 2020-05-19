package com.mmc.lamandys.liba_datapick.flutter;

import android.widget.Toast;

import com.mmc.lamandys.liba_datapick.AutoApplication;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class FlutterNetworkPlugin implements MethodChannel.MethodCallHandler {

    @Override
    public void onMethodCall(MethodCall methodCall, final MethodChannel.Result result) {
        switch (methodCall.method) {
            case "finish":
                Toast.makeText(AutoApplication.Companion.getInstance(), "finishing", Toast.LENGTH_SHORT).show();
                break;

            default:
                result.notImplemented();
                break;
        }
    }
}
