package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesAndroid10
import com.lukaslechner.coroutineusecasesonandroid.utils.MainCoroutineScopeRule
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class Perform2SequentialNetworkRequestsViewModelTest {

    private val receivedUiStates = mutableListOf<UiState>()


    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `should return Success when both network requests are successful`() {
        // GIVEN
        val fakeUsecase2Api = FakeUsecase2Api()
        val viewModel = Perform2SequentialNetworkRequestsViewModel(fakeUsecase2Api)
        observeViewModel(viewModel)
        // WHEN
        viewModel.perform2SequentialNetworkRequest()

        // THEN
        Assert.assertEquals(
            listOf(UiState.Loading, UiState.Success(mockVersionFeaturesAndroid10)),
            receivedUiStates
        )

    }


    private fun observeViewModel(viewModel: Perform2SequentialNetworkRequestsViewModel) {
        viewModel.uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }
}