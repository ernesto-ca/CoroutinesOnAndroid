package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase14

import com.lukaslechner.coroutineusecasesonandroid.utils.ReplaceMainDispatcherRule
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AndroidVersionRepositoryTest {
    @get:Rule
    val replaceMainDispatcherRule = ReplaceMainDispatcherRule()


    @Test
    fun `loadRecentAndroidVersions() should continue to load and store android versions when calling scope gets cancelled`() =
        runTest {
            // GIVEN
            val fakeDatabase = FakeDatabase()
            val fakeApi = FakeApi()
            val applicationScope = this

            val repository = AndroidVersionRepository(
                database = fakeDatabase,
                scope = applicationScope,
                api = fakeApi
            )

            // WHEN
            // Sharing the testScheduler with the applicationScope is important!
            val viewModelScope = TestScope(this.testScheduler)
            val job = viewModelScope.launch {
                repository.loadAndStoreRemoteAndroidVersions()
                // --- middle point (metaphoric)
                println("Should never be printed (job got cancelled)")
            }

            // execute coroutine until delay(1) in the fakeApi
            applicationScope.runCurrent()

            // THEN
            // Check if nothing is inserted into the db before we cancel the scope
            assertEquals(false, fakeDatabase.insertedIntoDb)

            // Cancel the scope and check if it was indeed cancelled
            viewModelScope.cancel()
            assertEquals(true, job.isCancelled)

            // continue coroutine execution after delay(1) in the fakeApi
            applicationScope.advanceTimeBy(1)
            applicationScope.runCurrent()

            Assert.assertTrue(fakeDatabase.insertedIntoDb)
        }
}