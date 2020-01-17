import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';

///登录事件
abstract class AuthenticationEvent extends Equatable {
  AuthenticationEvent([List props = const []]) : super();
}

class AppStarted extends AuthenticationEvent {
  @override
  String toString() {
    return 'AppStarted';
  }

  @override
  List<Object> get props => null;
}

class FirstScreenImageLoadedEvent extends AuthenticationEvent {
  @override
  String toString() {
    return 'FirstScreenLoaded';
  }

  @override
  // TODO: implement props
  List<Object> get props => null;
}
class IntroductionEndEvent extends AuthenticationEvent{
  @override
  // TODO: implement props
  List<Object> get props => null;

}

///登录验证中
class Logging extends AuthenticationEvent {
  @override
  String toString() {
    return 'Logging';
  }

  @override
  // TODO: implement props
  List<Object> get props => null;
}

///登录
class LoginIn extends AuthenticationEvent {
  final String token;

  LoginIn({@required this.token}) : super([token]);

  @override
  String toString() {
    return 'LoginIn{token: $token}';
  }

  @override
  // TODO: implement props
  List<Object> get props => null;
}

///登录成功
class LoginSucceed extends AuthenticationEvent {
  final int seconds;

  LoginSucceed({
    @required this.seconds,
  }) : super([seconds]);

  @override
  String toString() {
    return 'LoginSucceed';
  }

  @override
  // TODO: implement props
  List<Object> get props => null;
}

class LoginOut extends AuthenticationEvent {
  @override
  String toString() {
    return 'LoginOut';
  }

  @override
  // TODO: implement props
  List<Object> get props => null;
}

class LoginFailed extends AuthenticationEvent {
  final int seconds;

  LoginFailed({@required this.seconds}) : super([seconds]);

  @override
  String toString() {
    return 'LoginFailed';
  }

  @override
  // TODO: implement props
  List<Object> get props => null;
}
