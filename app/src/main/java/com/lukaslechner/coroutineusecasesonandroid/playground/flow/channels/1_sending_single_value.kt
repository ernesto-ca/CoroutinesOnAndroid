package com.lukaslechner.coroutineusecasesonandroid.playground.flow.channels

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {
    val deferred = async {
        delay(100)
        10
    }

    launch {
        val  result = deferred.await()
        val list = listOf<Int>(1,2,3)
        println(
            f(
                c = 0,
                list,
                list.size
            )
        )
        println(result)
    }
}

fun f(c: Int, arr: List<Int>, n: Int): Int{

    if (c == n - 1) {
        return arr[c]
    }

    if(c==0){
        return arr[c] + f(c + 1, arr, n) /2
    } else {
        return arr[c]  + f(c + 1, arr, n)
    }

}