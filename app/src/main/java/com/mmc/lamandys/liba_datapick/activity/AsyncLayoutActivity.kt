package com.mmc.lamandys.liba_datapick.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import com.mmc.lamandys.liba_datapick.R

class AsyncLayoutActivity : AppCompatActivity(), View.OnClickListener {

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AsyncLayoutInflater(this).inflate(R.layout.activity_async_layout, null) { view, resid, parent -> initView(view, resid, parent) }
    }

    private fun initView(view: View, resid: Int, parent: ViewGroup?) {
        setContentView(view)

    }

    override fun onClick(v: View?) {

    }
}