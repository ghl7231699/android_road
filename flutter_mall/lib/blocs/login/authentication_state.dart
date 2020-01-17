import 'package:equatable/equatable.dart';

///用户状态
abstract class AuthenticationState extends Equatable {
  AuthenticationState([List props = const <dynamic>[]]) : super();
}

//App刚启动
class AppStartState extends AuthenticationState {
  @override
  String toString() => 'AppStartState';

  @override
  List<Object> get props => null;
}

//引导页
class IntroductionState extends AuthenticationState {
  @override
  String toString() {
    return 'IntroductionState';
  }

  @override
  List<Object> get props => null;
}

///未初始化
class AuthenticationUninitialized extends AuthenticationState {
  @override
  String toString() => 'AuthenticationUninitialized';

  @override
  List<Object> get props => null;
}

///验证通过
class AuthenticationAuthenticated extends AuthenticationState {
  @override
  String toString() => 'AuthenticationAuthenticated';

  @override
  List<Object> get props => null;
}

///等待倒计时结束
class AuthenticationWaitingTime extends AuthenticationState {
  @override
  String toString() => 'AuthenticationWaitingTime';

  @override
  List<Object> get props => null;
}

///验证失败
class AuthenticationFailed extends AuthenticationState {
  @override
  String toString() => 'AuthenticationFailed';

  @override
  List<Object> get props => null;
}

///未登录
class AuthenticationUnauthenticated extends AuthenticationState {
  final int time;

  @override
  String toString() => 'AuthenticationUnauthenticated';

  AuthenticationUnauthenticated({this.time}) : super([DateTime.now()]);

  @override
  List<Object> get props => null;
}

class AuthenticationLoading extends AuthenticationState {
  @override
  String toString() => 'AuthenticationLoading${DateTime.now()}';

  @override
  List<Object> get props => null;
}
