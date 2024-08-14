package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

val scope = CoroutineScope(Dispatchers.Default)
fun main() = runBlocking {// We need a coroutine scope to start new coroutines

    val job = scope.launch {
        // will throw cancellation exception
        delay(100)
        println("$coroutineContext completed")
    }
    job.invokeOnCompletion {cause: Throwable? ->
        if (cause is CancellationException){
            println("Coroutine was cancelled!")
        }
    }
    delay(50)
    // finish the scope
    onDestroy()
}

fun onDestroy() {
    println("Life-time of scope ends")
    scope.cancel()
}