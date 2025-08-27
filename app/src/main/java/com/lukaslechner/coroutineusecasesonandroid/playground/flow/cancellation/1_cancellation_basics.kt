package com.lukaslechner.coroutineusecasesonandroid.playground.flow.cancellation

import android.accounts.NetworkErrorException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch

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
    emit(3)
}
