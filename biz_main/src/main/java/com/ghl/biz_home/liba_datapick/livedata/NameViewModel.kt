package com.ghl.biz_home.liba_datapick.livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NameViewModel : ViewModel() {
    val sexData = MutableLiveData<String>()
    val ageData = MutableLiveData<Int>()
    val nameData = MutableLiveData<String>()
    val currentName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}