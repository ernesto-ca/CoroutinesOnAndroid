package com.lukaslechner.coroutineusecasesonandroid.playground.flow.hot_and_cold

import androidx.annotation.Discouraged
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/** Hot Flows characteristics:
 *  - Are Active regardless of whether are collectors
 *  - Stay Active even when there is no more collector
 *
 */
fun main() {

    val sharedFlow = MutableSharedFlow<Int>()

    val scope = CoroutineScope(Dispatchers.Default)
    emissionsAreShared(scope, sharedFlow)

}

fun emissionsMissed(scope: CoroutineScope, sharedFlow: MutableSharedFlow<Int>)  {
    scope.launch {
        repeat(5) {
            println("SharedFlow emits $it")
            sharedFlow.emit(it)
            delay(200)
        }
    }

    Thread.sleep(500)
    // will lost the values if there is no collectors because it will emit anyway
    scope.launch {
        sharedFlow.collect {
            println("collected $it")
        }
    }
}

fun emissionsAreShared(scope: CoroutineScope, sharedFlow: MutableSharedFlow<Int>)  {
    scope.launch {
        repeat(5) {
            println("SharedFlow emits $it")
            sharedFlow.emit(it)
            delay(200)
        }
    }

    scope.launch {
        sharedFlow.collect {
            println("collected $it in collector 1")
        }
    }

    scope.launch {
        sharedFlow.collect {
            println("collected $it in  collector 2")
        }
    }

    Thread.sleep(1500)
}