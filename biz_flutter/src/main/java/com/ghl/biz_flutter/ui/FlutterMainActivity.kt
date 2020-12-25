package com.ghl.biz_flutter.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ghl.biz_flutter.R
import com.ghl.lib_dirty.constants.flutter.ACTIVITY_FLUTTER_MAIN
import com.ghl.biz_flutter.flutter.PageRouter
import com.ghl.router_annotation.Route
import java.lang.ref.WeakReference

const val TAG = "FlutterMainActivity"

@Route(ACTIVITY_FLUTTER_MAIN)
class FlutterMainActivity : AppCompatActivity() {

    companion object {
        lateinit var sRef: WeakReference<FlutterMainActivity>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sRef = WeakReference(this)
        setContentView(R.layout.activity_flutter_main)

        val params = HashMap<String, Any>()

        params["test1"] = "v_test1"
        params["test2"] = "v_test2"

        findViewById<View>(R.id.open_native).setOnClickListener {
            PageRouter.openPageByUrl(this, PageRouter.NATIVE_PAGE_URL, params)
        }

        findViewById<View>(R.id.open_flutter).setOnClickListener {
            PageRouter.openPageByUrl(this, PageRouter.FLUTTER_PAGE_URL, params)
        }

        findViewById<View>(R.id.open_flutter_fragment).setOnClickListener {
            PageRouter.openPageByUrl(this, PageRouter.FLUTTER_FRAGMENT_PAGE_URL, params)
        }
    }
}
