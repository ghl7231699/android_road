import 'dart:io';

import 'package:flutter_mall/common/utils/package_utils.dart';
import 'package:flutter_mall/common/utils/user_agent_utils.dart';

import 'channel_util.dart';
import 'devices_utils.dart';
import 'net_utils.dart';

class AppUtils {
  static Future<String> getUserAgent() async {
    Future<String> channel = ChannelUtils.getChannel();
    Future<String> version = getAppVersion();
    Future<String> phone = DevicesUtils.getDeviceName();
    Future<String> os = DevicesUtils.getOsInt();
    Future<String> netState = NetStateUtils.getState();

    List<String> results =
        await Future.wait([channel, version, phone, os, netState]);

    return UserAgentUtils.uniteUA(
      channel: results[0],
      version: results[1],
      phone: results[2],
      os: results[3],
      osName: Platform.operatingSystem,
      netState: results[4],
    );
  }

  static Future<Map> getUserInfo() async {
    Future<String> channel = ChannelUtils.getChannel();
    Future<String> version = getAppVersion();
    Future<String> phone = DevicesUtils.getDeviceName();
    Future<String> os = DevicesUtils.getOsInt();
    Future<String> netState = NetStateUtils.getState();

    List<String> results =
        await Future.wait([channel, version, phone, os, netState]);

    return {
      "app": "talent",
      "version": results[1],
      "channel": results[0],
      "device": results[2],
      "os": results[3],
      "platform": Platform.operatingSystem,
      "net": results[4],
    };
  }
}
