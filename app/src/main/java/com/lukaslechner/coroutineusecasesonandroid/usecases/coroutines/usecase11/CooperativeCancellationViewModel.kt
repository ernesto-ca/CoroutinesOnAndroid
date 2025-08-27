package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase11

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import timber.log.Timber
import java.math.BigInteger
import kotlin.system.measureTimeMillis

class CooperativeCancellationViewModel(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    private var calculationJob: Job? = null
    fun performCalculation(factorialOf: Int) {
        uiState.value = UiState.Loading
        calculationJob = viewModelScope.launch {
            var result = BigInteger.ONE
            var resultString = ""
            val computationDuration = measureTimeMillis {
                result = calculateFactorialOf(factorialOf)
            }
            val stringConversionDuration = measureTimeMillis {
                resultString = withContext(Dispatchers.Default + CoroutineName("Converting to string")){
                    println(coroutineContext)
                    result.toString()
                }
            }
            uiState.value = UiState.Success(
                result = resultString,
                computationDuration = computationDuration,
                stringConversionDuration = stringConversionDuration
            )
        }
        calculationJob?.invokeOnCompletion { throwable ->
            if (throwable is CancellationException) {
                uiState.value = UiState.Error("Calculation was cancelled!")
            }
        }
    }

    private suspend fun calculateFactorialOf(number: Int): BigInteger =
        withContext(Dispatchers.Default + CoroutineName("Calculating the factorial")) {
            Timber.d("Calculating Factorial starts")
            var factorial = BigInteger.ONE
            for (i in 1..number) {
                yield()
                factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
            }
            Timber.d("Calculating Factorial completed")
            factorial
        }

    fun uiState(): LiveData<UiState> = uiState
    fun cancelCalculation() {
        calculationJob?.cancel()
    }

    private val uiState: MutableLiveData<UiState> = MutableLiveData()
}