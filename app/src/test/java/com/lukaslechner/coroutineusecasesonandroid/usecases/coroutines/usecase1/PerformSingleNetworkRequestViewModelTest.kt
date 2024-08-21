package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase1

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.utils.MainCoroutineScopeRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PerformSingleNetworkRequestViewModelTest {
    private val receivedUiStates = mutableListOf<UiState>()

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()


    @Test
    fun `Should return Success when network request is successful`() {
        // Arrange
        val fakeSuccessApi = FakeSuccessApi()
        val viewModel = PerformSingleNetworkRequestViewModel(fakeSuccessApi)
        observeViewModel(viewModel)

        // Act
        viewModel.performSingleNetworkRequest()


        // Assert
        Assert.assertEquals(
            listOf(UiState.Loading, UiState.Success(mockAndroidVersions)),
            receivedUiStates
        )
    }

    @Test
    fun `should return Error when network request fails`() {
        // Arrange
        val fakeApi = FakeErrorApi()
        val viewModel = PerformSingleNetworkRequestViewModel(fakeApi)
        observeViewModel(viewModel)

        // Act
        viewModel.performSingleNetworkRequest()


        // Assert
        Assert.assertEquals(
            listOf(UiState.Loading, UiState.Error("HTTP 500 Response.error()")),
            receivedUiStates
        )
    }

    private fun observeViewModel(viewModel: PerformSingleNetworkRequestViewModel) {
        viewModel.uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }
}