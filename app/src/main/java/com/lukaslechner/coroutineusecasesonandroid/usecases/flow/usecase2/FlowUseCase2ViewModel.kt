package com.lukaslechner.coroutineusecasesonandroid.usecases.flow.usecase2

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.usecases.flow.mock.Stock
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.withIndex
import timber.log.Timber

class FlowUseCase2ViewModel(
    stockPriceDataSource: StockPriceDataSource,
    defaultDispatcher: CoroutineDispatcher
) : BaseViewModel<UiState>() {

    /*

    Flow exercise 1 Goals
        DONE 1) only update stock list when Alphabet(Google) (stock.name ="Alphabet (Google)") stock price is > 2300$
        DONE ) only show stocks of "United States" (stock.country == "United States")
        DONE 3) show the correct rank (stock.rank) within "United States", not the world wide rank
        DONE 4) filter out Apple  (stock.name ="Apple") and Microsoft (stock.name ="Microsoft"), so that Google is number one
        DONE 5) only show company if it is one of the biggest 10 companies of the "United States" (stock.rank <= 10)
        DONE 6) stop flow collection after 10 emissions from the dataSource
        DONE 7) log out the number of the current emission so that we can check if flow collection stops after exactly 10 emissions
        8) Perform all flow processing on a background thread

     */

    private var numEmission = 0
    val currentStockPriceAsLiveData: LiveData<UiState> =
        stockPriceDataSource.latestStockList
            .withIndex()
            .onEach { indexedValue ->
                Timber.tag("Emissions").d("Processing emission ${indexedValue.index + 1}")

            }
            .map { indexedValue ->
                indexedValue.value
            }
            .take(10)
            .filter { stockList ->
                val alphabetPrice = stockList.find { stock: Stock ->
                              stock.symbol == "GOOG"
                          }?.currentPrice ?: return@filter false
                alphabetPrice > 2300
            }
            .map { stockList ->
                stockList.filter { stock ->
                    stock.country == "United States"
                }
            }
            .cancellable() // be careful where to add your cancellable check always have a balance with the code read and cutter
            // a  lot of cancellables along the flow will may confuse or make it spagetti, a good idea is to set the operator right before the most or complex code will be executed
            .map { stockList ->
                stockList.filter { stock ->
                    stock.name != "Apple" &&
                            stock.name != "Microsoft"
                }
            }
            .map { stockList ->
                stockList.mapIndexed { index, stock  ->
                    stock.copy( rank = index + 1)
                }
            }

            .map { stockList ->
                stockList.filter { stock ->
                    stock.rank <= 10
                }
            }
            .map { stockList ->
                UiState.Success(stockList) as UiState
            }
            .onStart { emit(UiState.Loading) }
            .asLiveData(defaultDispatcher)


    /**
     * My try:
     * .takeWhile {
     *                 val alphabet = it.firstOrNull { stock: Stock ->
     *                     stock.symbol == "GOOG"
     *                 }
     *                 alphabet != null &&
     *                         alphabet.currentPrice > 2300
     *             }
     *             .take(10)
     *             .onEach {
     *                 numEmission++
     *                 Timber.tag("Emissions").d("Num of emission: $numEmission")
     *             }
     *             .map {
     *                 val filteredList =
     *                     it.filter { stock ->
     *                         stock.country == "United States" &&
     *                                 stock.rank <= 10
     *                     }.sortedByDescending {
     *                         it.currentPrice
     *                     }
     *                 UiState.Success(filteredList) as UiState
     *             }
     *             .asLiveData(context = defaultDispatcher)
     */
}