import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_mall/common/utils/image_utils.dart';
import 'package:flutter_mall/res/resources.dart';

///个人中心  管理模块
///

class PersonalManageWidget extends StatefulWidget {
  final String orderNum;
  final String incomeTotal;
  final String businessDays;
  final String percent;

  const PersonalManageWidget(
      {Key key,
      this.orderNum,
      this.incomeTotal,
      this.businessDays,
      this.percent})
      : super(key: key);

  @override
  State<StatefulWidget> createState() => ManageColumnState();
}

class ManageColumnState extends State<PersonalManageWidget> {
  final Radius radius = Radius.circular(10.0);

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      crossAxisAlignment: CrossAxisAlignment.center,
      mainAxisSize: MainAxisSize.min,
      children: <Widget>[
        Expanded(
          child: _buildItem(
            "网店管理",
            () => {},
            'ic_online_store',
          ),
        ),
        Gaps.hGap11,
        Expanded(
          child: _buildItem("推单管理", () => {}, 'recomm_record_unselect'),
        ),
      ],
    );
  }

  Widget _buildItem(String title, VoidCallback call, String iconPath) {
    return InkWell(
      onTap: call,
      child: Container(
        height: 88,
        alignment: Alignment.center,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: <Widget>[
            Container(
              child: Container(
                width: double.infinity,
                child: loadAssetImage(iconPath),
                decoration: BoxDecoration(
                  color: Colours.white,
                  borderRadius: BorderRadius.all(radius),
                ),
              ),
              height: 54.0,
              decoration: BoxDecoration(
                borderRadius: BorderRadius.all(radius),
              ),
            ),
            Expanded(
              child: Container(
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  mainAxisSize: MainAxisSize.max,
                  children: <Widget>[
                    Container(
                      alignment: Alignment.center,
                      child: Text(
                        title,
                        style: TextStyle(
                          fontSize: 12,
                          fontWeight: FontWeight.bold,
                          color: Colours.black,
                        ),
                      ),
                    ),
                    Gaps.hGap2,
                    Container(
                      padding: const EdgeInsets.fromLTRB(0, 6, 0, 5),
                      alignment: Alignment.center,
                      child: loadAssetImage('ic_dark_next'),
                    ),
                  ],
                ),
                decoration: BoxDecoration(
                  color: Colours.color_fffef7d6,
                  borderRadius: BorderRadius.only(
                    bottomLeft: radius,
                    bottomRight: radius,
                  ),
                ),
              ),
            ),
          ],
        ),
        decoration: BoxDecoration(
          color: Colours.color_fffef7d6,
          borderRadius: BorderRadius.all(radius),
        ),
      ),
    );
  }

  Icon itemIcon(IconData icon) {
    return Icon(
      icon,
      color: Colours.black,
    );
  }

  void onTap<T>(T data) {
    //跳转操作
  }
}
