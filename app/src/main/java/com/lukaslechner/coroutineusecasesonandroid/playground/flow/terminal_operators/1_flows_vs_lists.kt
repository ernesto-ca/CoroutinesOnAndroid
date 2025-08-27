package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operators

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay

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

    // is automatically executed as the build is immediate created.
    val list = buildList {
        add(1)
        println("add 1 to list")

        add(2)
        println("add 2 to list")
    }
}