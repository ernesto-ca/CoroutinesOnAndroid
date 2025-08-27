package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    try {
        //coroutineScope re-throw the exception instead of propagate it up
        // so that, we can handle it with a outer try/catch
        doSomethingSuspend()
    } catch (e: Exception) {
       println("Caught $e")
    }
}

private suspend fun doSomethingSuspend() {
    coroutineScope {
        launch {
            throw RuntimeException()
        }
    }
}