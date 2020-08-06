//import 'dart:io';
//import 'dart:ui';
//
//import 'package:flutter/material.dart';
//import 'package:flutter/services.dart';
//import 'package:flutter_boost/flutter_boost.dart';
//import 'package:flutter_mall/flutter_boost/simple_page_widgets.dart';
//import 'package:flutter_mall/pages/home/mall_home_page.dart';
//import 'package:flutter_mall/pages/personalcenter/person_page.dart';
//import 'package:flutter_mall/res/resources.dart';
//import 'package:flutter_mall/router/routes.dart';
//import 'package:flutter_mall/widgets/no_scale_widget.dart';
//import 'package:provider/provider.dart';
//
////void main() => runApp(_widgetBuildWithRoutes(window.defaultRouteName));
////void main() => runApp(_widgetBuildWithRoutes('/login/index'));
////void main() => runApp(_widgetBuildWithRoutes('sample://flutterFragmentPage'));
////void main() => runApp(_widgetBuildWithRoutes('/'));
//void main() => runApp(MyApp());
//
///// The entrypoint for the flutter module.
////void main() {
////  final model = CounterModel();
////
////  runApp(
////    ChangeNotifierProvider.value(
////      value: model,
////      child: MyApp(),
////    ),
////  );
////}
//
//const String method_channel = 'foo';
//
//Widget _widgetBuildWithRoutes(String route) {
//  // This call ensures the Flutter binding has been set up before creating the
//  // MethodChannel-based model.
//  WidgetsFlutterBinding.ensureInitialized();
////  Routes.navigate(route);
//  // TODO 如果属于集成单独的功能模块的话，需要在此对传递的路由做匹配
//
//  if (Platform.isAndroid) {
//    // 以下两行 设置android状态栏为透明的沉浸。写在组件渲染之后，是为了在渲染后进行set赋值，覆盖状态栏，写在渲染之前MaterialApp组件会覆盖掉这个值。
//    SystemUiOverlayStyle systemUiOverlayStyle =
//        SystemUiOverlayStyle(statusBarColor: Colors.transparent);
//    SystemChrome.setSystemUIOverlayStyle(systemUiOverlayStyle);
//  } else {
//    SystemChrome.setSystemUIOverlayStyle(SystemUiOverlayStyle.light);
//  }
//
//  Widget widget() {
//    switch (route) {
//      case '/login/index':
//        return PersonalCenter();
//      case 'sample://flutterFragmentPage':
//        return FlutterBoostApp();
//      case '/':
//      default:
//        return MallHomePage();
//    }
//  }
//
//  return MaterialApp(
//    title: '达人',
//    theme: ThemeData(
//      primaryColor: Colours.app_main,
//      appBarTheme: AppBarTheme(
//        color: Colors.white,
//        elevation: 4,
//      ),
//    ),
//    debugShowCheckedModeBanner: false,
//    // 去掉debug logo
//    onGenerateRoute: Routes.generator,
//    navigatorObservers: <NavigatorObserver>[Routes.observer],
//    navigatorKey: Routes.navigatorKey,
//    home: widget(),
////      localizationsDelegates: [
////        GlobalCupertinoLocalizations.delegate,
////        GlobalWidgetsLocalizations.delegate,
////        GlobalMaterialLocalizations.delegate,
////      ],
//    supportedLocales: [
//      const Locale('zh', 'CH'),
//      const Locale('en', 'US'),
//    ],
//    locale: Locale('zh'),
//    builder: (context, widget) {
//      return MaxScaleTextWidget(
//        max: 1.0,
//        child: widget,
//      );
//    },
//  );
//}
//
///// A simple model that uses a [MethodChannel] as the source of truth for the
///// state of a counter.
/////
///// Rather than storing app state data within the Flutter module itself (where
///// the native portions of the app can't access it), this module passes messages
///// back to the containing app whenever it needs to increment or retrieve the
///// value of the counter.
//class CounterModel extends ChangeNotifier {
//  CounterModel() {
//    _channel.setMethodCallHandler(_handleMessage);
//    _channel.invokeMethod('requestCounter');
//  }
//
//  final _channel = MethodChannel('dev.flutter.example/counter');
//
//  int _count = 0;
//
//  int get count => _count;
//
//  void increment() {
//    _channel.invokeMethod('incrementCounter');
//  }
//
//  Future<dynamic> _handleMessage(MethodCall call) async {
//    if (call.method == 'reportCounter') {
//      _count = call.arguments as int;
//      notifyListeners();
//    }
//  }
//}
//
///// The "app" displayed by this module.
/////
///// It offers two routes, one suitable for displaying as a full screen and
///// another designed to be part of a larger UI.class MyApp extends StatelessWidget {
//class MyApp extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return MaterialApp(
//      title: 'Flutter Module Title',
//      routes: {
//        '/': (context) => FullScreenView(),
//        '/mini': (context) => Contents(),
//      },
//    );
//  }
//}
//
///// Wraps [Contents] in a Material [Scaffold] so it looks correct when displayed
///// full-screen.
//class FullScreenView extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return Scaffold(
//      appBar: AppBar(
//        title: Text('Full-screen Flutter'),
//      ),
//      body: const Contents(showExit: true),
//    );
//  }
//}
//
///// The actual content displayed by the module.
/////
///// This widget displays info about the state of a counter and how much room (in
///// logical pixels) it's been given. It also offers buttons to increment the
///// counter and (optionally) close the Flutter view.
//class Contents extends StatelessWidget {
//  final bool showExit;
//  static const channel = MethodChannel('foo');
//
//  const Contents({this.showExit = false});
//
//  @override
//  Widget build(BuildContext context) {
//    final mediaInfo = MediaQuery.of(context);
//
//    return SizedBox.expand(
//      child: Stack(
//        children: [
//          Positioned.fill(
//            child: DecoratedBox(
//              decoration: BoxDecoration(
//                color: Theme.of(context).scaffoldBackgroundColor,
//              ),
//            ),
//          ),
//          Positioned.fill(
//            child: Opacity(
//              opacity: .25,
//              child: FittedBox(
//                fit: BoxFit.cover,
//                child: FlutterLogo(),
//              ),
//            ),
//          ),
//          Center(
//            child: Column(
//              mainAxisAlignment: MainAxisAlignment.center,
//              children: [
//                Text(
//                  'Window is ${mediaInfo.size.width.toStringAsFixed(1)} x '
//                  '${mediaInfo.size.height.toStringAsFixed(1)}',
//                  style: Theme.of(context).textTheme.headline,
//                ),
//                SizedBox(height: 16),
//                Consumer<CounterModel>(
//                  builder: (context, model, child) {
//                    return Text(
//                      'Taps: ${model.count}',
//                      style: Theme.of(context).textTheme.headline,
//                    );
//                  },
//                ),
//                SizedBox(height: 16),
//                Consumer<CounterModel>(
//                  builder: (context, model, child) {
//                    return RaisedButton(
//                      onPressed: () => model.increment(),
////                      onPressed: () => {
////                        channel.invokeMethod('findActivity'),
////                        print('channle  go go go')
////                      },
//                      child: Text('Tap me!'),
//                    );
//                  },
//                ),
//                if (showExit) ...[
//                  SizedBox(height: 16),
//                  RaisedButton(
//                    onPressed: () => SystemNavigator.pop(),
//                    child: Text('Exit this screen'),
//                  ),
//                ],
//              ],
//            ),
//          ),
//        ],
//      ),
//    );
//  }
//}
//
///// **************************************Flutter Boost*******************************************/
//
//class FlutterBoostApp extends StatefulWidget {
//  @override
//  _FlutterBoostAppState createState() => _FlutterBoostAppState();
//}
//
//class _FlutterBoostAppState extends State<FlutterBoostApp> {
//  @override
//  void initState() {
//    super.initState();
//
//    FlutterBoost.singleton.registerPageBuilders({
//      'embeded': (pageName, params, _) => EmbededFirstRouteWidget(),
//      'first': (pageName, params, _) => FirstRouteWidget(),
//      'second': (pageName, params, _) => SecondRouteWidget(),
//      'tab': (pageName, params, _) => TabRouteWidget(),
//      'platformView': (pageName, params, _) => PlatformRouteWidget(),
//      'flutterFragment': (pageName, params, _) => FragmentRouteWidget(params),
//
//      ///可以在native层通过 getContainerParams 来传递参数
//      'flutterPage': (pageName, params, _) {
//        print("flutterPage params:$params");
//
//        return FlutterRouteWidget(params: params);
//      },
//    });
//  }
//
//  @override
//  Widget build(BuildContext context) {
//    return MaterialApp(
//        title: 'Flutter Boost example',
//        builder: FlutterBoost.init(postPush: _onRoutePushed),
//        home: Container(color: Colors.white));
//  }
//
//  void _onRoutePushed(
//      String pageName, String uniqueId, Map params, Route route, Future _) {}
//}

import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';

import 'flutter_boost/simple_page_widgets.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();

    FlutterBoost.singleton.registerPageBuilders({
      'embeded': (pageName, params, _) => EmbededFirstRouteWidget(),
      'first': (pageName, params, _) => FirstRouteWidget(),
      'second': (pageName, params, _) => SecondRouteWidget(),
      'tab': (pageName, params, _) => TabRouteWidget(),
      'platformView': (pageName, params, _) => PlatformRouteWidget(),
      'flutterFragment': (pageName, params, _) => FragmentRouteWidget(params),

      ///可以在native层通过 getContainerParams 来传递参数
      'flutterPage': (pageName, params, _) {
        print("flutterPage params:$params");

        return FlutterRouteWidget(params: params);
      },
    });
    FlutterBoost.singleton
        .addBoostNavigatorObserver(TestBoostNavigatorObserver());
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Flutter Boost example',
        builder: FlutterBoost.init(postPush: _onRoutePushed),
        home: Container(color: Colors.white));
  }

  void _onRoutePushed(
      String pageName, String uniqueId, Map params, Route route, Future _) {}
}

class TestBoostNavigatorObserver extends NavigatorObserver {
  void didPush(Route<dynamic> route, Route<dynamic> previousRoute) {
    print("flutterboost#didPush");
  }

  void didPop(Route<dynamic> route, Route<dynamic> previousRoute) {
    print("flutterboost#didPop");
  }

  void didRemove(Route<dynamic> route, Route<dynamic> previousRoute) {
    print("flutterboost#didRemove");
  }

  void didReplace({Route<dynamic> newRoute, Route<dynamic> oldRoute}) {
    print("flutterboost#didReplace");
  }
}


//TODO  手势侧滑  可能需要用一个手势来包裹全部的view 重写侧滑的事件