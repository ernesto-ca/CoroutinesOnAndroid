package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import android.accounts.NetworkErrorException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {
    launch {
        stocksFlow()
            .catch { e->
                println("Handle Exception in catch() - $e")

            }
            .collect { stock ->
                println("Collected - $stock")
            }
    }
}

private fun stocksFlow() = flow {
    repeat(5) {
        delay(1000)
        if (it < 4) {
            emit("New Stock Data")
        } else {
            throw NetworkErrorException("Network error")
        }
    }
}
//    .retry(retries = 1){ cause ->
//        println("Enter retry() with cause -> $cause")
//        delay(1000) // always wait untuil retry a fow whioc perfmorms api requests
//        cause is RuntimeException
//    }

    .retryWhen { cause, attempt ->
        println("Enter retry() with cause -> $cause")
        delay(1000 * (attempt + 1)) // try again after certain attempt value, example if attempt is 1 this menas it already tried so lets wait 1000 * (1+1) miliseconds to try again
        cause is RuntimeException
    }
