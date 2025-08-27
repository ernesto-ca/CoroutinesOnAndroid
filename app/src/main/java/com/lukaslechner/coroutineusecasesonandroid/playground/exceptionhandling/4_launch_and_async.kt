package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

fun main() {

    val coroutineExceptionHandler = CoroutineExceptionHandler{ coroutineContext, throwable ->
        println("Caught $throwable on ${coroutineContext.job}")
    }
    val scope = CoroutineScope(Job() + coroutineExceptionHandler)

    scope.launch {
        // will be caught
        launch {
            delay(200)
            throw  RuntimeException()
        }
    }

    scope.launch {
        // will be caught
        async {
            delay(200)
            throw  RuntimeException()
        }
    }

    scope.async {
        // will not be caught
        async {
            delay(200)
            throw  RuntimeException()
        }
    }

    Thread.sleep(1000)


}