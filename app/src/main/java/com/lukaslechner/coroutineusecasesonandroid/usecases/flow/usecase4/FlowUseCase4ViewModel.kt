package com.lukaslechner.coroutineusecasesonandroid.usecases.flow.usecase4

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

class FlowUseCase4ViewModel(
    stockPriceDataSource: StockPriceDataSource
) : BaseViewModel<UiState>() {

    // SHARED FLOW
    val currentStockPriceAsFlow2: Flow<UiState> = stockPriceDataSource
        .latestStockList
        .map { stockList ->
            UiState.Success(stockList) as UiState
        }
        .onStart {
            emit(UiState.Loading)
        }
        .onCompletion {
            Timber.tag("Flow").d("Flow has completed.")
        }
        // convert a normal flow to a shared flow
        // this avoid multiple flows instances
        .shareIn(
            // will be collected according the viewmodel lifecycle
            scope = viewModelScope,
            // controls when the coroutine that collects and emits values should be started instead of term emit
            // types of Shared Objects:
            // SharingStarted.Eagerly -> the collection of the upstram flow will happen immediately, even if nobody currently collects from the shared flow
            // the flow would start at work immediately when the currentStockPriceAsFlow is initialized and not when the activity actually starts to collect from the shared flow,
            // in its own create method. (you may miss emissions)
            // SharingStarted.Lazily -> The collection and sharing of the emission start lazily when the first collector starts collecting from th share flow
            // this guarantees that the first collector gets all of the emitted values, however the upstream flow continues to be collected even when all collectors stop collecting (similar as eagerly)
            // SharingStarted.WhileSubscribed() -> The shared flow starts the collection and emission once the first collector starts collecting and immediately stops when the last collector stops collecting
            // as the name describes, collection and emission only happens while somebody is subscribed
            // The SharingStarted is an interface so you can also implement a customized one
            // add wait 5 seconds before stopping the upstream collection ( for configuration changes)
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            // Defines how many emissions are replayed or in the other terms re-emitted when a new collector starts collecting from our shared flow
            replay = 1, // will replay the last emission when a new subscriber start collecting, this case when the app return form the onStop due a configuration change
            // immediately the collector start collecting this paused shared flow (due the timeout) send the last effective emission, so that we avoid empty strings or loadings screens.
            // this solve the issue when configuration change re-starts the flow
        )

    // STATE FLOW
    val currentStockPriceAsFlow: Flow<UiState> = stockPriceDataSource
        .latestStockList
        .map { stockList ->
            UiState.Success(stockList) as UiState
        }
        .onStart {
            emit(UiState.Loading)
        }
        .onCompletion {
            Timber.tag("Flow").d("Flow has completed.")
        }
        // convert a normal flow to a state flow
        .stateIn(
            scope = viewModelScope,
            initialValue = UiState.Loading,
            started =  SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            // fixed value ofr replay to 1
        )

}