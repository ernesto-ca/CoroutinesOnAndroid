package com.lukaslechner.coroutineusecasesonandroid.playground.coroutinebuilders

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> { //coroutine 1
    // if we set "start = CoroutineStart.LAZY" for launch options
    // we will need to start the job later by using .start()
    val job = launch() { // coroutine 2
        val res = networkRequest()
        println(res + " received")
    }
    job.join() // suspend the coroutine 1 (runBlocking) and wait until job finish
    println("end of the runBlocking")
}

suspend fun networkRequest(): String {
    delay(500)
    return "Response"
}