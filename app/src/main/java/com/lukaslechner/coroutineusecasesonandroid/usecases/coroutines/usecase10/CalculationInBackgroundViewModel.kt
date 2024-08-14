package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase10

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.math.BigInteger
import kotlin.system.measureTimeMillis

class CalculationInBackgroundViewModel : BaseViewModel<UiState>() {

    fun performCalculation(factorialOf: Int) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
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
    }

    private suspend fun calculateFactorialOf(number: Int): BigInteger =
        withContext(Dispatchers.Default + CoroutineName("Calculating the factorial")) {
            var factorial = BigInteger.ONE
            for (i in 1..number) {
                factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
            }
            Timber.d("calculating Factorial completed")
            factorial
        }
}