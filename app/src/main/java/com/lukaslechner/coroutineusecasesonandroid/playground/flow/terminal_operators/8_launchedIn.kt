package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operators

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

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

    val scope = CoroutineScope(EmptyCoroutineContext)
    flow
        .onEach { println("Received $it in launchIn") }
        .launchIn(scope)
    /**
     * launchIn is just the same to :
     *     scope.launch {
     *         flow.collect {...}
     *     }
     *  launchedIn can be executed in the same coroutine several times and it will execute
     *  the code async because it just suspend its coroutine where is launched.
     */


    Thread.sleep(1000)
}