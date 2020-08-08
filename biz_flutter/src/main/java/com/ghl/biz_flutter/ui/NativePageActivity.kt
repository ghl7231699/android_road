package com.ghl.biz_flutter.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ghl.biz_flutter.R
import com.ghl.lib_dirty.flutter.PageRouter
import com.ghl.router_annotation.Route
import com.idlefish.flutterboost.interfaces.IFlutterViewContainer
import java.io.Serializable
import java.util.*

@Route("NativePageActivity")
class NativePageActivity : AppCompatActivity(), View.OnClickListener {
    private var mOpenNative: TextView? = null
    private var mOpenFlutter: TextView? = null
    private var mOpenFlutterFragment: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.native_page)
        mOpenNative = findViewById(R.id.open_native)
        mOpenFlutter = findViewById(R.id.open_flutter)
        mOpenFlutterFragment = findViewById(R.id.open_flutter_fragment)
        mOpenNative?.setOnClickListener(this)
        mOpenFlutter?.setOnClickListener(this)
        mOpenFlutterFragment?.setOnClickListener(this)
        intent
    }

    override fun onClick(v: View) {
        val params: MutableMap<String, String?> = HashMap()
        params["test1"] = "v_test1"
        params["test2"] = "v_test2"
        when {
            v === mOpenNative -> {
//            PageRouter.openPageByUrl(this, PageRouter.NATIVE_PAGE_URL, params);
                val urlParams = mutableMapOf<String, Any>()
                val intent = Intent()
                urlParams["native"] = "test native"
                intent.putExtra(IFlutterViewContainer.RESULT_KEY, (urlParams as Serializable))
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            v === mOpenFlutter -> {
                PageRouter.openPageByUrl(this, PageRouter.FLUTTER_PAGE_URL, params)
            }
            v === mOpenFlutterFragment -> {
                PageRouter.openPageByUrl(this, PageRouter.FLUTTER_FRAGMENT_PAGE_URL, params)
            }
        }
    }
}