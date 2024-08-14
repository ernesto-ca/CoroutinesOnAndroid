package com.lukaslechner.coroutineusecasesonandroid.playground.coroutinebuilders

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()
    val deferred1 = async(start = CoroutineStart.LAZY) {
        val res1 = networkCall(1)
        println("result1 received: $res1 after ${elapsedMillis(startTime)}ms")
        res1
    }
    val deferred2 = async {
        val res2 = networkCall(2)
        println("result2 received: $res2 after ${elapsedMillis(startTime)}ms")
        res2

    }
    deferred1.start()
    val resultList = listOf( deferred1.await(), deferred2.await())
    println("Result list: $resultList after ${elapsedMillis(startTime)}")


}

suspend fun networkCall(number: Int): String{
    delay(500)
    return "Result: $number"
}

fun elapsedMillis(startTime: Long) = System.currentTimeMillis() - startTime