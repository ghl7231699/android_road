// Copyright 2019 The Flutter team. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package com.mmc.lamandys.liba_datapick.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mmc.lamandys.liba_datapick.ENGINE_ID
import com.mmc.lamandys.liba_datapick.R
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
