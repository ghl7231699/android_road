package com.ghl.common.base

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ghl.common.R
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        if (intent != null) {
            val url = intent.getStringExtra(KEY_URL)
            webView.loadUrl(url)
        }

//        with(webView) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                addJavascriptInterface(this, "android")//添加js监听 这样html就能调用客户端
//                webViewClient = mClient
////                setWebChromeClient(mClient)
//            }
//            settings.javaScriptEnabled = true//允许使用js
//        }

        webview_back.setOnClickListener { finish() }
    }

    var mClient: WebViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) { //页面加载完成
            WebView_Progress.visibility = View.GONE
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) { //页面开始加载
            WebView_Progress.visibility = View.VISIBLE
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            Log.i("ansen", "拦截url:$url")
            if (url == "http://www.google.com/") {
                Toast.makeText(this@WebViewActivity, "国内不能访问google,拦截该url", Toast.LENGTH_LONG).show()
                return true //表示我已经处理过了
            }
            return super.shouldOverrideUrlLoading(view, url)
        }
    }

    companion object {
        const val KEY_URL = "url"
        const val KEY_TITLE = "title"
    }
}