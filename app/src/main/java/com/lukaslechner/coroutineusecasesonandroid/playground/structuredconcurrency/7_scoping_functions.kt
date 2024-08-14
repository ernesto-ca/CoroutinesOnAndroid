package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

fun main() {
    val scope = CoroutineScope(Job())
    scope.launch {
        doSomeTasks()

        launch {
            println("Starting Task 3")
            delay(300)
            println("Task 3 completed")
        }
    }
    Thread.sleep(1000)
}

// If all tasks are important and if ones fails all the other should be cancelled so then use:
// As well as is important to propagate upwards the exception.
suspend fun doSomeTasks() = coroutineScope {
    launch {
        println("Starting Task 1")
        delay(100)
        println("Task 1 completed")
    }
    launch {
        println("Starting Task 2")
        delay(200)
        println("Task 2 completed")
    }
}


// If all tasks are independent or not necessary to be linked each other then use:
// As well as the exception will not cancel the other coroutines, it must be handled inner the function
// and that exception(s) will not be propagated upwards
suspend fun doSomeTasksSupervisor() = supervisorScope {
    launch {
        println("Starting Task 1")
        delay(100)
        println("Task 1 completed")
    }
    launch {
        println("Starting Task 2")
        delay(200)
        println("Task 2 completed")
    }
}