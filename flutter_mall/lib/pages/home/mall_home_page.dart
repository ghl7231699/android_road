import 'package:flutter/material.dart';
import 'package:flutter_mall/res/colors.dart';
import 'package:flutter_mall/widgets/toast_widget.dart';

// 首页
class MallHomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colours.color_9b9b9b,
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Text(
            "你好，我是首页",
            textAlign: TextAlign.center,
            style: TextStyle(fontSize: 16, color: Colours.black),
          ),
          GestureDetector(
            child: Text(
              "点我",
            ),
            onTap: () {
              showToast("点击了我");
            },
          )
        ],
      ),
    );
  }
}
