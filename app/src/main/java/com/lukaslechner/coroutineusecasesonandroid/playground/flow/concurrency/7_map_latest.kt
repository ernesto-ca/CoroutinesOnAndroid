package com.lukaslechner.coroutineusecasesonandroid.playground.flow.concurrency

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest

suspend fun main() = coroutineScope {
    val flow = flow {
        repeat(5) {
            println("Emitter: Start Cooking Tacos $it")
            delay(100)
            println("Emitter: Taco $it ready!")
            emit(it)
        }
    }.mapLatest { // Useful in situations where we have a slow operation in its block and we again dont care about emissions
        // so everytime there is anre emission in the upstream, its block is cancelled and restarted with a new value
        // simlar to collect latest only the last or most recent emission will be sent
        println("Add salsa to the taco $it")
        delay(200)
        it

    }

    flow.collect {
        println("Collector: Start eating tacos $it")
        delay(300)
        println("Collector: Finished eating taco $it")
    }

}