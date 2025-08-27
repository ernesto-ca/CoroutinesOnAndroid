package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

fun main() {

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught exception $throwable")
    }
    val scope = CoroutineScope(SupervisorJob() + exceptionHandler)

    scope.launch {
        println("Coroutine 1 starts")
        delay(50)
        println("Coroutine 1 fails")
        throw RuntimeException()
    }

    scope.launch {
        println("Coroutine 2 starts")
        delay(200)
        println("Coroutine 2 completed ")
    }.invokeOnCompletion {
        if (it is CancellationException) {
            println("Coroutine 2 got cancelled!")
        }
    }

    Thread.sleep(1000)
    println("Scope got cancelled: ${!scope.isActive}")
}