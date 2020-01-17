import 'package:flutter/material.dart';
import 'dart:math' as math show sin, pi;

class DelayTween extends Tween<double> {
  DelayTween({
    double begin,
    double end,
    this.delay,
  }) : super(begin: begin, end: end);

  final double delay;

  @override
  double lerp(double t) {
    return super.lerp((math.sin((t - delay) * 2 * math.pi) + 1) / 2);
  }

  @override
  double evaluate(Animation<double> animation) => lerp(animation.value);
}

class AngleDelayTween extends Tween<double> {
  AngleDelayTween({
    double begin,
    double end,
    this.delay,
  }) : super(begin: begin, end: end);

  final double delay;

  @override
  double lerp(double t) => super.lerp(math.sin((t - delay) * math.pi * 0.5));

  @override
  double evaluate(Animation<double> animation) => lerp(animation.value);
}


enum SpinKitWaveType { start, end, center }

class SpinKitWave extends StatefulWidget {
  SpinKitWave({
    Key key,
    this.color,
    this.type = SpinKitWaveType.start,
    this.size = 20.0,
    this.itemBuilder,
    this.duration = const Duration(milliseconds: 1200),
  })  : assert(
  !(itemBuilder is IndexedWidgetBuilder && color is Color) &&
      !(itemBuilder == null && color == null),
  'You should specify either a itemBuilder or a color'),
        assert(type != null),
        assert(size != null),
        super(key: key);

  final Color color;
  final double size;
  final SpinKitWaveType type;
  final IndexedWidgetBuilder itemBuilder;
  final Duration duration;

  @override
  _SpinKitWaveState createState() => _SpinKitWaveState();
}

class _SpinKitWaveState extends State<SpinKitWave>
    with SingleTickerProviderStateMixin {
  AnimationController _scaleCtrl;

  @override
  void initState() {
    super.initState();
    _scaleCtrl = AnimationController(vsync: this, duration: widget.duration)
      ..repeat();
  }

  @override
  void dispose() {
    _scaleCtrl.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    List<Widget> _bars;
    if (widget.type == SpinKitWaveType.start) {
      _bars = [
        _bar(0, -1.2),
        _bar(1, -1.1),
        _bar(2, -1.0),
        _bar(3, -.9),
        _bar(4, -.8),
      ];
    } else if (widget.type == SpinKitWaveType.end) {
      _bars = [
        _bar(0, -.8),
        _bar(1, -.9),
        _bar(2, -1.0),
        _bar(3, -1.1),
        _bar(4, -1.2),
      ];
    } else if (widget.type == SpinKitWaveType.center) {
      _bars = [
        _bar(0, -0.75),
        _bar(1, -0.95),
        _bar(2, -1.2),
        _bar(3, -0.95),
        _bar(4, -0.75),
      ];
    }
    return Center(
      child: SizedBox.fromSize(
        size: Size(widget.size * 1.25, widget.size),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: _bars,
        ),
      ),
    );
  }

  Widget _bar(int index, double delay) {
    final _size = widget.size * 0.2;
    return ScaleYWidget(
      scaleY: DelayTween(
        begin: .4,
        end: 1.0,
        delay: delay,
      ).animate(_scaleCtrl),
      child: SizedBox.fromSize(
        size: Size(_size, widget.size),
        child: _itemBuilder(index),
      ),
    );
  }

  Widget _itemBuilder(int index) {
    return widget.itemBuilder != null
        ? widget.itemBuilder(context, index)
        : DecoratedBox(
      decoration: BoxDecoration(
        color: widget.color,
      ),
    );
  }
}

class ScaleYWidget extends AnimatedWidget {
  const ScaleYWidget({
    Key key,
    @required Animation<double> scaleY,
    @required this.child,
    this.alignment = Alignment.center,
  }) : super(key: key, listenable: scaleY);

  final Widget child;
  final Alignment alignment;

  Animation<double> get scaleY => listenable;

  @override
  Widget build(BuildContext context) {
    final double scaleValue = scaleY.value;
    final Matrix4 transform = Matrix4.identity()..scale(1.0, scaleValue, 1.0);
    return Transform(
      transform: transform,
      alignment: alignment,
      child: child,
    );
  }
}

class LoadingPage {
  final BuildContext _context;

  LoadingPage(this._context);

  ///打开loading
  void show({Function onClosed}) {
    showDialog(
      context: _context,
      builder: (context) {
        return Scaffold(
          backgroundColor: Colors.white,
          body: SafeArea(
            child: Stack(
              children: <Widget>[
                Positioned.fill(
                  child: Center(
                    child: SpinKitWave(color: Colors.yellow, type: SpinKitWaveType.start),
                  ),
                ),
              ],
            ),
          ),
        );
      },
    ).then((value) {
      onClosed(value);
    });
  }

  ///关闭loading
  void close() {
    Navigator.of(_context).pop();
  }

}


//class Loading extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return SafeArea(
//      child: Stack(
//        children: <Widget>[
//          Positioned.fill(
//            child: Center(
//              child: SpinKitWave(color: Colors.yellow, type: SpinKitWaveType.start),
//            ),
//          ),
//        ],
//      ),
//    );
//  }
//}


