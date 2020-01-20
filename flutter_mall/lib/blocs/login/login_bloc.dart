//import 'package:bloc/bloc.dart';
//import 'package:meta/meta.dart';
//
//import 'login_events.dart';
//import 'login_state.dart';
//import 'user_repository.dart';
//
//class LoginBloc extends Bloc<LoginEvent, LoginState> {
//  final UserRepository userRepository;
//
//  LoginBloc({@required this.userRepository}) : assert(userRepository != null);
//
//  @override
//  LoginState get initialState => LoginInitial();
//
//  @override
//  Stream<LoginState> mapEventToState(LoginEvent event) async* {
//    if (event is LoginButtonPressed) {
//      yield LoginLoading();
////      try {
////        var login = await userRepository.authenticate(
////          phone: event.userName,
////          authCode: event.password,
////        );
////        if (login) {
////          //登录成功
////          yield LoginLoadingDismiss();
////          yield LoginSuccess();
////        } else {
////          yield LoginLoadingDismiss();
////          yield LoginFailure(error: "登录失败");
////        }
////      } catch (error) {
////        yield LoginLoadingDismiss();
////        yield LoginFailure(error: error.toString());
////      }
//    }
//    if (event is LoginFinished) {
//      yield LoginLoadingDismiss();
//    }
//    if (event is SendAuthCode) {
//      yield EnableSendCode();
//    }
//
//    if (event is SendingAuthCode) {
//      yield DisableSendCode();
////      var success = await userRepository.sendAuthCode(phone: event.phone);
////      if (success) {
////        yield SendCodeSuccess();
////      } else {
////        yield SendCodeFailure();
////        yield EnableSendCode();
////      }
//      yield SendCodeSuccess();
//    }
//    if (event is SendTheAuthCode) {
//      yield EnableSendCode();
//    }
//  }
//}
