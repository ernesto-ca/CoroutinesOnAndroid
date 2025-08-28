package com.lukaslechner.coroutineusecasesonandroid.playground.flow.concurrency

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow

suspend fun main() = coroutineScope {
    val flow = flow {
        repeat(5) {
            println("Emitter: Start Cooking Tacos $it")
            delay(100)
            println("Emitter: Taco $it ready!")
            emit(it)
        }
    }.conflate() // similar to buffer with capacity of 1, actually in the docs it says:
    /*
       `Note that conflate operator is a shortcut for buffer with capacity of Channel.CONFLATED, which is, in turn,
        a shortcut to a buffer that only keeps the latest element as created by buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST).`
     */

    flow.collect { // this will running in another coroutine thanks to conflate (or buffer)
        println("Collector: Start eating tacos $it")
        delay(300)
        println("Collector: Finished eating taco $it")
    }

}