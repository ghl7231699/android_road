import 'dart:io';
import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_mall/pages/home/mall_home_page.dart';
import 'package:flutter_mall/pages/personalcenter/person_page.dart';
import 'package:flutter_mall/res/resources.dart';
import 'package:flutter_mall/router/routes.dart';
import 'package:flutter_mall/widgets/no_scale_widget.dart';

void main() => runApp(_widgetBuildWithRoutes(window.defaultRouteName));
//void main() => runApp(_widgetBuildWithRoutes('/login/index'));

Widget _widgetBuildWithRoutes(String route) {
//  Routes.navigate(route);
  // TODO 如果属于集成单独的功能模块的话，需要在此对传递的路由做匹配

  if (Platform.isAndroid) {
    // 以下两行 设置android状态栏为透明的沉浸。写在组件渲染之后，是为了在渲染后进行set赋值，覆盖状态栏，写在渲染之前MaterialApp组件会覆盖掉这个值。
    SystemUiOverlayStyle systemUiOverlayStyle =
        SystemUiOverlayStyle(statusBarColor: Colors.transparent);
    SystemChrome.setSystemUIOverlayStyle(systemUiOverlayStyle);
  } else {
    SystemChrome.setSystemUIOverlayStyle(SystemUiOverlayStyle.light);
  }

  Widget widget() {
    switch (route) {
      case '/login/index':
        return PersonalCenter();
      case '/':
      default:
        return MallHomePage();
    }
  }

  return MaterialApp(
    title: '达人',
    theme: ThemeData(
      primaryColor: Colours.app_main,
      appBarTheme: AppBarTheme(
        color: Colors.white,
        elevation: 4,
      ),
    ),
    debugShowCheckedModeBanner: false,
    // 去掉debug logo
    onGenerateRoute: Routes.generator,
    navigatorObservers: <NavigatorObserver>[Routes.observer],
    navigatorKey: Routes.navigatorKey,
    home: widget(),
//      localizationsDelegates: [
//        GlobalCupertinoLocalizations.delegate,
//        GlobalWidgetsLocalizations.delegate,
//        GlobalMaterialLocalizations.delegate,
//      ],
    supportedLocales: [
      const Locale('zh', 'CH'),
      const Locale('en', 'US'),
    ],
    locale: Locale('zh'),
    builder: (context, widget) {
      return MaxScaleTextWidget(
        max: 1.0,
        child: widget,
      );
    },
  );
}
