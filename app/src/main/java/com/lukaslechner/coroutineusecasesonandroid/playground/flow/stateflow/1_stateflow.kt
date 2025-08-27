package com.lukaslechner.coroutineusecasesonandroid.playground.flow.stateflow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

suspend fun main() {
    // State flows always need a initial value
    val stateFlow = MutableStateFlow(0)

    // And characteristic from state flow is that we can access to the current value directly without needing to start a new collecting routine first
    // access t the value in an easy and non suspending way which is very convenient
    // NOTE:  if we want to get updates we MUST collect it
    println(stateFlow.value)

    // Directly assigning values to immutable state flow from mutable threads is not thread safe
    // avoid stateFlow.value = x that is not thread safe which means it may be different depending the collector thread

    coroutineScope {
        repeat(10_000 ) {
            launch {
                // NO THREAD SAFE:
                // stateFlow.value = stateFlow.value + 1
                // THREAD SAFE:
                stateFlow.update {
                    it + 1
                }
            }
        }
    }
    println(stateFlow.value)
}