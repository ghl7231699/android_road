import 'package:flutter/material.dart';
import 'package:flutter_mall/common/utils/flutter_screenutil.dart';
import 'package:flutter_mall/common/utils/image_utils.dart';
import 'package:flutter_mall/pages/personalcenter/person_income.dart';
import 'package:flutter_mall/res/resources.dart';
import 'package:flutter_mall/widgets/app_bar.dart';

import 'person_head_portrait.dart';
import 'person_mange.dart';

/// 个人中心
class PersonalCenter extends StatefulWidget {
  @override
  _PersonalCenterState createState() => _PersonalCenterState();
}

class _PersonalCenterState extends State<PersonalCenter> {
  final Radius radius = Radius.circular(20.0);
  List<IconDatas> icons = List();
  var radius10 = Radius.circular(10.0);

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.instance =
        ScreenUtil(width: 375, height: 667, allowFontScaling: false)
          ..init(context);
    double width = MediaQuery.of(context).size.width;
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: MyAppBar(
        isBack: false,
        icons: icons,
      ),
      body: Stack(
        children: <Widget>[
          Container(
            width: width,
            alignment: Alignment.center,
            child: ClipRRect(
              child: loadAssetImage(
                'bg_person',
                fit: BoxFit.fill,
                width: ScreenUtil().setWidth(375),
                height: ScreenUtil().setHeight(620),
              ),
              borderRadius: BorderRadius.all(radius10),
            ),
          ),
          Container(
            padding: const EdgeInsets.fromLTRB(20.0, 0, 20, 0),
            decoration: BoxDecoration(
              borderRadius: BorderRadius.all(radius),
            ),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                Gaps.vGap44,
                HeadPortrait(() {}),
                Gaps.vGap20,
                IncomeColumnWidget(),
                Gaps.vGap13,
                PersonalManageWidget()
              ],
            ),
          ),
        ],
      ),
    );
  }
}
