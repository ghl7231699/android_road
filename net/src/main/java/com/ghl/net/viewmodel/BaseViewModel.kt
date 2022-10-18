package com.ghl.net.viewmodel

import androidx.lifecycle.ViewModel
import com.ghl.net.repository.BaseRepository
import com.ghl.net.tools.TUtil

abstract class BaseViewModel<R : BaseRepository<*>> : ViewModel() {
    @JvmField
    protected var mRepository: R

    init {
        mRepository = baseRepository
    }

    private val baseRepository: R
        get() = TUtil.getT(this, 0)
}