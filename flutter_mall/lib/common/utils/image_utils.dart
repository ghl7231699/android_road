import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

/// 加载本地资源图片
Image loadAssetImage(
  String name, {
  double width,
  double height,
  BoxFit fit,
  Color color,
  String format: 'png',
}) {
  return Image.asset(
    Utils.getImgPath(
      name,
      format: format,
    ),
    height: height,
    width: width,
    fit: fit,
    color: color,
  );
}

class Utils {
  static String getImgPath(String name, {String format}) {
    return 'assets/images/$name.$format';
  }
}

/// 加载网络图片
Widget loadNetworkImage(String imageUrl,
    {String placeholder: "none",
    double width,
    double height,
    BoxFit fit: BoxFit.cover}) {
  print(imageUrl);
  return CachedNetworkImage(
    cacheManager: null,
    imageUrl: imageUrl == null ? "" : imageUrl,
    placeholder: (context, url) =>
        loadAssetImage(placeholder, height: height, width: width, fit: fit),
    errorWidget: (context, url, error) =>
        loadAssetImage(placeholder, height: height, width: width, fit: fit),
    width: width,
    height: height,
    fit: fit,
  );
}
