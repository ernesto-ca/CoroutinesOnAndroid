package com.lukaslechner.coroutineusecasesonandroid.playground.flow.basics

import com.lukaslechner.coroutineusecasesonandroid.playground.utils.printWithTimePassed
import java.math.BigInteger

fun main() {
    // get all the elements of factorial at once so then there is no
    // change in the time
    val startTime = System.currentTimeMillis()
     calculateFactorialOf(5).forEach {
        printWithTimePassed(it, startTime)
    }
}

private fun calculateFactorialOf(number: Int): List<BigInteger> = buildList {
    var factorial = BigInteger.ONE
    for (i in 1..number) {
        Thread.sleep(10)
        factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        add(factorial)
    }
}