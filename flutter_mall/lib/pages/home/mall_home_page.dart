import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/services.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'package:flutter_mall/channel_constants.dart';
import 'package:flutter_mall/common/utils/image_utils.dart';
import 'package:flutter_mall/res/resources.dart';
import 'package:flutter_mall/widgets/loading_widget.dart';
import 'package:flutter_mall/widgets/text_icon_widget.dart';
import 'package:flutter_mall/widgets/toast_widget.dart';

class MallHomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    var image = loadAssetImage('main_login_bg', fit: BoxFit.cover);
    return Scaffold(
      body: Stack(
        fit: StackFit.expand,
        alignment: Alignment.bottomCenter,
        children: <Widget>[
          image,
          loadAssetImage(
            'main_login_bg_layer',
            fit: BoxFit.cover,
          ),
          Positioned(
            bottom: 150,
            left: 10,
            child: loadAssetImage(
              'main_login_bg_title',
              fit: BoxFit.cover,
            ),
          ),
          UserLoginWidget(),
        ],
      ),
    );
  }
}

class UserLoginWidget extends StatefulWidget {
  final bool showIcon;

  const UserLoginWidget({Key key, this.showIcon: false}) : super(key: key);

  @override
  State<StatefulWidget> createState() => UserLoginMainState();
}

class UserLoginMainState extends State<UserLoginWidget> {
  bool loading = true;
  static const platform = const MethodChannel(Method_channel);

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Stack(
      fit: StackFit.expand,
      alignment: Alignment.bottomCenter,
      children: <Widget>[
        Positioned(
          bottom: 0,
          child: Container(
            alignment: Alignment.center,
            child: Padding(
              padding: EdgeInsets.all(20),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                  TextIcon(
                    showBg: true,
                    imageAssets: false,
                    onPressed: () async {
                      setState(() {
                        loading = false;
                      });
                      FlutterBoost.singleton
                          .open("sample://nativePage")
                          .then((Map value) {
                        print('$value');
                        ShowToast().showToast(value['native']);
                      });
                    },
                    text: "使用微信一键登录",
                    fontSize: 16,
                    textColor: Colours.color_212121,
                    showRightIcon: false,
                    showLeftIcon: false,
                    backgroundColor: Colours.app_main,
                  ),
                  TextIcon(
                    showBg: false,
                    imageAssets: true,
                    showRightIcon: true,
                    showLeftIcon: false,
                    rightIconPath: 'ic_advance',
                    rightIconColor: Colours.white,
                    onPressed: () {
//                      LoginOnTap.onPhoneTap(
//                        context,
//                        from: loginPhone,
//                        replace: false,
//                      );
                      jumpToHomePage();
                    },
                    text: '使用手机号登录',
                    fontSize: 13,
                    textColor: Colours.white,
                  )
                ],
              ),
            ),
          ),
        ),
        Offstage(
          offstage: loading,
          child: Center(
            child: loadingView(),
          ),
        )
      ],
    );
  }

  void jumpToHomePage() async {
    await platform.invokeMethod(
      method_finish,
    );
  }

  Widget loadingView() {
    return Material(
      //创建透明层
      type: MaterialType.transparency, //透明类型(dialog的半透明效果)
      child: GestureDetector(
        onTap: () {
          setState(() {
            loading = true;
          });
        },
        child: Container(
          color: Colors.black54,
          child: Center(
            child: SpinKitWave(
              color: Colours.app_main,
              type: SpinKitWaveType.start,
            ),
          ),
        ),
      ),
    );
  }
}
