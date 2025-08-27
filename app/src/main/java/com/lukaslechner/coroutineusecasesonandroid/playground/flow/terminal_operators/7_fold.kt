package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operators

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
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
        val item = flow.fold(5) { accumulator, emittedItem ->
            // the accumulator is the initial value
            accumulator + emittedItem
        }
        println("The sum is: $item")
    }
}