import 'dart:io';

import 'package:device_info/device_info.dart';


class DevicesUtils {
  static AbsDevice _device = _isAndroid() ? AndroidDevice() : IosDevice();

  static bool _isAndroid() {
    return Platform.isAndroid;
  }

  bool _isIos() {
    return Platform.isIOS;
  }

  static Future<String> getDeviceName() {
    return _device.getDeviceName();
  }

  static Future<String> getOsInt() {
    return _device.getOSInt();
  }
}

abstract class AbsDevice {
  Future<String> getDeviceName();

  Future<String> getOSInt();
}

class AndroidDevice extends AbsDevice {
  Future<AndroidDeviceInfo> infoPlugin = DeviceInfoPlugin().androidInfo;

  @override
  Future<String> getDeviceName() async {
    return (await infoPlugin).device;
  }

  @override
  Future<String> getOSInt() async {
    return (await infoPlugin).version.release;
  }
}

class IosDevice extends AbsDevice {
  Future<IosDeviceInfo> infoPlugin = DeviceInfoPlugin().iosInfo;

  @override
  Future<String> getDeviceName() async {
    return (await infoPlugin).systemName;
  }

  @override
  Future<String> getOSInt() async {
    return (await infoPlugin).systemVersion;
  }
}
