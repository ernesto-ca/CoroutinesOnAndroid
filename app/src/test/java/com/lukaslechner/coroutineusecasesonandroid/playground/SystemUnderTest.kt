package com.lukaslechner.coroutineusecasesonandroid.playground

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class SystemUnderTest {

    suspend fun functionWithDelay(): Int {
        delay(1000)
        return 42
    }
}

fun CoroutineScope.functionThatStartsNewCoroutine() {
    launch {
        delay(1000)
        println("Coroutine completed")
    }
}

class Testclass {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `functionWithDelay should return 42`() = runTest{
        val realTimeStart = System.currentTimeMillis()
        val virtualTimeStart = currentTime
        val sut = SystemUnderTest()

        val actual = sut.functionWithDelay()

        functionThatStartsNewCoroutine()

        Assert.assertEquals(42, actual)

        val realTimeDuration = System.currentTimeMillis() - realTimeStart
        val virtualTimeDuration = currentTime - virtualTimeStart

        println("test took $realTimeDuration real ms")
        println("test took $virtualTimeDuration virtual ms")

    }

}