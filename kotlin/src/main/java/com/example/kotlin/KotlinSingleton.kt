package com.example.kotlin


fun main() {

//    sequence()
    sequenceExample()
}

fun sequenceExample() {
    // 1. iterable
    val words = "The quick brown fox jumps over the lazy dog".split(" ")
    val map = words.filter { println("filter: $it");it.length > 3 }
            .map { println("length:${it.length}");it.length }
            .take(5)

    println("Lengths of first 4 words longer than 3 chars:")
    println("result:$map")

    // 2. Sequence

    //convert the List to Sequence

    val wordSequence = words.asSequence()

    val take = wordSequence.filter { println("filter: $it");it.length > 3 }
            .map { println("length:${it.length}");it.length }
            .take(5)
    println("Lengths of first 4 words longer than 3 chars:")
    println(take.toList())
}

private fun sequence() {
    //    listOf(1, 2, 3, 4)
//            .asSequence()
//            .map { if (it < 3) it + 2 else 0 }
//            .find { it > 3 }

    val find = listOf(1, 2, 3, 4)
            .asSequence()
            .map { it * it }
            .find { it > 3 }

    println(find)

    val oddNumbersLessThan10 = generateSequence(10) { if (it < 10) it + 1 else null }
    println(oddNumbersLessThan10.count())
    println(oddNumbersLessThan10.take(6).toList())
    println(oddNumbersLessThan10.find { it > 3 })
}

class KotlinSingleton {

    companion object {
        val INSTANCE: KotlinSingleton = KotlinSingleton()
    }

    val s4: S4 = S4().instance!!
}


class S1 {
    //饿汉式
    val instance: S1 = S1()
}

class S2 {
    val instance: S2
        get() = SingletonHolder.instance

    //静态内部类
    private object SingletonHolder {
        internal val instance: S2 = S2()
    }
}


class Singleton {
    //懒汉式
    /**
     * 示意方法，单例可以有自己的操作
     */

    fun singletonOperation() {
        //功能处理

    }

    /**
     * 示意方法，让外部通过这些方法来访问属性的值
     *
     * @return 属性的值
     */
    /**
     * 示意属性，单例可以有自己的属性
     */

    val singletonData: String? = null

    companion object {

        /**
         * 定义一个变量来存储创建好的类实例
         */

        private var uniqueInstance: Singleton? = null//如果没有，就创建一个类实例，并把值赋值给存储类实例的变量

        //如果有值，那就直接使用
        //判断存储实例的变量是否有值

        /**
         * 定义一个方法来为客户端提供类实例
         *
         * @return 一个Singleton的实例
         */
        @get:Synchronized
        val instance: Singleton?
            get() {

                //判断存储实例的变量是否有值

                if (uniqueInstance == null) {
                    //如果没有，就创建一个类实例，并把值赋值给存储类实例的变量
                    uniqueInstance = Singleton()
                }

                //如果有值，那就直接使用


                return uniqueInstance
            }
    }
}

//懒汉式 线程不安全
class S4 {
    var instance: S4? = null
        get() {
            if (field == null) {
                field = S4()
            }
            return field
        }
}

//懒汉式 线程安全
class S5 {
    @get:Synchronized
    var instance: S5? = null
        get() {
            if (field == null) {
                field = S5()
            }
            return field
        }
        private set
}


class S6 {
    //双重校验锁
    var instance: S6? = null
        get() {
            if (field == null) {
                synchronized(S6::class.java) {
                    if (field == null) {
                        field = S6()
                    }
                }
            }
            return field
        }
        private set
}