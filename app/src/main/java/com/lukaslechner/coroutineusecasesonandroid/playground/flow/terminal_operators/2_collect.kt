package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operators

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {
    // will not be executed because is the constructor of a flow it must be collected
    val flow = flow {
        delay(100)

        println("Emitting the first value")
        emit(1)

        delay(100)

        println("Emitting the second value")
        emit(2)
    }

    runBlocking {
        flow.collect { receivedValue ->
            println("Received value: $receivedValue")
        }
    }
}