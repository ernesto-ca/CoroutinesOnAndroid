package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{
    println("main starts")
    joinAll(
        async { coroutineWithThreadInfo(1, 500) },
        async { coroutineWithThreadInfo(2, 300) },
        async {
            repeat(5) {
                println("other task is working on ${Thread.currentThread().name}")
                delay(100)
            }
        }
    )
    println("main ends")
}

suspend fun suspendingCoroutine(number: Int, delay: Long){
    println("Coroutine $number starts work or ${Thread.currentThread().name}")
    delay(delay)
    println("Coroutine $number has finished ${Thread.currentThread().name}")
}