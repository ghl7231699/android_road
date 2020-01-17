import 'package:bloc/bloc.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/painting.dart';
import 'package:flutter_mall/blocs/login/user_repository.dart';
import 'package:meta/meta.dart';

import 'authentication_events.dart';
import 'authentication_state.dart';

///  用户状态Bloc
class AuthenticationBloc
    extends Bloc<AuthenticationEvent, AuthenticationState> {
  final UserRepository userRepository;

  // NetworkImage image;
  ImageProvider image;

  AuthenticationBloc({@required this.userRepository})
      : assert(userRepository != null);

  @override
  AuthenticationState get initialState => AppStartState();

  @override
  Stream<AuthenticationState> mapEventToState(
      AuthenticationEvent event) async* {
    if (event is AppStarted) {
      yield AppStartState();
    }
    if (event is FirstScreenImageLoadedEvent) {
      var isFirstOpen = await UserRepository.isFirstOpened();
      if (isFirstOpen) {
        yield IntroductionState();
      } else {
        event = IntroductionEndEvent();
      }
    }
    if (event is IntroductionEndEvent) {
      ///检测本地是否保存有token及用户信息
      final bool hasToken = await userRepository.hasToken();
      if (hasToken) {
        yield AuthenticationAuthenticated();
      } else {
        yield AuthenticationUninitialized();
      }
    }
    if (event is LoginSucceed) {
      await userRepository.waitingTime(event.seconds);
      yield AuthenticationWaitingTime();
    }
    if (event is LoginFailed) {
      if (event.seconds > 0) {
        await userRepository.waitingTime(event.seconds);
      }
      yield AuthenticationUnauthenticated();
    }
    if (event is LoginOut) {
      yield AuthenticationUnauthenticated();
    }
  }
}
