import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_mall/common/utils/image_utils.dart';
import 'package:flutter_mall/res/resources.dart';

class IconDatas {
  String icon;
  VoidCallback callback;

  IconDatas(this.icon, this.callback);
}

class MyAppBar extends StatefulWidget implements PreferredSizeWidget {
  const MyAppBar({
    Key key,
    this.backgroundColor: Colors.white,
    this.title: "",
    this.centerTitle: "",
    this.actionName: "",
    this.onPressed1,
    this.onPressed2,
    this.onPressed3,
    this.backImg: "ic_back",
    this.onPressed,
    this.showIconRight: false,
    this.iconRight,
    this.showIconRight2: false,
    this.iconRight2,
    this.iconRight3,
    this.isBack: true,
    this.fontSize = Dimens.font_sp18,
    this.fontWeight = FontWeight.normal,
    this.bottom,
    this.bottomOpacity,
    this.icons,
    this.radius: true,
  }) : super(key: key);

  final Color backgroundColor;
  final String title;
  final String centerTitle;
  final String backImg;
  final String actionName;
  final VoidCallback onPressed;
  final VoidCallback onPressed1;
  final VoidCallback onPressed2;
  final VoidCallback onPressed3;
  final bool isBack;
  final bool showIconRight;
  final String iconRight;
  final bool showIconRight2;
  final String iconRight2;
  final String iconRight3;
  final double fontSize;
  final FontWeight fontWeight;
  final PreferredSizeWidget bottom;
  final double bottomOpacity;
  final List<IconDatas> icons;
  final bool radius;

  @override
  _MyAppBarState createState() => _MyAppBarState();

  @override
  Size get preferredSize =>
      Size.fromHeight(kToolbarHeight + (bottom?.preferredSize?.height ?? 0.0));
}

class _MyAppBarState extends State<MyAppBar> {
  SystemUiOverlayStyle _overlayStyle = SystemUiOverlayStyle.light;

  @override
  void initState() {
    super.initState();
    setState(() {
      _overlayStyle =
          ThemeData.estimateBrightnessForColor(widget.backgroundColor) ==
                  Brightness.dark
              ? SystemUiOverlayStyle.light
              : SystemUiOverlayStyle.dark;
    });
  }

  Color getColor() {
    return _overlayStyle == SystemUiOverlayStyle.light
        ? Colors.white
        : Colours.black;
  }

  @override
  Widget build(BuildContext context) {
    Widget appBar = SafeArea(
      child: Stack(
        alignment: Alignment.centerLeft,
        children: <Widget>[
          Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Container(
                alignment: widget.centerTitle.isEmpty
                    ? Alignment.centerLeft
                    : Alignment.center,
                width: double.infinity,
                child: Text(
                  widget.title.isEmpty ? widget.centerTitle : widget.title,
                  style: TextStyle(
                    fontSize: widget.fontSize,
                    color: getColor(),
                    fontWeight: widget.fontWeight,
                    decoration: TextDecoration.none,
                  ),
                ),
                padding: const EdgeInsets.symmetric(horizontal: 56.0),
              )
            ],
          ),
          widget.isBack
              ? GestureDetector(
                  onTap: () {
                    Navigator.of(context).pop();
                  },
                  child: Container(
                    padding: const EdgeInsets.fromLTRB(24.20, 0, 0, 0),
                    child: loadAssetImage(
                      widget.backImg,
                      color: getColor(),
                    ),
                  ),
                )
              : Gaps.empty,
          Positioned(
            right: 0.0,
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                Theme(
                  data: ThemeData(
                      buttonTheme: ButtonThemeData(
                    padding: const EdgeInsets.symmetric(horizontal: 16.0),
                    minWidth: 60.0,
                  )),
                  child: widget.actionName.isEmpty
                      ? Container()
                      : FlatButton(
                          child: Text(widget.actionName),
                          textColor: getColor(),
                          highlightColor: Colors.transparent,
                          onPressed: widget.onPressed,
                        ),
                ),
              ],
            ),
          ),
          Positioned(
            right: 20.0,
            child: widget.icons?.isEmpty ?? true
                ? Container()
                : Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    mainAxisSize: MainAxisSize.max,
                    children: buildIcons(),
                  ),
          ),
        ],
      ),
    );

    if (widget.bottom != null) {
      appBar = Column(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          Flexible(
            child: ConstrainedBox(
              constraints: const BoxConstraints(maxHeight: kToolbarHeight),
              child: appBar,
            ),
          ),
          widget.bottom,
        ],
      );
    }

    return AnnotatedRegion<SystemUiOverlayStyle>(
      value: _overlayStyle,
      child: Material(
        color: widget.backgroundColor,
        shadowColor: Colours.white,
        borderRadius: BorderRadius.vertical(
          bottom: widget.radius ? Radius.circular(25.0) : Radius.circular(0),
        ),
        child: SafeArea(
          child: Container(
            child: appBar,
//            decoration: BoxDecoration(
//              color: Colours.white,
//              borderRadius: BorderRadius.vertical(
//                bottom: Radius.circular(25.0),
//              ),
//            ),
          ),
        ),
      ),
    );
  }

  List<Widget> buildIcons() {
    return widget.icons.map((icon) {
      return Offstage(
        offstage: icon == null || (icon.icon?.isEmpty ?? true),
        child: GestureDetector(
          onTap: icon.callback,
          child: Row(
            children: <Widget>[
              Gaps.hGap22,
              loadAssetImage(icon.icon),
            ],
          ),
        ),
      );
    }).toList();
  }
}

class AppBarTitle extends StatelessWidget {
  final String title;

  const AppBarTitle(this.title, {Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Text(
      title,
      style: TextStyles.textBoldDark16.copyWith(letterSpacing: 2),
    );
  }
}

class AppBarLeading extends StatelessWidget {
  final GestureTapCallback onTap;

  const AppBarLeading({Key key, this.onTap}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      child: Icon(Icons.arrow_back),
      onTap: onTap != null ? onTap : () => Navigator.pop(context),
    );
  }
}
