class UserAgentUtils {
  /// talent/4.15.0 (iPhone8,1; iOS 11.4.1; Scale/2.00) App/iOS (iPhone; Phone; iOS 11.4.1) AppChannel/talent AppVersion/4.15.0 NetType/Wifi”
  static String uniteUA(
      {String version,
      String channel,
      String phone,
      String os,
      String osName,
      String netState}) {
    return "talent/$version ($phone; $os;Scale/2.00) App/$osName AppChannel/$channel AppVersion/$version NetType/$netState";
  }
}
