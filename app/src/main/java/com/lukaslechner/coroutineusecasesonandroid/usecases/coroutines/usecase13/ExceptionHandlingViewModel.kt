package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase13

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.*
import retrofit2.HttpException
import timber.log.Timber

class ExceptionHandlingViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun handleExceptionWithTryCatch() {
        uiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                api.getAndroidVersionFeatures(27)
            } catch (exception: Exception) {
                if (exception is HttpException) {
                    uiState.value = UiState.Error("Network Error: $exception")
                } else {
                    uiState.value = UiState.Error("Something went wrong: $exception")
                }
            }
        }

    }

    fun handleWithCoroutineExceptionHandler() {
        uiState.value = UiState.Loading

        val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            uiState.value = UiState.Error("Something went wrong: ${throwable.message}")
        }
        viewModelScope.launch(coroutineExceptionHandler) {
            val response = api.getAndroidVersionFeatures(27)
            uiState.value = UiState.Success(listOf(response))
        }
    }

    fun showResultsEvenIfChildCoroutineFails() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            supervisorScope {
                val oreoDeferredFeatures = async {
                    api.getAndroidVersionFeatures(28)
                }

                val pieDeferredFeatures = async {
                    api.getAndroidVersionFeatures(27)
                }

                val android10DeferredFeatures = async {
                    api.getAndroidVersionFeatures(29)
                }

                val versionFeatures =
                    listOf(
                        oreoDeferredFeatures,
                        pieDeferredFeatures,
                        android10DeferredFeatures
                    ).mapNotNull {
                        try {
                            it.await()
                        } catch (e: Exception) {
                            if (e is CancellationException) {
                                // when coroutine gets cancelled
                                // it completes immediately with cancellation exception
                                // no additional operations are executed afterwards
                                throw e
                            }
                            Timber.e("Error loading feature data!")
                            null
                        }
                    }
                uiState.value = UiState.Success(versionFeatures)
            }

        }
    }
}