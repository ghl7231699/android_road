import 'package:flutter/material.dart';
import 'package:flutter_mall/res/resources.dart';

class WidgetNotFound extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: Text(
          "页面不存在",
        ),
      ),
      body: Center(
        child: Text(
          "页面不存在",
          style: TextStyles.textBoldDark16,
        ),
      ),
    );
  }
}
