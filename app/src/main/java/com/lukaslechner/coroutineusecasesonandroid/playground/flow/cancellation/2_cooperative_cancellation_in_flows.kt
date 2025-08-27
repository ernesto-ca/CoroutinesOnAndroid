package com.lukaslechner.coroutineusecasesonandroid.playground.flow.cancellation

import android.accounts.NetworkErrorException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch
import java.math.BigInteger

suspend fun main(): Unit = coroutineScope {
    launch {
        intFlow()
            .onCompletion { throwable ->
                if (throwable is CancellationException) {
                    println("Flow got cancelled")
                }
            }
            .collect {
                println("Collected $it")

                if (it == 2) {
                    cancel()
                }
            }
    }
}

private fun intFlow() = flow {
    emit(1)
    emit(2)

   // currentCoroutineContext().ensureActive() // with this line we check if the flow is still active

    // if we don add the ensure this heavy calculation wil continue becasue despite the flow was cancelled it will not emit values but will finish all the code until reach the final line
    println("Start Calculation")
    calculateFactorialOf(1_000)
    println("Calculation cancelled")
    emit(3)
}

private suspend fun  calculateFactorialOf(number: Int): BigInteger = coroutineScope {
    var factorial = BigInteger.ONE
    for(i in 1 .. number) {
        factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        ensureActive() // check the coroutine still active ( if the caller got cancelled (parent) this will be cancelled as well)
    }
    factorial
}