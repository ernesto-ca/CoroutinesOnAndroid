package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
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
                println("Handle Exception in terminal catch block - $e")
                emit("Default value")
            }
            .map {
                // if some error occurs here the catch will no "Catch it" because of the position
                it
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