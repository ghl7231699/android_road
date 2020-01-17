import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_mall/blocs/login/authentication_bloc.dart';
import 'package:flutter_mall/blocs/login/authentication_events.dart';
import 'package:flutter_mall/res/resources.dart';

// 引导页
class IntroductionPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _State();
  }
}

class _State extends State<IntroductionPage> {
  PageController _pageController;
  @override
  void initState() {
    super.initState();
    _pageController = PageController();
    _pageController.addListener(() {
      setState(() {});
    });
  }

  @override
  void dispose() {
    _pageController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    var top = MediaQuery.of(context).padding.top;
    return NotificationListener<_NextNotification>(
      onNotification: (notification) {
        return true;
      },
      child: Builder(
        builder: (context) => Scaffold(
          body: Stack(
            children: <Widget>[
              PageView(
                controller: _pageController,
                // 引导页面
                children: <Widget>[
                  Container(color: Colors.red),
                  Container(color: Colors.yellow),
                  Container(color: Colors.blue),
                ],
              ),
              Positioned(
                bottom: 20,
                left: 0,
                right: 0,
                child: _IndicatorRow(3, _currentPage.round()),
              ),
              Positioned(
                right: 10,
                top: top + 5,
                child: GestureDetector(
                  onTap: () => _NextNotification().dispatch(context),
                  child: Container(
                    decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.circular(5),
                      border: Border(
                        bottom: BorderSide(),
                        top: BorderSide(),
                        left: BorderSide(),
                        right: BorderSide(),
                      ),
                    ),
                    padding: EdgeInsets.symmetric(horizontal: 5, vertical: 2),
                    child: Text('skip'),
                  ),
                ),
              )
            ],
          ),
        ),
      ),
    );
  }

  double get _currentPage =>
      _pageController.hasClients ? _pageController.page : 0;
}

class _IndicatorRow extends StatelessWidget {
  final int count;
  final int current;
  const _IndicatorRow(this.count, this.current, {Key key}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: List.generate(count, (i) => _CircleIndicator(i == current)),
    );
  }
}

class _CircleIndicator extends StatelessWidget {
  final bool checked;
  const _CircleIndicator(this.checked, {Key key}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.all(5),
      child: CircleAvatar(
        radius: checked ? 8 : 4,
        foregroundColor: checked ? Colors.black : Colours.app_main,
      ),
    );
  }
}

class _NextNotification extends Notification {}
