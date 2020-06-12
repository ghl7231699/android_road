package com.ghl.kotlin

fun main() {
    println("123".second())
    println(20.dp())
}

fun String.second() = this[1]

fun Int.dp() = this * 10