package com.lukaslechner.coroutineusecasesonandroid.playground.flow.builders

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    // Most common, create a flow from a fixed set of values
    val firstFlow = flowOf<Int>(1)
        .collect {emitedValue ->
            println("firstflow: $emitedValue")
        }

    val secondFlow = flowOf<Int>(1,2,3)

    // extension function on various types to convert them into Flows
    listOf("A","B","C").asFlow().collect { emitedValue ->
        println("asFlow: $emitedValue")
    }

    // builder function to construct arbitrary flows from sequential calls to
    // the emit function
    flow {
        kotlinx.coroutines.delay(2000)
        emit("Value emited after 2000ms")
        emitAll(secondFlow)
    }.collect { emitedValue ->
        println("flow{}: $emitedValue")

    }
}