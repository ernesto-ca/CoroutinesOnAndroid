package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import java.util.jar.Attributes.Name

fun main() = runBlocking<Unit> {
    try {
        doSomethingSuspend()
    } catch (e: Exception) {
       println("Caught $e")
    }
    doSomethingCatch()
}

// will be handled by the try catch
private suspend fun doSomethingSuspend() {
    supervisorScope {
        async {
            throw RuntimeException()
        }.await()
    }
}

// won't be handled by the try catch due this is a
// independent scope in which we have to handle it
private suspend fun doSomething1Suspend() {
    supervisorScope {
        launch {
            throw RuntimeException()
        }
    }
}

// will be handled by the try/catch because the supervisor
// inherits the coroutine exception handler as it is in the documentation
private fun doSomethingCatch() {
    val exceptionHandler = CoroutineExceptionHandler{coroutineContext, throwable ->
        println("Caught $throwable by CoroutineExceptionHandler")
    }
    val scope = CoroutineScope( Job() + exceptionHandler)
    scope.launch {
        try {
            supervisorScope {
                launch {
                    println("CoroutineExceptionHandler: ${coroutineContext[CoroutineExceptionHandler]}")
                    throw RuntimeException()
                }
            }
        } catch (e: Exception) {
            println("Caught $e by Try/Catch")
        }
    }

    Thread.sleep(1000)
}