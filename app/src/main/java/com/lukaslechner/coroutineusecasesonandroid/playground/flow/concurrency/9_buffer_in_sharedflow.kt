package com.lukaslechner.coroutineusecasesonandroid.playground.flow.concurrency

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

suspend fun main(): Unit = coroutineScope {
    // Shared Flows has no capacity for slow collectors  so when they still processing one emission and one more is ready
    // the overflow strategy is suspend which add more time in emission metrics and memory
    // so its important to define a capacity if we know there will be a collector that wil take more time for each collected emission
    val flow = MutableSharedFlow<Int>(
        // replay = 1, if we define replay remember that the "good" or "fast" collector will collect twice each emission which can be not desired
        // onBufferOverflow = BufferOverflow.DROP_OLDEST, it also may fix the time issue, but the collectors will potentially lose emissions.
        extraBufferCapacity = 10 // without capacity the total time can increase until half of a second
    )
    // Buffer in Shared Flows = Replay Cache + Extra Buffer

    // Collector 1
    launch {
        flow.collect {
            println("Collector 1 process $it")
        }
    }

    // Collector 2
    launch {
        flow.collect {
            println("Collector 2 process $it")
            delay(100)
        }
    }

    // Emitter
    launch {
        val timeToEmit = measureTimeMillis {
            repeat(5) {
                flow.emit(it)
                delay(10)
            }
        }
        println(" Time to emit All values:  $timeToEmit ms") // should be a value between 50 and 100 ms
    }
}