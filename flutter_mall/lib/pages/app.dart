import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_mall/pages/home/mall_home_page.dart';
import 'package:flutter_mall/res/resources.dart';
import 'package:flutter_mall/router/routes.dart';
import 'package:flutter_mall/widgets/no_scale_widget.dart';

class App extends StatefulWidget {
  App() {
//    Env.setting(false); //false:online;true:TEST
//    EnvironmentManager.init(Env.isDebug());
//    EnvironmentManager.getHost().then((host) {
//      Url.change(host);
//      return;
//    });
//    DioGo.init(pem);
//    HttpImageOverrides.init(pem);
//    NetBizUtils.init();
    Routes.init();
  }

  @override
  _AppState createState() => _AppState();
}

class _AppState extends State<App> {
//  final userRepository = UserRepository();

  @override
  Widget build(BuildContext context) {
//    return BlocProvider<AuthenticationBloc>(
//      builder: (context) {
//        return AuthenticationBloc(userRepository: userRepository)
//          ..dispatch(AppStarted());
//      },
//      child:
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
      home: MallHomePage(),
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
//    );
  }
}

//class SimpleBlocDelegate extends BlocDelegate {
//  @override
//  void onEvent(Bloc bloc, Object event) {
//    super.onEvent(bloc, event);
//    print(event);
//  }
//
//  @override
//  void onTransition(Bloc bloc, Transition transition) {
//    super.onTransition(bloc, transition);
//    print(transition);
//  }
//
//  @override
//  void onError(Bloc bloc, Object error, StackTrace stacktrace) {
//    super.onError(bloc, error, stacktrace);
//    print(error);
//  }
//}
//
//class DefaultDebugManager implements AbsDebugManager {
//  bool _isShow = false;
//
//  DefaultDebugManager() {
//    shakeInject(jump);
//  }
//
//  static void init(bool isDebug) {
//    if (!isDebug) {
//      return;
//    }
//    DefaultDebugManager();
//  }
//
//  void jump() async {
//    if (!_isShow) {
//      _isShow = true;
//      String host = await EnvironmentManager.getHost();
//      AbsDebugManager manager = this;
//      Map<String, dynamic> map = Map<String, dynamic>();
//      map['host'] = host;
//      map['manager'] = manager;
//      Routes.navigate("/debug", params: map);
//    }
//  }
//
//  @override
//  void changeHost(BuildContext context, String host) {
//    EnvironmentManager.setHost(host).then((host) => Url.change(host));
//    Routes.navigateTo(context, LoginRoutes.index, replace: true);
//  }
//
//  @override
//  void changeShow(bool isShow) {
//    this._isShow = isShow;
//  }
//
//  @override
//  void testRoute(BuildContext context, String path, dynamic params) {
//    Routes.navigateTo(context, path, params: params);
//  }
//
//  @override
//  void map(Map<String, dynamic> param) async {}
//}
