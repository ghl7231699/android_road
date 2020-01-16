import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

import 'colors.dart';
import 'dimens.dart';

class TextStyles {
  static const TextStyle textMain12 = TextStyle(
    fontSize: Dimens.font_sp12,
    color: Colours.app_main,
  );
  static const TextStyle textMain14 = TextStyle(
    fontSize: Dimens.font_sp14,
    color: Colours.app_main,
  );
  static const TextStyle textNormal12 = TextStyle(
    fontSize: Dimens.font_sp12,
    color: Colours.text_normal,
  );
  static const TextStyle textDark12 = TextStyle(
    fontSize: Dimens.font_sp12,
    color: Colours.text_dark,
  );
  static const TextStyle textDark14 = TextStyle(
    fontSize: Dimens.font_sp14,
    color: Colours.text_dark,
  );
  static const TextStyle textDark16 = TextStyle(
    fontSize: Dimens.font_sp16,
    color: Colours.text_dark,
  );
  static const TextStyle textBoldDark12 = TextStyle(
    fontSize: Dimens.font_sp12,
    color: Colours.black,
    fontWeight: FontWeight.bold,
    decoration: TextDecoration.none,
  );
  static const TextStyle textBoldDark14 = TextStyle(
    fontSize: Dimens.font_sp14,
    color: Colours.black,
    fontWeight: FontWeight.bold,
    decoration: TextDecoration.none,
  );
  static const TextStyle textNormalDark14 = TextStyle(
    fontSize: Dimens.font_sp14,
    color: Colours.black,
    fontWeight: FontWeight.normal,
    decoration: TextDecoration.none,
  );
  static const TextStyle textBoldDark15 = TextStyle(
    fontSize: Dimens.font_sp15,
    color: Colours.text_dark,
    fontWeight: FontWeight.bold,
    decoration: TextDecoration.none,
  );
  static const TextStyle textBoldDark16 = TextStyle(
    fontSize: Dimens.font_sp16,
    color: Colours.text_dark,
    fontWeight: FontWeight.bold,
    decoration: TextDecoration.none,
  );
  static const TextStyle textBoldDarkness16 = TextStyle(
    fontSize: Dimens.font_sp16,
    color: Colours.black,
    fontWeight: FontWeight.bold,
    decoration: TextDecoration.none,
  );
  static const TextStyle textBoldDark18 = TextStyle(
    fontSize: Dimens.font_sp18,
    color: Colours.text_dark,
    fontWeight: FontWeight.bold,
    decoration: TextDecoration.none,
  );
  static const TextStyle textBoldDark24 = TextStyle(
    fontSize: 24.0,
    color: Colours.text_dark,
    fontWeight: FontWeight.bold,
    decoration: TextDecoration.none,
  );
  static const TextStyle textNormalLight32 = TextStyle(
    fontSize: 32.0,
    color: Colours.white,
    fontWeight: FontWeight.normal,
    decoration: TextDecoration.none,
  );

  static const TextStyle textNormalLight12 = TextStyle(
    fontSize: 12.0,
    color: Colours.white,
    fontWeight: FontWeight.normal,
    decoration: TextDecoration.none,
  );
  static const TextStyle textNormalDark11 = TextStyle(
    fontSize: 11.0,
    color: Colours.black,
    fontWeight: FontWeight.normal,
    decoration: TextDecoration.none,
  );
  static const TextStyle textNormalDark12 = TextStyle(
    fontSize: 12.0,
    color: Colours.black,
    fontWeight: FontWeight.normal,
    decoration: TextDecoration.none,
  );
  static const TextStyle textNormalLight14 = TextStyle(
    fontSize: 14.0,
    color: Colours.white,
    fontWeight: FontWeight.normal,
    decoration: TextDecoration.none,
  );
  static const TextStyle textBoldLight14 = TextStyle(
    fontSize: 14.0,
    color: Colours.white,
    fontWeight: FontWeight.bold,
  );
  static const TextStyle textNormalLight13 = TextStyle(
    fontSize: 13.0,
    color: Colours.white,
    fontWeight: FontWeight.normal,
    decoration: TextDecoration.none,
  );
  static const TextStyle textBoldLight18 = TextStyle(
    fontSize: 18.0,
    color: Colours.white,
    fontWeight: FontWeight.bold,
    decoration: TextDecoration.none,
  );
  static const TextStyle textNormalLight16 = TextStyle(
    fontSize: 16.0,
    color: Colours.white,
    fontWeight: FontWeight.normal,
    decoration: TextDecoration.none,
  );

  static const TextStyle textBoldLight16 = TextStyle(
    fontSize: 16.0,
    color: Colours.white,
    fontWeight: FontWeight.bold,
    decoration: TextDecoration.none,
  );

  static const TextStyle textNormalDark16 = TextStyle(
    fontSize: 16.0,
    color: Colours.black,
    fontWeight: FontWeight.w600,
    decoration: TextDecoration.none,
  );
  static const TextStyle textGray10 = TextStyle(
    fontSize: Dimens.font_sp10,
  );
  static const TextStyle textNormalLight10 = TextStyle(
    fontSize: Dimens.font_sp10,
    color: Colours.white,
    fontWeight: FontWeight.normal,
    decoration: TextDecoration.none,
  );
  static const TextStyle textGray12 = TextStyle(
    fontSize: Dimens.font_sp12,
    color: Colours.text_gray,
  );
  static const TextStyle textGray14 = TextStyle(
    fontSize: Dimens.font_sp14,
    color: Colours.text_gray,
  );
  static const TextStyle textGray16 = TextStyle(
    fontSize: Dimens.font_sp16,
    color: Colours.text_gray,
  );
  static const TextStyle textGrayC12 = TextStyle(
    fontSize: Dimens.font_sp12,
    color: Colours.text_gray_c,
  );
  static const TextStyle textGrayC14 = TextStyle(
    fontSize: Dimens.font_sp14,
    color: Colours.text_gray_c,
  );

  // appBar标题样式
  static const TextStyle textAppbarTitle = textBoldDark16;

  // textField的hint样式
  static const TextStyle textInputHint = TextStyle(
    fontSize: Dimens.font_sp14,
    color: Colours.color_9b9b9b,
  );

  // textField的文字样式
  static const TextStyle textInputText = textDark14;
}

/// 间隔
class Gaps {
  /// 水平间隔
  static const Widget hGap2 = SizedBox(width: Dimens.gap_dp2);
  static const Widget hGap4 = SizedBox(width: Dimens.gap_dp4);
  static const Widget hGap5 = SizedBox(width: Dimens.gap_dp5);
  static const Widget hGap6 = SizedBox(width: Dimens.gap_dp6);
  static const Widget hGap7 = SizedBox(width: Dimens.gap_dp7);
  static const Widget hGap8 = SizedBox(width: Dimens.gap_dp8);
  static const Widget hGap10 = SizedBox(width: Dimens.gap_dp10);
  static const Widget hGap11 = SizedBox(width: Dimens.gap_dp11);
  static const Widget hGap12 = SizedBox(width: Dimens.gap_dp12);
  static const Widget hGap14 = SizedBox(width: Dimens.gap_dp14);
  static const Widget hGap15 = SizedBox(width: Dimens.gap_dp15);
  static const Widget hGap16 = SizedBox(width: Dimens.gap_dp16);
  static const Widget hGap19 = SizedBox(width: Dimens.gap_dp19);
  static const Widget hGap20 = SizedBox(width: Dimens.gap_dp20);
  static const Widget hGap22 = SizedBox(width: Dimens.gap_dp22);
  static const Widget hGap57 = SizedBox(width: Dimens.gap_dp57);

  /// 垂直间隔
  static const Widget vGap2 = SizedBox(height: Dimens.gap_dp2);
  static const Widget vGap3 = SizedBox(height: Dimens.gap_dp3);
  static const Widget vGap4 = SizedBox(height: Dimens.gap_dp4);
  static const Widget vGap5 = SizedBox(height: Dimens.gap_dp5);
  static const Widget vGap7 = SizedBox(height: Dimens.gap_dp7);
  static const Widget vGap10 = SizedBox(height: Dimens.gap_dp10);
  static const Widget vGap11 = SizedBox(height: Dimens.gap_dp11);
  static const Widget vGap15 = SizedBox(height: Dimens.gap_dp15);
  static const Widget vGap20 = SizedBox(height: Dimens.gap_dp20);
  static const Widget vGap22 = SizedBox(height: Dimens.gap_dp22);
  static const Widget vGap23 = SizedBox(height: Dimens.gap_dp23);
  static const Widget vGap38 = SizedBox(height: Dimens.gap_dp38);
  static const Widget vGap44 = SizedBox(height: Dimens.gap_dp38);
  static const Widget vGap50 = SizedBox(height: Dimens.gap_dp50);

  static const Widget vGap4l = SizedBox(height: Dimens.gap_dp4);
  static const Widget vGap6 = SizedBox(height: Dimens.gap_dp6);
  static const Widget vGap8 = SizedBox(height: Dimens.gap_dp8);
  static const Widget vGap9 = SizedBox(height: Dimens.gap_dp9);
  static const Widget vGap12 = SizedBox(height: Dimens.gap_dp12);
  static const Widget vGap14 = SizedBox(height: Dimens.gap_dp14);
  static const Widget vGap13 = SizedBox(height: Dimens.gap_dp13);
  static const Widget vGap16 = SizedBox(height: Dimens.gap_dp16);
  static const Widget vGap18 = SizedBox(height: Dimens.gap_dp18);
  static const Widget vGap25 = SizedBox(height: Dimens.gap_dp25);
  static const Widget vGap30 = SizedBox(height: Dimens.gap_dp30);

  static Widget line = Container(height: 0.6, color: Colours.line);
  static const Widget empty = SizedBox();
}

class Dividers {
  static const Widget hDivider1 = Divider(
    height: 0.1,
    color: Colours.color_e0e0e0,
  );
  static const Widget vDivider1 = VerticalDivider(
    width: 0.1,
    color: Colours.color_e0e0e0,
  );

  static const Divider setting_divider = Divider(
    height: 0.1,
    color: Colours.color_e0e0e0,
  );
}
