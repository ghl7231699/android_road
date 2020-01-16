//import 'dart:io';
//
//import 'package:cached_network_image/cached_network_image.dart';
//import 'package:flutter/material.dart';
//import 'package:flutter/services.dart';
//
//class PersonInfoPage extends StatefulWidget {
//  @override
//  State<StatefulWidget> createState() {
//    return _PersonInfoState();
//  }
//}
//
//class _PersonInfoState extends State<PersonInfoPage>
//    with WidgetsBindingObserver {
//  final TextEditingController _controllerNickname = TextEditingController();
//  final TextEditingController _controllerRedbook = TextEditingController();
//  final TextEditingController _controllerTikTok = TextEditingController();
//  final TextEditingController _controllerDesc = TextEditingController();
//  final List<TextInputFormatter> _formattersNickname = [
//    LengthLimitingTextInputFormatter(20)
//  ];
//  final List<TextInputFormatter> _formattersRedbook = [
//    LengthLimitingTextInputFormatter(15),
//    WhitelistingTextInputFormatter(RegExp(r'[\d\w_]*'))
//  ];
//  final List<TextInputFormatter> _formattersDouyin = [
//    LengthLimitingTextInputFormatter(16),
//    WhitelistingTextInputFormatter(RegExp(r'[\d\w_]*'))
//  ];
//  final List<TextInputFormatter> _formattersDesc = [
//    LengthLimitingTextInputFormatter(40)
//  ];
//  bool _loading = false;
//  bool _haveChanged = false;
//  String _headImage;
//
//  void _setLoading(bool loading) {
//    setState(() {
//      _loading = loading;
//    });
//  }
//
//  @override
//  void initState() {
//    super.initState();
//    _network();
//  }
//
//  Future _network() async {
//    if (_loading) return;
//    _setLoading(true);
//    var info = await PersonalDao.getPersonInfo()
//        .whenComplete(() => _setLoading(false));
//    setState(() {
//      _personInfo = info;
//      _controllerNickname.text = _personInfo?.nickName;
//      _controllerRedbook.text = _personInfo?.redOpenId;
//      _controllerTikTok.text = _personInfo?.tiktokOpenId;
//      _controllerDesc.text = _personInfo?.introduction;
//      _headImage = _personInfo?.headImage;
//    });
//  }
//
//  @override
//  Widget build(BuildContext context) {
//    return WillPopScope(
//      onWillPop: () async {
//        Navigator.pop(context, _haveChanged);
//        return false;
//      },
//      child: GestureDetector(
//        onTap: () {
//          FocusScope.of(context).requestFocus(FocusNode());
//        },
//        child: Scaffold(
//          resizeToAvoidBottomInset: false,
//          appBar: AppBar(
//            backgroundColor: Colours.white,
//            title: Text(
//              '个人信息',
//              style: TextStyles.textAppbarTitle,
//            ),
//            centerTitle: true,
//            elevation: 0,
//          ),
//          body: _loading
//              ? LoadingView()
//              : Container(
//                  color: Colors.white,
//                  child: Column(
//                    children: <Widget>[
//                      Container(
//                        height: 1.0,
//                        color: Colours.color_f2f2f2,
//                      ),
//                      Expanded(
//                        child: ListView(
//                          padding: EdgeInsets.symmetric(horizontal: 20),
//                          children: <Widget>[]
//                            ..addAll(_header(
//                              // _personInfo?.headImage,
//                              _headImage,
//                              // _uploadImage,
//                            ))
//                            ..addAll(_item('编辑昵称', '专业的名字会提升信任感，如：成都旅行达人小惠惠',
//                                _controllerNickname, _formattersNickname))
//                            ..addAll(_item('小红书号', '补充社交账号将有益于打造个人ip',
//                                _controllerRedbook, _formattersRedbook))
//                            ..addAll(_item('抖音账号', '补充社交账号将有益于打造个人ip',
//                                _controllerTikTok, _formattersDouyin))
//                            ..addAll(_item('个人介绍', '例如“背包客”、“探店达人”',
//                                _controllerDesc, _formattersDesc)),
//                        ),
//                      ),
//                      Container(
//                        height: 45,
//                        width: double.infinity,
//                        margin: EdgeInsets.fromLTRB(20, 0, 20, 10),
//                        child: FlatButton(
//                          onPressed: _submit,
//                          color: Colours.app_main,
//                          disabledColor: Colors.grey,
//                          shape: StadiumBorder(),
//                          child: Text(
//                            '保存',
//                            style: TextStyle(
//                              fontSize: 16.0,
//                              fontWeight: FontWeight.bold,
//                            ),
//                          ),
//                        ),
//                      ),
//                    ],
//                  ),
//                ),
//        ),
//      ),
//    );
//  }
//
//  void _preview(String imageUrl) {
//    if (imageUrl == null) return;
//    GalleryPhotoViewWrapper.showGallery(context, 1, [imageUrl]);
//  }
//
//// 上传图片
//  Future _uploadImage() async {
//    File file;
//    if (Platform.isAndroid) {
//      var files = await Routes.navigate('/album', params: {"maxSize": 1});
//      if (files == null) return;
//      if (files is List<File> && files.isNotEmpty) {
//        file = files[0];
//      }
//    } else {
//      file = await ImagePicker.pickImage(source: ImageSource.gallery);
//    }
//    // _setLoading(true);
//    var result = await PersonalDao.uploadHeadImg(file);
//    if (result != null) {
//      // _network();
//      _haveChanged = true;
//      setState(() {
//        _headImage = result;
//      });
//    }
//  }
//
//  Future _submit() async {
//    if (_controllerRedbook.text?.isNotEmpty ?? false) {
//      if (_controllerRedbook.text.length < 6) {
//        showToast('小红书号必须6-15位');
//        return;
//      }
//    }
//    _setLoading(true);
//    // 返回null表示无需修改
//    String getRealStr(String edit, String origin) =>
//        edit == origin ? null : edit;
//    var nickName = getRealStr(_controllerNickname.text, _personInfo?.nickName);
//    var tikTokOpenId =
//        getRealStr(_controllerTikTok.text, _personInfo?.tiktokOpenId);
//    var redOpenId = getRealStr(_controllerRedbook.text, _personInfo?.redOpenId);
//    var introduction =
//        getRealStr(_controllerDesc.text, _personInfo?.introduction);
//
//    if (nickName == null &&
//        tikTokOpenId == null &&
//        redOpenId == null &&
//        introduction == null) {
//      Navigator.pop(context, _haveChanged);
//      return;
//    }
//
//    var s = await PersonalDao.savePersonInfo(
//      nickname: nickName,
//      tikTokOpenId: tikTokOpenId,
//      redOpenId: redOpenId,
//      introduction: introduction,
//    ).whenComplete(() => _setLoading(false));
//    if (s != null) {
//      showToast(s);
//      // _network();
//      _haveChanged = true;
//      Navigator.pop(context, _haveChanged);
//    }
//  }
//
//  List<Widget> _header(String imageUrl) {
//    var localImage = imageUrl?.isEmpty ?? true;
//    return [
//      GestureDetector(
//        onTap: _uploadImage,
//        onLongPress: localImage ? null : () => _preview(imageUrl),
//        child: Row(
//          children: <Widget>[
//            Expanded(
//              child: Text(
//                '更换头像',
//                style: _titleStyle,
//              ),
//            ),
//            Padding(
//              padding: EdgeInsets.symmetric(vertical: 30),
//              child: ClipOval(
//                child: Container(
//                    width: 70,
//                    height: 70,
//                    child: CachedNetworkImage(
//                      fit: BoxFit.cover,
//                      imageUrl: imageUrl ?? '',
//                      // placeholder: (context, url) =>
//                      //     Image.asset(default_image_placeholder),
//                      errorWidget: (a, b, c) =>
//                          Image.asset(default_image_failed),
//                    )),
//              ),
//            ),
//          ],
//        ),
//      ),
//      Dividers.setting_divider,
//    ];
//  }
//}
//
//TextStyle _titleStyle = TextStyles.textBoldDarkness16;
//
//List<Widget> _item(String title, String hint, TextEditingController controller,
//    List<TextInputFormatter> formatters) {
//  return <Widget>[
//    Padding(
//      child: Text(
//        title,
//        style: _titleStyle,
//      ),
//      padding: EdgeInsets.only(top: 20),
//    ),
//    TextField(
//      controller: controller,
//      style: TextStyles.textDark14,
//      decoration: InputDecoration(
//          hintText: hint,
//          hintStyle: TextStyles.textInputHint,
//          border: InputBorder.none),
//      inputFormatters: formatters,
//    ),
//    Gaps.vGap10,
//    Dividers.setting_divider,
//  ];
//}
