package com.lukaslechner.coroutineusecasesonandroid.playground.flow.concurrency

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

suspend fun main(): Unit = coroutineScope {
    // State Flows indeed is a shared flow with the following parameters:
    /*
         MutableSharedFlow<*>(
          replay = 1,
          onBufferOverflow = BufferOverflow.DROP_OLDEST
         )
     */
    // so the Collector 2 will be able to process only the first emission and the last (due its processing time)
    // as the State Flow drop those values that where not collected (old)
    val flow = MutableStateFlow<Int>(0 )
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
        println(" Time to emit All values:  $timeToEmit ms") // should be a value between 50 and 100 ms ( state flows did need a change in its buffer)
    }
}