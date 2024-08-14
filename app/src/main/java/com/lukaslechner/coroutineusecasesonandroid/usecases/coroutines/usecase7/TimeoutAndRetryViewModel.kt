package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase7

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import timber.log.Timber

class TimeoutAndRetryViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        uiState.value = UiState.Loading
        val numberOfRetries = 2
        val timeout = 1000L

        val oreoFeaturesDeferred = viewModelScope.async {
            retryWithTimeout(numberOfRetries = numberOfRetries, timeout = timeout) {
                api.getAndroidVersionFeatures(27)
            }
        }
        val pieFeaturesDeferred =
            viewModelScope.async {
                retryWithTimeout(numberOfRetries = numberOfRetries, timeout = timeout) {
                    api.getAndroidVersionFeatures(27)
                }
            }
        viewModelScope.launch {
            try {
                val versionFeatures = awaitAll(
                    oreoFeaturesDeferred,
                    pieFeaturesDeferred
                )
                uiState.value = UiState.Success(versionFeatures)
            } catch (error: Exception) {
                Timber.e(error)
                uiState.value = UiState.Error(error.message.orEmpty())
            }
        }
    }

    private suspend fun <T> retryWithTimeout(
        numberOfRetries: Int,
        timeout: Long,
        block: suspend () -> T
    ) = retry(numberOfRetries) {
        withTimeout(timeout) {
            block()
        }
    }

    private suspend fun <T> retry(
        numberOfRetries: Int,
        delayBetweenRetries: Long = 100,
        block: suspend () -> T
    ): T {
        repeat(numberOfRetries) {
            try {
                return block()
            } catch (exception: Exception) {
                Timber.e(exception)
            }
            delay(delayBetweenRetries)
        }
        return block()
    }
}