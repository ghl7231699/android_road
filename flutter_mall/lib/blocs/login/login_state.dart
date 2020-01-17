import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';

///登录状态
abstract class LoginState extends Equatable {
  LoginState([List props = const []]) : super();
}

class LoginInitial extends LoginState {
  @override
  String toString() {
    return 'LoginInitial';
  }

  @override
  List<Object> get props => null;
}

class LoginLoadingDismiss extends LoginState {
  @override
  String toString() {
    return 'LoginLoadingDismiss';
  }

  @override
  List<Object> get props => null;
}

class LoginLoading extends LoginState {
  @override
  String toString() {
    return 'LoginLoading';
  }

  @override
  List<Object> get props => null;
}

class LoginSuccess extends LoginState {
  @override
  String toString() {
    return 'LoginSuccess';
  }

  @override
  List<Object> get props => null;
}

class LoginFailure extends LoginState {
  final String error;

  LoginFailure({@required this.error}) : super([error]);

  @override
  String toString() {
    return 'LoginFailure{error: $error}';
  }

  @override
  List<Object> get props => null;
}

///发送验证码失败
class SendCodeFailure extends LoginState {
  @override
  String toString() {
    return 'SendCodeFailure';
  }

  @override
  List<Object> get props => null;
}

///发送验证码成功
class SendCodeSuccess extends LoginState {
  @override
  String toString() {
    return 'SendCodeSuccess';
  }

  @override
  List<Object> get props => null;
}

///可点击发送验证码
class EnableSendCode extends LoginState {
  @override
  String toString() {
    return 'EnableSendCode';
  }

  @override
  List<Object> get props => null;
}

///不可点击发送验证码
class DisableSendCode extends LoginState {
  @override
  String toString() {
    return 'DisableSendCode';
  }

  @override
  List<Object> get props => null;
}
