import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_mall/common/utils/image_utils.dart';

///带icon的text widget

class TextIcon extends StatefulWidget {
  final bool showLeftIcon;
  final bool showRightIcon;
  final String text;
  final Color textColor;
  final String leftIconPath;
  final String rightIconPath;
  final VoidCallback onPressed;
  final Color leftIconColor;
  final Color rightIconColor;
  final bool imageAssets;
  final bool showBg;
  final double fontSize;
  final Color backgroundColor;
  final double height;

  const TextIcon({
    Key key,
    this.showLeftIcon: true,
    this.showRightIcon: false,
    @required this.text,
    this.textColor: Colors.transparent,
    this.leftIconPath,
    this.rightIconPath,
    this.leftIconColor,
    this.rightIconColor,
    @required this.imageAssets,
    @required this.showBg,
    this.fontSize,
    this.onPressed,
    this.backgroundColor,
    this.height: 50.0,
  }) : super(key: key);

  @override
  State<StatefulWidget> createState() => TextIconState();
}

class TextIconState extends State<TextIcon> {
  @override
  Widget build(BuildContext context) {
    final double _width = MediaQuery.of(context).size.width;
    return Container(
      child: InkWell(
        onTap: widget.onPressed,
        child: Container(
          width: _width - 40,
          padding: EdgeInsets.only(top: 14, bottom: 14),
//          margin: EdgeInsets.only(left: 20.0, right: 20.0),
          child: Row(
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisSize: MainAxisSize.min,
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Offstage(
                offstage: !widget.showLeftIcon,
                child: widget.showLeftIcon
                    ? Container(
                        child: imageOrIcon(true),
                      )
                    : Container(),
              ),
              Container(
                margin: EdgeInsets.only(left: 5),
                child: Text(
                  widget.text,
                  style: TextStyle(
                      color: widget.textColor,
                      fontSize:
                          widget.fontSize != null ? widget.fontSize : 16.0,
                      fontWeight: FontWeight.normal),
                ),
              ),
              Offstage(
                offstage: !widget.showRightIcon,
                child: widget.showRightIcon
                    ? Container(
                        child: imageOrIcon(false),
                      )
                    : Container(),
              ),
            ],
          ),
          decoration: widget.showBg
              ? BoxDecoration(
                  color: widget.backgroundColor,
                  borderRadius: BorderRadius.all(Radius.circular(100)),
                )
              : null,
        ),
      ),
    );
  }

  Widget imageOrIcon(bool left) {
    return loadAssetImage(
      left ? widget.leftIconPath : widget.rightIconPath,
      width: 25.0,
      height: 25.0,
      color: left ? widget.leftIconColor : widget.rightIconColor,
    );
  }
}
