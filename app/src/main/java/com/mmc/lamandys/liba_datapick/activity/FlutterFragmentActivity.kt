package com.mmc.lamandys.liba_datapick.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.mmc.lamandys.liba_datapick.Constanats.CACHED_FLUTTER_ENGINE
import com.mmc.lamandys.liba_datapick.R
import com.mmc.lamandys.liba_datapick.util.StatusBarUtils
import io.flutter.embedding.android.FlutterFragment
import io.flutter.plugin.common.MethodChannel


class FlutterFragmentActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private const val TAG_FLUTTER_FRAGMENT = "flutter_fragment"


    }

    private var mFlutterFragment: FlutterFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtils.transparencyBar(this)
        setContentView(R.layout.activity_flutter_layout)

        initView()

    }

    private fun initView() {
        findViewById<View>(R.id.tv_a).setOnClickListener(this)
        findViewById<View>(R.id.tv_b).setOnClickListener(this)
        findViewById<View>(R.id.tv_c).setOnClickListener(this)
    }

    private fun replaceFragment(type: Int) {

        val fragmentManager: FragmentManager = supportFragmentManager
        mFlutterFragment = fragmentManager.findFragmentByTag(TAG_FLUTTER_FRAGMENT) as FlutterFragment?
        val flutterFragment = when (type) {
            1 ->//新建引擎获取 默认为flutter 初始页面
                FlutterFragment.createDefault()
            2 ->//缓存引擎获取
                FlutterFragment.withCachedEngine(CACHED_FLUTTER_ENGINE).build()
            3 ->  //跳转到指定flutter页面
                FlutterFragment.withNewEngine().initialRoute("/login/index").build()

            else -> FlutterFragment.createDefault()
        }
        mFlutterFragment = flutterFragment

        fragmentManager.beginTransaction()
                .replace(R.id.fl_content, flutterFragment, TAG_FLUTTER_FRAGMENT)
                .commit()

    }

    override fun onClick(v: View?) {

        when {
            v?.id == R.id.tv_a -> replaceFragment(1)
            v?.id == R.id.tv_b -> replaceFragment(2)
            v?.id == R.id.tv_c -> replaceFragment(3)
            else -> replaceFragment(1)
        }

    }

    override fun onPostResume() {
        super.onPostResume()
        mFlutterFragment?.onPostResume()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { mFlutterFragment!!.onNewIntent(it) }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mFlutterFragment!!.onBackPressed()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mFlutterFragment!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        mFlutterFragment!!.onUserLeaveHint()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        mFlutterFragment!!.onTrimMemory(level)
    }


    /**
     * 废弃的方法
     */
    private fun initViewDep() {
        //通过FlutterView引入Flutter编写的页面
//        val flutterView: View = Flutter.createView(
//                this,
//                lifecycle,
//                "route1"
//        )
//        val layout = FrameLayout.LayoutParams(600, 800)
//        layout.leftMargin = 100
//        layout.topMargin = 200
//        addContentView(flutterView, layout)
//        // fragment
//        val tx: FragmentTransaction = supportFragmentManager.beginTransaction()
//        tx.replace(R.id.someContainer, Flutter.createFragment("route1"))
//        tx.commit()

//        mFrameLayout = findViewById(R.id.fl_content)
//        //创建一个 FlutterView 就可以了，这个时候还不会渲染。
//        val flutterView = FlutterView(this)
//        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
//        mFrameLayout?.addView(flutterView, layoutParams)
//        //调用以下代码 才会渲染
//        flutterView.attachToFlutterEngine(flutterEngine)
    }

}

