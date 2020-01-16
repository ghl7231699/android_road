import 'dart:collection';
import 'dart:convert';
import 'package:crypto/crypto.dart';

String generateSign(String path, Map<String, dynamic> params) {
  var sign = path;
  var signParams = SplayTreeMap.from(params);
  signParams.forEach((key, value) {
    sign += "|$key|${value.toString()}";
  });
  sign += "|2019v001";
  int signCnt = Uri.encodeComponent(sign).length % 5 + 2;
  for (int i = 0; i < signCnt; i++) {
    sign = md5.convert(Utf8Encoder().convert(sign)).toString();
  }
  return "s1_$sign";
}
