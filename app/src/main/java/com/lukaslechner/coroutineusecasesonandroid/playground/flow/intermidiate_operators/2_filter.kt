package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermidiate_operators

import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapNotNull

suspend fun main() {
    flowOf(1,2,3,4,5)
        // filters the emission values for specific predicate
        .filterIsInstance<Int>()
        .filterNotNull()
        .filter{  it > 1}
        .filterNot{  it > 1} // adding an "!" or negation to the operation result
        .collect {collectValue ->
            println(collectValue)
        }

}