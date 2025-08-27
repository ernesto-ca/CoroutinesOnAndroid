package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow

suspend fun main(): Unit = coroutineScope {
    flow {
        try {
            emit(1)
        } catch (e: Exception) {
            println("Catch Exception in flow builder.")
            emit(2) // will crash due its not catch
        }
    }.collect { emittedValue ->
        // this will be catch in the flow builder because its called when we perform the emit() its all about functions invocations
        throw Exception("Exception in collect{}")
    }
}
