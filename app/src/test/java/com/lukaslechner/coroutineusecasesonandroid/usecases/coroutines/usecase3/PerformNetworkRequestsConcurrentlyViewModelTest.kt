package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase3

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesAndroid10
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesOreo
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesPie
import com.lukaslechner.coroutineusecasesonandroid.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest

import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class PerformNetworkRequestsConcurrentlyViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val replaceMainDispatcherRule : ReplaceMainDispatcherRule = ReplaceMainDispatcherRule()

    private val receivedUiState = mutableListOf<UiState>()

    @Test
    fun `performNetworkRequest sequentially should load data sequentially`() = runTest {
        // GIVEN
        val fakeApi = FakeApiUseCase3(responseDelay = 1000L)
        val viewModel = PerformNetworkRequestsConcurrentlyViewModel(fakeApi)
        observeViewModel(viewModel)
        // WHEN
        viewModel.performNetworkRequestsSequentially()
        advanceUntilIdle()
        val forwardedTime = currentTime

        // THEN
        Assert.assertEquals(
            listOf(
                UiState.Loading, UiState.Success(
                    listOf(
                        mockVersionFeaturesOreo,
                        mockVersionFeaturesPie,
                        mockVersionFeaturesAndroid10
                    )
                )
            ),
        receivedUiState
        )
        Assert.assertEquals(3000,  forwardedTime )
    }

    @Test
    fun `performNetworkRequest sequentially should load data concurrently`() = runTest{
        // GIVEN
        val fakeApi = FakeApiUseCase3(responseDelay = 1000L)
        val viewModel = PerformNetworkRequestsConcurrentlyViewModel(fakeApi)
        observeViewModel(viewModel)
        // WHEN
        viewModel.performNetworkRequestsConcurrently()
        advanceUntilIdle()
        val forwardedTime = currentTime


        // THEN
        Assert.assertEquals(
            listOf(
                UiState.Loading, UiState.Success(
                    listOf(
                        mockVersionFeaturesOreo,
                        mockVersionFeaturesPie,
                        mockVersionFeaturesAndroid10
                    )
                )
            ),
            receivedUiState
        )

        Assert.assertEquals(
            1000L,
            forwardedTime
        )

    }

    private fun observeViewModel(viewModel: PerformNetworkRequestsConcurrentlyViewModel) {
        viewModel.uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiState.add(uiState)
            }
        }
    }

}