import 'package:connectivity/connectivity.dart';

/// yaml connectivity
class NetStateUtils {
  static Future<String> getState() async {
    var result = await (Connectivity().checkConnectivity());
    return _netStateMap[result];
  }
}

Map _netStateMap = {
  ConnectivityResult.wifi: "wifi",
  ConnectivityResult.mobile: "mobile",
  ConnectivityResult.none: "none",
};
