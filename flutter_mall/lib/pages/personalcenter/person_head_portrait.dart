import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:flutter_mall/common/utils/image_utils.dart';
import 'package:flutter_mall/res/constants.dart';
import 'package:flutter_mall/res/resources.dart';

///个人头像及账号

class HeadPortrait extends StatelessWidget {
  final VoidCallback _onPress;

  const HeadPortrait(this._onPress);

  final bool show = false;

  @override
  Widget build(BuildContext context) {
    var tikTalk = '抖音: 139132i4i4342225256';
    var redBook = '小红书: fuyaontgna';
    var account = [tikTalk, redBook].where((it) => it != null).join(' ; ');

    bool showPenIcon = account.isEmpty || tikTalk == null || redBook == null;
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      crossAxisAlignment: CrossAxisAlignment.center,
      children: <Widget>[
        Stack(
          alignment: Alignment.center,
          children: <Widget>[
            CircleAvatar(
              backgroundColor: Colors.white,
              radius: 45,
            ),
            GestureDetector(
              onTap: () {},
              child: ClipOval(
                child: Container(
                  width: 60,
                  height: 60,
                  child: CachedNetworkImage(
                    fit: BoxFit.cover,
                    imageUrl:
                        'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1579175945583&di=b84e45cf4cfcf28db0053b09e0c992c1&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201511%2F25%2F20151125070541_jSQkt.thumb.700_0.jpeg',
                    placeholder: (context, url) =>
                        Image.asset(default_image_placeholder),
                    errorWidget: (a, b, c) => Image.asset(default_image_failed),
                  ),
                ),
              ),
            ),
          ],
        ),
        Gaps.vGap2,
        Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            Text(
              '隔壁老宋',
              style: TextStyle(
                fontSize: 16.0,
                color: Colours.color_641C06,
                fontWeight: FontWeight.bold,
              ),
            ),
            Gaps.vGap7,
            show
                ? buildMessage()
                : Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: <Widget>[
                      Container(
                        alignment: Alignment.center,
                        child: Text(
                          '补充个人信息，有利于打造个人ip',
                          style: TextStyles.textNormalDark12,
                        ),
                      ),
                      Visibility(
                        visible: showPenIcon,
                        child: GestureDetector(
                          onTap: _onPress,
                          child: Padding(
                            padding: EdgeInsets.only(left: 10),
                            child: loadAssetImage('ic_edit_message'),
                          ),
                        ),
                      ),
                    ],
                  ),
            Visibility(
              visible: account.isNotEmpty,
              child: Text(
                account,
                style: TextStyles.textDark12,
              ),
            ),
          ],
        ),
      ],
    );
  }

  Widget buildMessage() {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      crossAxisAlignment: CrossAxisAlignment.center,
      children: <Widget>[
        Gaps.vGap6,
        Text(
          '',
          style: TextStyle(
            fontSize: 12.0,
            color: Colours.black,
            fontWeight: FontWeight.bold,
          ),
        ),
        Gaps.vGap6,
        GestureDetector(
          onTap: null,
          child: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              Text(
                '',
                style: TextStyle(
                  color: Colours.color_99000000,
                  fontSize: 12.0,
                ),
              ),
              loadAssetImage(''),
            ],
          ),
        ),
      ],
    );
  }
}
