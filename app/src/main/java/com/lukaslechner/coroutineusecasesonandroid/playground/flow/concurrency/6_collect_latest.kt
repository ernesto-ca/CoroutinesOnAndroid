package com.lukaslechner.coroutineusecasesonandroid.playground.flow.concurrency

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

suspend fun main() = coroutineScope {
    val flow = flow {
        repeat(5) {
            println("Emitter: Start Cooking Tacos $it")
            delay(100)
            println("Emitter: Taco $it ready!")
            emit(it)
        }
    }

    // With collectLatest every time upstream emits a new item, the collect latest block is immediately cancelled an restarted
    flow.collectLatest {
        println("Collector: Start eating tacos $it")
        delay(300)
        println("Collector: Finished eating taco $it")
    }

}