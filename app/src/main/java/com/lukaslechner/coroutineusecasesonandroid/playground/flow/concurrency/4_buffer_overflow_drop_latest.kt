package com.lukaslechner.coroutineusecasesonandroid.playground.flow.concurrency

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
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
        // by adding this operator the whole flow is running under other coroutine lest call it 'z'
        .buffer(
            capacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST // delete the most recent emission that was not collected
        )

    flow.collect { // this will running in another coroutine 'w'
        println("Collector: Start eating tacos $it")
        delay(300)
        println("Collector: Finished eating taco $it")
    }

    // Without the buffer operator the z and w will be running in the same coroutine which make it sequential,
    // with buffer the coroutine z is concurrent as happening at the same time while the collector still "eating"
}