import 'package:equatable/equatable.dart';

///登录事件
abstract class LoginEvent extends Equatable {
  LoginEvent([List props = const []]) : super();
}

class LoginButtonPressed extends LoginEvent {
//  final String userName;
//  final String password;
//
//  LoginButtonPressed({@required this.userName, this.password})
//      : super([userName, password]);

  @override
  String toString() {
    return 'LoginButtonPressed{userName';
  }

  @override
  List<Object> get props => null;
}

class LoginInit extends LoginEvent {
  @override
  String toString() {
    return 'LoginInit';
  }

  @override
  List<Object> get props => null;
}

class LoginFinished extends LoginEvent {
  @override
  String toString() {
    return 'LoginFinished';
  }

  @override
  List<Object> get props => null;
}

///初始化状态
class SendAuthCodeInit extends LoginEvent {
  @override
  String toString() {
    return 'SendAuthCodeInit';
  }

  @override
  List<Object> get props => null;
}

///发送验证码
class SendAuthCode extends LoginEvent {
  @override
  String toString() {
    return 'SendAuthCode';
  }

  @override
  List<Object> get props => null;
}

///发送验证码 倒计时状态
class SendingAuthCode extends LoginEvent {
  final String phone;

  SendingAuthCode({this.phone});

  @override
  String toString() {
    return 'SendingAuthCode';
  }

  @override
  List<Object> get props => null;
}

///发送验证码完成
class SendTheAuthCode extends LoginEvent {
  @override
  String toString() {
    return 'SendTheAuthCode';
  }

  @override
  List<Object> get props => null;
}
