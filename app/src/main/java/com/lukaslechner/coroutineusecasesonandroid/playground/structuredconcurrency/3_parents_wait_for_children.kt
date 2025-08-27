package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val scope = CoroutineScope(Dispatchers.Default)

    val parentCoroutine = scope.launch {
        launch {
            delay(1000)
            println("Coroutine 1 has completed")
        }
        launch {
            delay(1000)
            println("Coroutine 2 has completed")
        }
    }

    parentCoroutine.join()
    println("parent coroutine has completed")
}