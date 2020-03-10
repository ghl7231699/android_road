package com.mmc.lamandys.liba_datapick.livedata

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mmc.lamandys.liba_datapick.R

class NameActivity : AppCompatActivity() {
    private lateinit var model: NameViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)

        var nameTv = findViewById<TextView>(R.id.name_tv)

        model = ViewModelProviders.of(this).get(NameViewModel::class.java)

        //Create the observer which updates the UI
        model.currentName.observe(this, Observer<String> { newName ->
            nameTv.text = newName
        })
    }
}