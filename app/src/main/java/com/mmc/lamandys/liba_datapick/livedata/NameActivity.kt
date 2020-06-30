package com.mmc.lamandys.liba_datapick.livedata

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProviders
import com.ghl.lib_dirty.constants.main.ACTIVITY_LIVE_DATA_VIEW_MODEL
import com.ghl.router_annotation.Route
import com.mmc.lamandys.liba_datapick.R

@Route(ACTIVITY_LIVE_DATA_VIEW_MODEL)
class NameActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var model: NameViewModel
    private var nameTv: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)
        nameTv = findViewById(R.id.name_tv)

        model = ViewModelProviders.of(this).get(NameViewModel::class.java)
        //Create the observer which updates the UI
        model.currentName.observe(this, Observer<String> { newName ->
            nameTv?.text = newName
        })
        model.currentName.postValue("I am the king")

        nameTv?.setOnClickListener(this)
        findViewById<View>(R.id.btn_map).setOnClickListener(this)
        findViewById<View>(R.id.btn_switch_map).setOnClickListener(this)
        findViewById<View>(R.id.btn_switch_change).setOnClickListener(this)

        model.nameData.value = "switchMap"
        model.sexData.value = "男"
    }

    private fun dataMap() {
        val map = Transformations.map(model.ageData) {
            it.toString()
        }
    }

    private fun switchMap() {
        val switchMap = Transformations.switchMap(model.ageData) {
            if (it > 2) {
                model.nameData
            } else {
                model.sexData
            }
        }
        switchMap.observe(this, Observer { newName ->
            nameTv?.text = newName
        })
    }

    private fun change() {
        val nextInt = (0..10).random()
        model.ageData.postValue(nextInt)
        findViewById<Button>(R.id.btn_switch_change).text = "改变后：$nextInt"
    }

    override fun onClick(v: View?) {
        v?.let {
            when (v.id) {
                R.id.btn_map -> dataMap()
                R.id.btn_switch_map -> switchMap()
                R.id.btn_switch_change -> change()
                else -> println("id has not found")
            }
        }
    }
}