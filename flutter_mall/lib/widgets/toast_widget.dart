import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

class ShowToast {
  showToast(String message) {
    Fluttertoast.showToast(
      msg: message,
      toastLength: Toast.LENGTH_SHORT,
      gravity: ToastGravity.CENTER,
      fontSize: 15,
      textColor: Colors.white,
      backgroundColor: Colors.black.withOpacity(0.8),
      timeInSecForIos: 2,
    );
  }
}

void showToast(String message) {
  Fluttertoast.showToast(
      msg: message,
      toastLength: Toast.LENGTH_SHORT,
      gravity: ToastGravity.CENTER,
      fontSize: 15,
      textColor: Colors.white,
      backgroundColor: Colors.black.withOpacity(0.8),
      timeInSecForIos: 2);
}
