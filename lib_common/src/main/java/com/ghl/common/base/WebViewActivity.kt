package com.ghl.common.base

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ghl.common.constants.web.PAGE_WEB_VIEW
import com.ghl.lib_common.R
import com.ghl.router_annotation.Route

@Route(PAGE_WEB_VIEW)
class WebViewActivity : AppCompatActivity() {
    private val webView: WebView by lazy {
        findViewById(R.id.webView)
    }
    private val webViewBack: TextView by lazy {
        findViewById(R.id.webview_back)
    }
    private val progressBar: ProgressBar by lazy {
        findViewById(R.id.WebView_Progress)
    }

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        intent?.run {
            getStringExtra(KEY_URL)?.let { url ->
                webView.loadUrl(url)
            }
        }

//        with(webView) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                addJavascriptInterface(this, "android")//添加js监听 这样html就能调用客户端
//                webViewClient = mClient
////                setWebChromeClient(mClient)
//            }
//            settings.javaScriptEnabled = true//允许使用js
//        }

        webViewBack.setOnClickListener { finish() }
    }

    var mClient: WebViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) { //页面加载完成
            progressBar.visibility = View.GONE
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) { //页面开始加载
            progressBar.visibility = View.VISIBLE
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            Log.i("ansen", "拦截url:$url")
            if (url == "http://www.google.com/") {
                Toast.makeText(this@WebViewActivity, "国内不能访问google,拦截该url", Toast.LENGTH_LONG)
                    .show()
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