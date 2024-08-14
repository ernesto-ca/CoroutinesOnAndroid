package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.launch
import timber.log.Timber

class Perform2SequentialNetworkRequestsViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun perform2SequentialNetworkRequest() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                mockApi.getRecentAndroidVersions().takeIf { it.isNotEmpty() }?.last()?.apiLevel?.let {
                    val getAndroidFeatures = mockApi.getAndroidVersionFeatures(it)
                    uiState.value = UiState.Success(getAndroidFeatures)
                }
            } catch (error: Exception) {
                Timber.e(error)
                uiState.value = UiState.Error("Something went wrong, try again later.")
            }
        }
    }
}