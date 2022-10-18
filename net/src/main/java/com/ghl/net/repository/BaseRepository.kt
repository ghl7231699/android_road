package com.ghl.net.repository

import com.ghl.net.api.ApiClient
import com.ghl.net.tools.TUtil

abstract class BaseRepository<S> {
    @JvmField
    protected var service: S = ApiClient.instance.mClient.create(getService())

    protected open fun getService(): Class<S>? {
        return TUtil.getT1(this, 0)
    }

}