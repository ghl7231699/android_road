package com.ghl.lib_dirty

import dalvik.system.PathClassLoader

class StarUpClassLoader(dexPath: String?, parent: ClassLoader?) : PathClassLoader(dexPath, parent) {

    override fun findClass(name: String?): Class<*> {

        return super.findClass(name)
    }
}