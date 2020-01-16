import 'package:package_info/package_info.dart';

Future<String> getAppVersion() async {
  PackageInfo info = await getPackageInfo();
  return info.version;
}

Future<PackageInfo> getPackageInfo() async {
  return await PackageInfo.fromPlatform();
}
