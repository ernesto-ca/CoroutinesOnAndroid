package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


fun main() {
    val scopeJob = Job()
    val scope = CoroutineScope(Dispatchers.Default + scopeJob)

    var childCoroutineJob: Job? = null
    val coroutineJob = scope.launch {
        childCoroutineJob = launch {
            println("Starting child coroutine")
            delay(1000)
        }
        println("Coroutine started")
        delay(1000)
    }

    Thread.sleep(100)

    println("Is childCoroutineJob a child of coroutineJob => ${coroutineJob.children.contains(childCoroutineJob)}")
    println("Is coroutineJob a child of scopeJob => ${scopeJob.children.contains(coroutineJob)}")

}