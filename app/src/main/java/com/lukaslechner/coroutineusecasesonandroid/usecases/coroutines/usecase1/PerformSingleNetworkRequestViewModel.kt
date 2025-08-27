package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import com.lukaslechner.coroutineusecasesonandroid.mock.createMockApi
import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.utils.MockNetworkInterceptor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PerformSingleNetworkRequestViewModel(
    private val mockApi: MockApi = mockApi(),
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    fun performSingleNetworkRequest() {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            try {
                val recentVersions = getRecentAndroidVersions()
                uiState.value = UiState.Success(recentVersions)
            } catch (exception: Exception) {
                uiState.value = UiState.Error("Network Request failed")
            }
        }
    }

    private suspend fun getRecentAndroidVersions() = withContext(ioDispatcher) {
        mockApi.getRecentAndroidVersions()
    }

    fun uiState(): LiveData<UiState> = uiState
    private val uiState: MutableLiveData<UiState> = MutableLiveData()

    companion object {
        fun mockApi() =
            createMockApi(
                MockNetworkInterceptor()
                    .mock(
                        "http://localhost/recent-android-versions",
                        Gson().toJson(mockAndroidVersions),
                        200,
                        1500
                    )
            )
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val recentVersions: List<AndroidVersion>) : UiState()
        data class Error(val message: String) : UiState()
    }
}