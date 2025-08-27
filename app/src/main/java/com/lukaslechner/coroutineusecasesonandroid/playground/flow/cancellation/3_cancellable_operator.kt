package com.lukaslechner.coroutineusecasesonandroid.playground.flow.cancellation

import android.accounts.NetworkErrorException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import java.math.BigInteger

suspend fun main(): Unit = coroutineScope {
    launch {
        flowOf(1,2,3) // must check in onEach the state of the coroutine or use cancellable to perform the same check
            .onCompletion { throwable ->
                if (throwable is CancellationException) {
                    println("Flow got cancelled")
                }
            }.cancellable() //  this operator checks as the following code:

//            .onEach {
//                println("Revceive $it in onEach")
//
//                ensureActive()
//                // the ensureActive is similar to have:
////                if (!currentCoroutineContext().job.isActive){
////                    throw CancellationException()
////                }
//            }
            .collect {
                println("Collected $it")

                if (it == 2) {
                    cancel()
                }
            }
    }
}
