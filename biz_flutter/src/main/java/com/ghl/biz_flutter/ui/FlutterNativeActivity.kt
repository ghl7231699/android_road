package com.ghl.biz_flutter.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ghl.biz_flutter.R
import com.ghl.lib_dirty.ENGINE_ID
import io.flutter.embedding.android.FlutterActivity

class FlutterNativeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)

        findViewById<View>(R.id.AnBtn).setOnClickListener {
            val intent = FlutterActivity
                    .withCachedEngine(ENGINE_ID)
                    .build(this)
            startActivity(intent)
        }

    }
}
