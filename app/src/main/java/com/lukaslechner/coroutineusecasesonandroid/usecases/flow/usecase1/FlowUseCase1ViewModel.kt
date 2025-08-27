package com.lukaslechner.coroutineusecasesonandroid.usecases.flow.usecase1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

class FlowUseCase1ViewModel(
    private val stockPriceDataSource: StockPriceDataSource
) : BaseViewModel<UiState>() {

    val currentStockPriceAsMutableLiveData: MutableLiveData<UiState> = MutableLiveData()
    val currentStockPriceAsLiveData: LiveData<UiState> =
        useAsLiveDataTerminalOperatorAndLifecycleOperators()
    var job : Job? = null

    fun startFlowCollection() {
        // LaunchedIn terminal op
        // useLaunchedInTerminalOperatorAndLifecycleOperators()
    }

    private fun useAsLiveDataTerminalOperatorAndLifecycleOperators():
            LiveData<UiState> {
        // We dont need a scope because its created internally
        // is based on weather the live data is active or not, it means
        // if there is an active observer of the live data

        return stockPriceDataSource
            .latestStockList
            .map { stockList ->
                UiState.Success(stockList) as UiState
            }
            .onStart {
                // onStart and onCompletion can be installed in any position of the pipeline
                // its code will be executed depending the state of the coroutine
                // but in this example the map is affecting the downstream so if we move
                // this block above the map block, the emit will not work
                emit(UiState.Loading)
            }
            .onCompletion {
                Timber.tag("Flow").d("Flow has completed.")
            }
            .asLiveData(
                //timeout = This parameter allow us to add determined time for the
                // observer of this live data, that time check if the observer has been inactive
                // for that amount of time (5seconds) well the live data will cancel the flow collection
                // when we do configuration changes like dark mode switching the activity restarts, so then
                // the observer go inactive for a short period of seconds, so don't need to cancel the flow
                // and start it again
            )
    }

    private fun useLaunchedInTerminalOperatorAndLifecycleOperators() {
        // By using this way, the collection will occur even in background which is a waste of resources
        // for the device which we MUST avoid
        /**
         * FLOW PROCESSING PIPELINE
        stockPriceDataSource
        .latestStockList
        --EACH OPERATOR IS EXECUTED ONE AFTER THE OTHER--
        .onStart {
        // ITS EXECUTED RIGHT BEFORE THE FLOW IS COLLECTED, when it starts and not
        // when the first item is collected
        Timber.d("The Flow starts to be collected. ")
        }
        .onCompletion { cause ->
        // ITS EXECUTED RIGHT WHEN THE FLOW HAS EMITTED ALL ITS VALUES OR GOT AN EXCEPTION
        // if the flow completed normally :cause: is null, otherwise contain the exception
        Timber.d("The Flow has completed ")
        // TODO: EVERY OPERATOR THAT HAPPENS BEFORE THIS BLOCK, IS THE **UPSTREAM**
        // TODO: EVERY OPERATOR THAT HAPPENS AFTERWARDS THIS BLOCK, IS THE **DOWNSTREAM**
        }
        .onEach { stockList ->
        currentStockPriceAsLiveData.value = UiState.Success(
        stockList
        )
        }
        .launchIn(viewModelScope)
         *
         */
        job = stockPriceDataSource
            .latestStockList
            .map { stockList ->
                UiState.Success(stockList) as UiState
            }
            .onStart {
                // onStart and onCompletion can be installed in any position of the pipeline
                // its code will be executed depending the state of the coroutine
                // but in this example the map is affecting the downstream so if we move
                // this block above the map block, the emit will not work
                emit(UiState.Loading)
            }
            .onEach { uiState ->
                currentStockPriceAsMutableLiveData.value = uiState
            }
            .onCompletion {
                Timber.tag("Flow").d("Flow has completed.")
            }
            .launchIn(viewModelScope)
    }

    fun stopFlowCollection(){
        // if we are using the launchedIn way anytime the activity restarts or go to stop phase
        // the job will be cancelled, for example if the user change its UI mode to light or dark
        // the activity is restarted so then the flow will be cancelled which could be a issue
        job?.cancel()
    }

}