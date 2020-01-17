import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_mall/res/constants.dart';

class WindowBackgroundPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _State();
  }
}

class _State extends State<WindowBackgroundPage> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.white,
      // child: Image(image: assetImage),
      child: Image.asset(
        default_app_background_image,
        fit: BoxFit.cover,
      ),
    );
  }
}
