package com.ghl.base.kotlin

import Lock

/**
 * 泛型
 **/

class This {
    fun doTheStuff() {
    }
}

class That {
    fun doTheStuff() {
    }
}

@Suppress("CAST_NEVER_SUCCEEDS")
fun go(worker: This) {
    worker.doTheStuff()
    val that = That()
    go(that as This)
}

fun doTheStuff() {
    val that = That()
    go(that)
}

fun <T> go(worker: T) {
}

class Box<T>(t: T) {
    var value = t
}

fun main(args: Array<String>) {
    val box = Box<Int>(1)
    var runoob1 = Runoob<String>("I am fine")
    var runoob2 = Runoob("are you ok")

    runoob2 = runoob1
    println(runoob2.foo())

    // 类型擦除
    val arrayList = ArrayList<String>()
    println(arrayList.javaClass)

    inlineFunc()


    val L1 = ArrayList<String>()
    val L2 = ArrayList<Int>()

    println(L1.javaClass == L2.javaClass)

}

fun <T> copyWhenGreater(list: List<T>, threshold: T): List<String>
        where T : CharSequence,
              T : Comparable<T> {
    return list.filter { it > threshold }.map { it.toString() }
}

/******************************协变&&逆变******************************/
//使用out，协变类型参数只能用作输出，可以作为返回值类型但是无法作为入参的类型
class Runoob<out A>(val a: A) {
    fun foo(): A {
        return a
    }
}

//in 使得⼀一个类型参数逆变，逆变类型参数只能⽤用作输入，可以作为入参的类型但是无法作为返回值的类型
class RunoobA<in B>(a: B) {
    fun foo() {
    }
}

/******************************inLine************************************/
// 将内联函数的函数体复制到调用处，减少内存分配（对于函数对象和类）和虚拟调用所引入的运行时间开销
//内联可能导致生成的代码增加；不过如果我们使用得当（即避免内联过大函数），性能上会有所提升，尤其在循环中的"超多态（megamorphic）"调用处
inline fun <T> lock(lock: Lock, body: () -> T): T {
    return body()
}

val getX: () -> String = {
    "你好啊"
}


fun inlineFunc() {
    val lock = Lock()
    val lock1 = lock(lock) {
        10
    }
    println("lock1= $lock1")
    lock.lock()
    try {
        lock.unLock()
    } finally {

    }

    var genericThing = GenericThing<String>(json)
    val castInt = genericThing.castInt<String>()
    println("castInt== $castInt")

}

const val json = "{\"first\":\"\"lili\",\"last\":\"liyang\"}"

class GenericThing<T>(constructorArg: T) {
    // 成员属性
    private val thing: T = constructorArg

    //函数参数
    fun doSomething(thing: T) = println(thing)

    //泛型类型实参
    fun emptyList() = listOf<T>()

    //作为类型形参约束
    //强制类型转换
    fun <U : T> castInt(): T = thing as U
}