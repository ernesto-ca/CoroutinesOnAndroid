package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase5

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import timber.log.Timber

class NetworkRequestWithTimeoutViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest(timeout: Long) {
        uiState.value = UiState.Loading
        //usingWithTimeout(timeout)
        usingWithTimeoutOrNull(timeout)
    }

    private fun usingWithTimeout(timeout: Long) {
        viewModelScope.launch {
            try {
                val recentAndroidVersions = withTimeout(timeout) {
                    api.getRecentAndroidVersions()
                }
                uiState.value = UiState.Success(recentAndroidVersions)
            } catch (timeoutCancellationException: TimeoutCancellationException) {
                uiState.value = UiState.Error(timeoutCancellationException.message.orEmpty())
            } catch (error: Exception) {
                Timber.e(error)
                uiState.value = UiState.Error(error.message.orEmpty())
            }
        }
    }

    private fun usingWithTimeoutOrNull(timeout: Long) {
        viewModelScope.launch {
            try {
                val recentAndroidVersions = withTimeoutOrNull(timeout) {
                    api.getRecentAndroidVersions()
                }
                if (recentAndroidVersions != null) {
                    uiState.value = UiState.Success(recentAndroidVersions)
                } else {
                    uiState.value = UiState.Error("Network request is null!")
                }
            } catch (error: Exception) {
                Timber.e(error)
                uiState.value = UiState.Error(error.message.orEmpty())
            }
        }
    }

}