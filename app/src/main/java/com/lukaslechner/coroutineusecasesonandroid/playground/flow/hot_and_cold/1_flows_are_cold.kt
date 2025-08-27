package com.lukaslechner.coroutineusecasesonandroid.playground.flow.hot_and_cold

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

/**
 * Cold flows characteristics:
 *  - If there is no collector, the code inner the flow will be never executed
 *  - Became inactive on cancellation of the collecting coroutine
 *  - Emit individual emissions to every collector
 */
fun coldFlow() = flow {
    println("Emitting 1")
    emit(1)

    delay(1000)
    println("Emitting 2")
    emit(2)

    delay(1000)
    println("Emitting 3")
    emit(3)
}

suspend fun main(): Unit = coroutineScope {
    //cancellationInactiveFlow()
    multipleCollectorsGetItsOwnStream()
}

suspend fun cancellationInactiveFlow() = coroutineScope {
    val job = launch {
        coldFlow()
            .collect {
                println("collector 1 collects: $it")
            }
    }


    delay((1500))
    job.cancel() // flow got inactive
}

suspend fun multipleCollectorsGetItsOwnStream() = coroutineScope {
    launch {
        coldFlow()
            .collect {
                println("collector 1 collects: $it")
            }
    }
    launch {
        coldFlow()
            .collect {
                println("collector 2 collects: $it")
            }
    }
}