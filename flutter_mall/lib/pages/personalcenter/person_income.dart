import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_mall/common/utils/image_utils.dart';
import 'package:flutter_mall/res/resources.dart';
import 'package:flutter_mall/widgets/toast_widget.dart';

///个人中心  收入widget

class IncomeColumnWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => IncomeColumnState();
}

class IncomeColumnState extends State<IncomeColumnWidget> {
  final Radius radius = Radius.circular(10.0);
  final double height = 96;

  Future _network() async {
//    try {
//      var content = await PersonalDao.getIncome();
//      setState(() {
//        _income = content;
//      });
//    } catch (e) {
//      print(e);
//    }
  }

  @override
  void didUpdateWidget(IncomeColumnWidget oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (!paused) {
      _network();
    }
  }

  bool paused = false;

  @override
  void deactivate() {
    super.deactivate();
    paused = !paused;
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: incomeOnTap,
      child: Container(
        child: Row(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            Expanded(
              child: Container(
                height: height,
                child: Container(
                  decoration: BoxDecoration(
                    color: Colors.white,
                    borderRadius: BorderRadius.all(radius),
                  ),
                  child: Row(
                    children: <Widget>[
                      Gaps.hGap19,
                      Container(
                        width: 62,
                        alignment: Alignment.center,
                        child: Container(
                          alignment: Alignment.center,
                          child: Text(
                            '100单',
                            textAlign: TextAlign.center,
                            style: TextStyle(
                              fontSize: 14.0,
                              color: Colors.black,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                        decoration: ShapeDecoration(
                          shape: CircleBorder(
                            side: BorderSide(color: Colors.black, width: 3.0),
                          ),
                        ),
                      ),
                      Gaps.hGap12,
                      Container(
                        alignment: Alignment.center,
                        child: Column(
                          mainAxisAlignment: MainAxisAlignment.center,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: <Widget>[
                            Text(
                              "¥10000",
                              style: TextStyle(
                                  fontSize: 36.0,
                                  color: Colours.black,
                                  fontWeight: FontWeight.bold),
                            ),
                            Gaps.vGap3,
                            Text(
                              "累积收入",
                              style: TextStyle(
                                fontSize: 12.0,
                                color: Colours.black,
                              ),
                            ),
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
            Container(
              padding: const EdgeInsets.fromLTRB(10.0, 13.0, 13.0, 13.0),
              height: height,
              alignment: Alignment.center,
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                  Text(
                    '经营99天',
                    textAlign: TextAlign.center,
                    style: TextStyle(
                      fontSize: 12.0,
                      color: Colours.color_9b9b9b,
                      decoration: TextDecoration.none,
                    ),
                  ),
                  loadAssetImage('ic_light_next'),
                ],
              ),
              decoration: BoxDecoration(
                color: Colours.color_fffef7d6,
                borderRadius:
                    BorderRadius.only(topRight: radius, bottomRight: radius),
                border: Border.all(
                  width: 1,
                  color: Colours.color_fffef7d6,
                ),
              ),
            )
          ],
        ),
        decoration: BoxDecoration(
          color: Colours.color_fff4f4f4,
          borderRadius: BorderRadius.all(radius),
        ),
      ),
    );
  }

  //经营管理
  void incomeOnTap() {
    ShowToast().showToast('敬请期待');
  }
}
