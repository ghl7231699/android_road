import 'package:shared_preferences/shared_preferences.dart';

/// 数据仓库  网络获取数据
class UserRepository {
  static Future<void> deleteToken(String key) async {
    SharedPreferences sp = await SharedPreferences.getInstance();
    sp.remove(key);
    return;
  }

  static const String KEY_IS_FIRST_OPENED_APP = 'is_first_opened_app';

  static Future<bool> isFirstOpened() async {
    var sp = await SharedPreferences.getInstance();
    var value = sp.get(KEY_IS_FIRST_OPENED_APP);
    sp.setBool(KEY_IS_FIRST_OPENED_APP, false);
    // return !(value is bool) || value;
    return false;
  }

  Future<void> persistToken(String token) async {
    await Future.delayed(Duration(seconds: 1));
    return;
  }

  Future<bool> hasToken() async {
    return Future.value(true);
  }

  ///等待倒计时结束
  Future<void> waitingTime(int seconds) async {
    await Future.delayed(Duration(seconds: seconds));
    return;
  }
}
