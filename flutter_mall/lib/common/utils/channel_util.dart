import 'dart:io';

import 'package:flutter/services.dart';

class ChannelUtils {
  static const _platformChannel = const MethodChannel('xiaozhu/channel');
  static const _methodNameChannel = 'getChannel';
  static Future<String> _channel;

  /// iPhone iPad => appstore
  /// Android => walle 渠道 (xiaozhu,xiaomi,huawai ...)
  /// other => unknown
  static Future<String> _getChannel() async {
    String channel = "unknown";
    if (Platform.isIOS) {
      channel = "appstore";
    } else if (Platform.isAndroid) {
      channel = await getAndroidChannel(_platformChannel, _methodNameChannel);
    } else {
      print("excuse me?");
    }
    return channel;
  }

  static Future<String> getChannel() {
    if (_channel == null) {
      _channel = _getChannel();
    }
    return _channel;
  }

  static Future<String> getAndroidChannel(
      MethodChannel platform, String methodName) async {
    String androidChannel;
    try {
      androidChannel = await platform.invokeMethod('getChannel');
    } on PlatformException catch (e) {
      print("Failed to get channel: '${e.message}'.");
      androidChannel = "android_channel_error";
    }
    return androidChannel;
  }
}
