package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {
    launch {
        stocksFlow()
            .onCompletion { cause ->
                if (cause == null){
                    println("Flow completed successfully!")
                } else {
                    println("Flow completed exceptionally with $cause")
                }
            }
            .onEach { stock ->
                throw Exception("Exception inner collect{}") // will be catched
                println("$stock- Collected and performing mapping, process or something")
            }
            // position is important in upstream
            .catch { e ->
                println("Handle Exception in 1. terminal catch operator - $e")
            }
            .collect { stock ->
                //throw Exception("Exception inner collect{}") will not be catched
                println("Collected - $stock")
            }
    }
}

private fun stocksFlow() = flow {
    emit("Apple")
    emit("Microsoft")

    throw Exception("Network error")
}
