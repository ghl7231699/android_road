package com.ghl.kotlin

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 *  协程
 */
fun main() {
//    runAsync()

//    runAsync(1)

//    main1()

//    job()

//    launch()

//    coroutineScope()

//    runs()

//    runA()

//    cancel()

    more()
}

private fun runAsync() {
    GlobalScope.launch {
        //在后台启动一个新的携程并继续
        delay(1000L)//非阻塞的等待1s，默认为毫秒
        println("hello,kotlin")
    }

    println("Hi,")
    Thread.sleep(2000L)//阻塞主线程2s钟来保证JVM存活
}

private fun runAsync(i: Int) {
    GlobalScope.launch {
        delay(1000L)
        println("hello,kotlin")
    }

    println("Hi,")   //主线程中的代码会立即执行
    runBlocking {
        //这个表达式阻塞了主线程
        delay(2000L)// 延迟2s来保证JVM的存活
    }
}

fun main1() = runBlocking<Unit> {
    GlobalScope.launch {
        delay(1000L)
        println("hello,kotlin")
    }

    println("Hi,")   //主线程中的代码会立即执行
    delay(2000L)// 延迟2s来保证JVM的存活
}

fun job() = runBlocking {
    val job = GlobalScope.launch {
        delay(1000L)
        println("hello,kotlin")
    }
    println("Hi,")

    job.join()
}

fun launch() = runBlocking {
    launch {
        delay(1000L)
        println("Kotlin")
    }
    println("Hello,")
}

/**
 * 作用域构建器
 */
fun coroutineScope() = runBlocking {
    launch {
        delay(1000L)
        println("Task from runBlocking")
    }

    kotlinx.coroutines.coroutineScope {
        //创建一个携程作用域
        launch {
            doWorld()
        }

        delay(100L)

        println("Task from coroutine scope")//这一行会在内嵌launch之前输出

        println("CoroutineScope is over")//这一行在内嵌launch执行完毕后输出

    }
}

/**
 * 提取函数重构  suspend修饰符  挂起函数
 */
private suspend fun doWorld() {
    delay(500L)
    println("Task from nested launch")
}

private suspend fun doWorld(i: Int) {
    delay(500L)
    println("我是第" + i + "个")
}

fun runs() = runBlocking {
    repeat(10000) {
        launch { doWorld(it) }
    }
}

/**
 * 在 GlobalScope 中启动的活动协程并不会使进程保活。它们就像守护线程。
 */
fun runA() = runBlocking {
    repeat(10) { i ->
        println("I'm sleeping $i")

        delay(200L)
    }
    delay(2000L)
}


fun cancel() = runBlocking {

    val job = launch {
        repeat(100) { i ->
            println("job： I'm sleeping $i"
            )
            delay(100L)
        }
    }

    delay(1300L)
    println("main: I'm tired of waiting")

    job.cancel()//取消该作业

    job.join()//等待作业执行结束

    println("main: Now I can quit.")
}


fun more() {
    runBlocking {
        //        repeat(100_000) {
//            // launch a lot of coroutines
//            launch {
//                delay(1000L)
//                print(".")
//            }
//        }
        GlobalScope.launch {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        }
        delay(1300L) // just quit after delay
    }
}