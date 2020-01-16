import 'package:fluro/fluro.dart';
import 'package:flutter_mall/pages/home/mall_home_page.dart';

import '../routes.dart';

class LoginRoutes {
  static String prefix = "/login";
  static String index = "$prefix/index";
  static String phone = "$prefix/phone";
  static String captcha = "$prefix/captcha";
  static String image = "$prefix/image";

  static init() {
    Routes.define("$index",
        page: MallHomePage(), transitionType: TransitionType.fadeIn);
//    Routes.define("$phone", handler: (BuildContext context, dynamic arguments) {
//      return LoginPage(
//        map: arguments,
//      );
//    });
//    Routes.define(
//      "$captcha",
//      handler: (BuildContext context, dynamic arguments) {
//        return LoginCaptchaPage(
//          arguments,
//        );
//      },
//    );
//    Routes.define(
//      "$image",
//      handler: (BuildContext context, dynamic arguments) {
//        return LoginImageCaptchaPage(
//          params: arguments,
//        );
//      },
//    );
  }
}
