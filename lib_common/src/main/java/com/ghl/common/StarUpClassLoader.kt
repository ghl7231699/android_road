package com.ghl.common

import dalvik.system.PathClassLoader

class StarUpClassLoader(dexPath: String?, parent: ClassLoader?) : PathClassLoader(dexPath, parent) {

    override fun findClass(name: String?): Class<*> {

        return super.findClass(name)
    }
}