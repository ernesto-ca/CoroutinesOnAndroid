package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() = runBlocking{
    println("main starts")
    joinAll(
        async { threadSwitchingCoroutines(1, 500) },
        async { threadSwitchingCoroutines(2, 300) }
    )
    println("main ends")
}

suspend fun threadSwitchingCoroutines(number: Int, delay: Long){
    println("Coroutine $number starts work or ${Thread.currentThread().name}")
    delay(delay)
    withContext(Dispatchers.Default) {
        println("Coroutine $number has finished ${Thread.currentThread().name}")
    }
}