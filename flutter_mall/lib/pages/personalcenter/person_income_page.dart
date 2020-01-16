import 'package:flutter/material.dart';
import 'package:flutter_mall/common/utils/image_utils.dart';
import 'package:flutter_mall/res/resources.dart';

///收入明细页面

class PersonIncomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colours.white,
      appBar: AppBar(
        backgroundColor: Colours.white,
        title: Text(
          '收入明细',
          style: TextStyles.textNormalDark16,
        ),
        centerTitle: true,
        elevation: 0.0,
      ),
      body: PersonIncomeBody(),
    );
  }
}

class PersonIncomeBody extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => PersonIncomeBodyState();
}

class PersonIncomeBodyState extends State<PersonIncomeBody> {
  int curPage = 1; //开始页数
  int totalPage; //总页数
  bool loading = true;
  bool failure = false; //请求失败

  ///滑动控制器
  ScrollController _controller = new ScrollController(keepScrollOffset: false);

  @override
  void initState() {
    super.initState();
    _controller.addListener(() {
      ///获得 SrollController 监听控件可以滚动的最大范围
      var maxScroll = _controller.position.maxScrollExtent;

      ///获得当前位置的像素值
      var pixels = _controller.position.pixels;

      ///当前滑动位置到达底部，同时还有更多数据
      if (maxScroll == pixels && curPage < totalPage) {
        ///加载更多
        fetchListData(curPage.toString());
      }
    });
    fetchListData('1');
  }

  void fetchListData(String pageNo) async {
//    try {
//      PersonalIncomeModel _model =
//          await PersonalDao.fetchListData(pageNo: pageNo);
//      if (_model != null) {
//        curPage++;
//        _content = _model.content;
//        totalPage = int.parse(_content?.pageInfo?.ttlPages);
//        setState(() {
//          loading = false;
//          failure = false;
//        });
//      }
//    } catch (e) {
//      print(e);
//      setState(() {
//        loading = false;
//        failure = true;
//      });
//    }
  }

  @override
  Widget build(BuildContext context) {
//    if (loading) {
//      return LoadingView();
//    }
//    if (failure) {
//      return LoadingFailure(
//        callBack: () {
//          setState(() {
//            loading = true;
//            failure = false;
//          });
//          fetchListData('1');
//        },
//      );
//    }
    return Container(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          buildContent(),
          Expanded(
            child: buildList(),
          ),
        ],
      ),
      decoration: BoxDecoration(
        color: Colours.color_fafafa,
        borderRadius: BorderRadius.all(
          Radius.circular(20),
        ),
      ),
    );
  }

  Widget buildContent() => Row(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisSize: MainAxisSize.max,
        children: <Widget>[
          Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              Gaps.vGap30,
              buildTitle(
                'ic_total_income',
                '累积收入',
                '10000+',
              ),
              Gaps.vGap30,
              buildTitle(
                'ic_maked_deal',
                '成交订单数',
                '1000',
              ),
            ],
          ),
          Expanded(
            child: loadAssetImage('bg_oval_person', fit: BoxFit.cover),
          ),
        ],
      );

  Widget buildTitle(String image, String title, String content) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 30),
      alignment: Alignment.centerLeft,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisSize: MainAxisSize.max,
            children: <Widget>[
              Container(
                alignment: Alignment.center,
                child: loadAssetImage(
                  image,
                  width: 16,
                  height: 16,
                ),
              ),
              Gaps.hGap6,
              Container(
                alignment: Alignment.center,
                child: Text(
                  title,
                  style: TextStyle(
                    fontSize: 14.0,
                    color: Colours.black,
                    fontWeight: FontWeight.w500,
                    decoration: TextDecoration.none,
                  ),
                ),
              ),
            ],
          ),
          Gaps.vGap10,
          Container(
            padding: const EdgeInsets.only(left: 22),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisSize: MainAxisSize.max,
              children: <Widget>[
                Text(
                  content,
                  style: TextStyle(
                    fontSize: 26.0,
                    color: Colours.black,
                    fontWeight: FontWeight.w500,
                    decoration: TextDecoration.none,
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget buildList() => ListView.separated(
        shrinkWrap: true,
        padding: EdgeInsets.symmetric(horizontal: 30),
        physics: BouncingScrollPhysics(),
        itemBuilder: (context, index) {
          return buildListItem(index);
        },
        separatorBuilder: (context, index) {
          return Dividers.hDivider1;
        },
        itemCount: 10,
      );

  Widget buildListItem(int index) {
    return Container(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Gaps.vGap20,
          Text(
            '2020010$index',
            style: TextStyle(
              color: Colours.color_ff757575,
              fontSize: 12.0,
              fontWeight: FontWeight.normal,
              decoration: TextDecoration.none,
            ),
          ),
          Gaps.vGap2,
          Row(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              Expanded(
                child: Text(
                  '庄老弟',
                  style: TextStyle(
                    color: Colours.color_212121,
                    fontSize: 16.0,
                    decoration: TextDecoration.none,
                    fontWeight: FontWeight.bold,
                    height: 1.5,
                  ),
                ),
              ),
              Gaps.hGap15,
              Text(
                '+2$index',
                style: TextStyle(
                  color: Colours.color_212121,
                  fontSize: 18.0,
                  decoration: TextDecoration.none,
                  fontWeight: FontWeight.bold,
                  height: 1.5,
                ),
              ),
            ],
          ),
          Gaps.vGap2,
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              Expanded(
                child: Container(
                  alignment: Alignment.centerLeft,
                  child: Text(
                    '预订人：老宋头',
                    style: TextStyle(
                      color: Colours.color_6c6c6c,
                      fontSize: 14.0,
                      decoration: TextDecoration.none,
                      fontWeight: FontWeight.normal,
                    ),
                  ),
                ),
              ),
//              Text(
//                listBean?.commissionState?.isNotEmpty ?? false
//                    ? listBean.commissionState
//                    : '',
//                style: TextStyle(
//                  color: '已打款' != listBean?.commissionState
//                      ? Colours.black
//                      : Colours.color_6c6c6c,
//                  fontSize: 12.0,
//                  decoration: TextDecoration.none,
//                  fontWeight: FontWeight.normal,
//                ),
//              ),
            ],
          ),
          Gaps.vGap20,
        ],
      ),
    );
  }
}
