package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2.rx

import android.annotation.SuppressLint
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class SequentialNetworkRequestsRxViewModel(
    private val mockApi: RxMockApi = mockApi()
) : BaseViewModel<UiState>() {

    private val disposables = CompositeDisposable()
    @SuppressLint("CheckResult")
    fun perform2SequentialNetworkRequest() {
        uiState.value = UiState.Loading
        mockApi.getRecentAndroidVersions()
            .flatMap { androidVersions ->
            val recentVersion = androidVersions.last().apiLevel
            mockApi.getAndroidVersionFeatures(recentVersion)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { featureVersions ->
                    uiState.value = UiState.Success(featureVersions)
                },
                onError = {
                    uiState.value = UiState.Error(it.message.orEmpty())
                }
            ).addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}