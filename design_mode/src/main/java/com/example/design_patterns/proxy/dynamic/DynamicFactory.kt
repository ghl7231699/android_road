package com.example.design_patterns.proxy.dynamic

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class DynamicFactory : InvocationHandler {

    var factory: Any? = null

    //执行对应方法前增强方法
    private fun doBefore() {
        println("我是执行方法前的")
    }

    //执行对应的方法后增强方法
    private fun doAfter() {
        println("我是执行方法后的")
    }

    fun getProxyInstance(): Any {
        return Proxy.newProxyInstance(factory?.javaClass!!.classLoader, factory?.javaClass!!
                .interfaces, this)
    }

    @Throws(Throwable::class)
    override fun invoke(proxy: Any?, method: Method?, args: Array<Any>?): Any {
        doBefore()
        val result = method?.invoke(factory, args)
        doAfter()
        return (result)!!
    }
}