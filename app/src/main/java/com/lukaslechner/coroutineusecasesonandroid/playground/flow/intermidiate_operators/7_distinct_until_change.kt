package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermidiate_operators

import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.withIndex

suspend fun main() {
    flowOf(1,2,2,2,3,4,5)
        // Check if the last values is not the same than the new emitted value, if yes
        // will cancel the downstream emission until the value change
        .distinctUntilChanged()
        .collect {collectValue ->
            println(collectValue)
        }

}