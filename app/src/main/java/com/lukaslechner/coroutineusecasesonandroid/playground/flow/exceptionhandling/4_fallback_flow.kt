package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
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
            // position is important in upstream
            .catch { e ->
                println("Handle Exception in 1. terminal catch operator - $e")
                emitAll(fallbackFlow())
            }
            .catch { e-> // collect the new throw for the fallback flow
                println("Handle Exception in 2. terminal catch operator - $e")

            }
            .collect { stock ->
                println("Collected - $stock")
            }
    }
}

private fun stocksFlow() = flow {
    emit("Apple")
    emit("Microsoft")

    throw Exception("Network error")
}

private fun fallbackFlow() = flow {
    emit("Fallback Stock")

    throw Exception("Exception in Fallback Flow")
}