package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermidiate_operators

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.withIndex

suspend fun main() {
    flowOf(1,2,3,4,5)
        // Returns flow that wraps each element into a indexed value
        .withIndex()
        .collect {collectValue ->
            println(collectValue)
        }

}