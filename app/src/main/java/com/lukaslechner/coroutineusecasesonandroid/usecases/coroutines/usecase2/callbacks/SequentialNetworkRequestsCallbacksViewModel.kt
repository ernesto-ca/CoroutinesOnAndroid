package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2.callbacks

import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SequentialNetworkRequestsCallbacksViewModel(
    private val mockApi: CallbackMockApi = mockApi()
) : BaseViewModel<UiState>() {

    private var getAndroidVersionsCall: Call<List<AndroidVersion>>? = null
    private var getAndroidFeaturesCall: Call<VersionFeatures>? = null

    fun perform2SequentialNetworkRequest() {
        uiState.value = UiState.Loading
        getAndroidVersionsCall = mockApi.getRecentAndroidVersions()
        getAndroidVersionsCall?.enqueue(object : Callback<List<AndroidVersion>> {
            override fun onResponse(
                call: Call<List<AndroidVersion>>,
                res: Response<List<AndroidVersion>>
            ) {
               if (res.isSuccessful){
                   res.body()?.let { mostRecentAndroidList ->
                       getAndroidFeaturesCall = mockApi.getAndroidVersionFeatures(mostRecentAndroidList.last().apiLevel)
                       getAndroidFeaturesCall?.enqueue(object : Callback<VersionFeatures>{
                           override fun onResponse(
                               call: Call<VersionFeatures>,
                               res: Response<VersionFeatures>
                           ) {
                               if (res.isSuccessful) {
                                   res.body()?.let { featuresOfMostRecentVersion ->
                                       uiState.value = UiState.Success(featuresOfMostRecentVersion)
                                   }
                               } else {
                                   uiState.value = UiState.Error("Network request Failed")
                               }
                           }

                           override fun onFailure(call: Call<VersionFeatures>, t: Throwable) {
                               uiState.value = UiState.Error(t.message ?: "Something unexpected happened!")
                           }

                       })
                   }
               } else {
                   uiState.value = UiState.Error("Network request Failed")
               }
            }

            override fun onFailure(call: Call<List<AndroidVersion>>, t: Throwable) {
               uiState.value = UiState.Error(t.message ?: "Something unexpected happened!")
            }

        })
    }

    override fun onCleared() {
        super.onCleared()
        getAndroidVersionsCall?.cancel()
        getAndroidFeaturesCall?.cancel()
    }
}