package com.ghl.coroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    GlobalScope.launch {// 在后台启动一个新的协程并继续
        delay(1000)// 非阻塞的等待 1 秒钟
        println("World！")// 在延迟后打印输出
    }
    println("Hello")// 协程已在等待时主线程还在继续
    Thread.sleep(2000L)// 阻塞主线程 2 秒钟来保证 JVM 存活
}