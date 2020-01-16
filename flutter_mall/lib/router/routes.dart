import 'dart:async';
import 'dart:io';

import 'package:fluro/fluro.dart';
import 'package:flutter/material.dart';
import 'package:flutter_mall/pages/404.dart';
import 'package:flutter_mall/widgets/toast_widget.dart';
import 'package:uni_links/uni_links.dart';

import 'routes/login_routes.dart';
import 'routes/root_routes.dart';

typedef Widget RouterHandlerFunc(BuildContext context, dynamic arguments);

class Routes {
  static Router router = Router.appRouter;
  static Map<String, dynamic> _routerArguments = {};
  static GlobalKey<NavigatorState> navigatorKey = GlobalKey<NavigatorState>();
  static RouteFactory generator = Router.appRouter.generator;
  static RouteObserver<PageRoute> observer = RouteObserver<PageRoute>();

  static void init() {
    router.notFoundHandler = Handler(
        handlerFunc: (BuildContext context, Map<String, List<String>> params) {
      print("找不到路由");
      return WidgetNotFound();
    });

    //初始化路由表
    RootRoutes.init();
    LoginRoutes.init();
  }

  // 路由定义, page和handler二选一
  static void define(String path,
      {String prefix,
      Widget page,
      RouterHandlerFunc handler,
      TransitionType transitionType}) {
    Router.appRouter.define(path, handler: Handler(handlerFunc:
            (BuildContext context, Map<String, List<String>> params) {
      if (handler != null) {
        return handler(context, _routerArguments[path]);
      }
      if (page != null) {
        return page;
      }
      return null;
    }),
        transitionType: transitionType ??
            (Platform.isIOS
                ? TransitionType.native
                : TransitionType.inFromRight));
  }

  // 路由跳转
  static Future navigateTo(BuildContext context, String path,
      {dynamic params,
      bool replace = false,
      bool clearStack = false,
      TransitionType transition,
      Duration transitionDuration = const Duration(milliseconds: 250),
      RouteTransitionsBuilder transitionBuilder}) {
    _routerArguments[path] = params;
    return router.navigateTo(context, path,
        replace: replace,
        clearStack: clearStack,
        transition: transition,
        transitionDuration: transitionDuration,
        transitionBuilder: transitionBuilder);
  }

  static bool pop(BuildContext context, [dynamic result]) {
    return Navigator.of(context).pop(result);
  }

  // 路由跳转, 不带context
  static Future navigate(String path, {dynamic params, bool replace: false}) {
    _routerArguments[path] = params;
    return replace
        ? navigatorKey.currentState.pushReplacementNamed(path)
        : navigatorKey.currentState.pushNamed(path);
  }

  static Future navigateRemove(String path,
      {dynamic params, bool remove: false}) {
    return navigateTo(navigatorKey.currentContext, path,
        params: params, clearStack: remove);
  }

  static Future open(String url,
      {bool showTitle = true, String showBottom = 'true'}) {
    Uri uri = Uri.parse(url);
    Map<String, String> params = uri.queryParameters;
    var replace = params != null && params['replace'] == 'true';
    if (url.startsWith("http")) {
      //http链接
      return navigate("/webview",
          params: {
            "url": url,
            'showTitle': showTitle,
          },
          replace: replace);
    }

    //内部路由
    if (url.startsWith("talent://")) {
      Map<String, String> paramNew = Map.from(params);
      paramNew['showBottom'] = showBottom;
      String route = "/" + uri.host + uri.path;
      try {
        return navigate(route, params: paramNew, replace: replace);
      } catch (e) {
        ShowToast().showToast('找不到路由');
      }
    }

    Completer completer = Completer();
    Future future = completer.future;
    String error = "找不到路由 '$url'.";
    completer.completeError(RouteNotFoundException(error, url));
    return future;
  }

  static handleDeepLink() async {
    getLinksStream().listen(open);

    String link = await getInitialLink();
    if (link != null && link.isNotEmpty) {
      open(link);
    }
  }
}
