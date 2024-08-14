package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


fun main() {
    val scope = CoroutineScope(Job())

    scope.launch {
        try {
        delay(200)
        launch {
            // will not be caught
            throw RuntimeException()
        }
        }catch (exception :RuntimeException){
            println("Exception caught!")
        }
    }

    Thread.sleep(1000)
}