package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermidiate_operators

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapNotNull

suspend fun main() {
    flowOf(1,2,3,4,5)
        // converts the emission values for another type object or implements operations with them
        .mapNotNull{  "Emission $it"}
        .collect {collectValue ->
            println(collectValue)
        }

}