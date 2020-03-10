/**
 * 内联函数的使用
 */
fun main(args: Array<String>) {
    run()
    run2()
    apply()
    let()
    also()
    with()
}

// 功能：调用run函数块。返回值为函数块最后一行，或者指定return表达式。
fun run() {
    val a = run {
        println("run")
        return@run 10
    }
    println(a)
}

//功能：调用某对象的run函数，在函数块内可以通过 this 指代该对象。返回值为函数块的最后一行或指定return表达式。
fun run2() {
    val a = "string".run {
        println(this)
        11
    }
    println(a)
}

//功能：调用某对象的apply函数，在函数块内可以通过 this 指代该对象。返回值为该对象自己。
fun apply() {
    val apply = "apply".apply {
        println(this)
    }
    println(apply)
}

//调用某对象的let函数，则该对象为函数的参数。在函数块内可以通过 it 指代该对象。返回值为函数块的最后一行或指定return表达式。

fun let() {
    val let = "let".let {
        println(it)
        "return let"
    }
    println(let)
}

//功能：调用某对象的also函数，则该对象为函数的参数。在函数块内可以通过 it 指代该对象。返回值为该对象自己。
fun also() {
    val also = "also".also {
        println("it is $it")
    }
    println(also)
}

// with函数和前面的几个函数使用方式略有不同，因为它不是以扩展的形式存在的。它是将某对象作为函数的参数，在函数块内可以通过 this 指代该对象。返回值为函数块的最后一行或指定return表达式。
fun with() {
    val with = with("with") {
        println("inner is $this")
        1
    }
    println(with)
}