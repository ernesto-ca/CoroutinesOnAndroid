package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermidiate_operators

import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.takeWhile

suspend fun main() {
    // SIZE LIMITING OPERATOR
    flowOf(1,2,3,4,5)
        // will miss the number of emissions specified
        .drop(3)
        .dropWhile { it < 2 }
        .collect {collectValue ->
            println(collectValue)
        }

}