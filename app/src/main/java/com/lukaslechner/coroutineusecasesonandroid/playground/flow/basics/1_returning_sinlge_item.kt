package com.lukaslechner.coroutineusecasesonandroid.playground.flow.basics

import java.math.BigInteger

fun main() {
    // first example
    val result =  calculateFactorialOf(5)
    println("Factorial result : $result")
}

private fun calculateFactorialOf(number: Int): BigInteger {
    var factorial = BigInteger.ONE
    for (i in 1 .. number){
        Thread.sleep(10)
        factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
    }
    return factorial
}