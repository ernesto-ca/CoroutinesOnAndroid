package com.lukaslechner.coroutineusecasesonandroid.usecases.flow.usecase4

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.lukaslechner.coroutineusecasesonandroid.base.BaseActivity
import com.lukaslechner.coroutineusecasesonandroid.base.flowUseCase4Description
import com.lukaslechner.coroutineusecasesonandroid.databinding.ActivityFlowUsecase1Binding
import com.lukaslechner.coroutineusecasesonandroid.utils.setGone
import com.lukaslechner.coroutineusecasesonandroid.utils.setVisible
import com.lukaslechner.coroutineusecasesonandroid.utils.toast
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat

class FlowUseCase4Activity : BaseActivity() {

    private var job: Job? = null
    private val binding by lazy { ActivityFlowUsecase1Binding.inflate(layoutInflater) }
    private val adapter = StockAdapter()

    private val viewModel: FlowUseCase4ViewModel by viewModels {
        ViewModelFactory(NetworkStockPriceDataSource(mockApi(applicationContext)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.recyclerView.adapter = adapter

        //job =
        lifecycleScope.launch {
            // start the flow collection when the activate is in and over the specified state, otherwise it will stop it (when the app is in onStop)
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // must be performed in its respective coroutine, if we try to collect two or more different flows
                // inner the repeatOnLifecycle the second and else flow will be not collected
                // because the coroutine is suspended until collection of the first flow finish
                launch {

                    viewModel.currentStockPriceAsFlow.collect { uiState ->
                        render(uiState)
                    }
                }
                launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.currentStockPriceAsFlow.collect { uiState ->
                            render(uiState)
                        }
                    }
                }
            }

        }


        // ALTERNATIVE TO THE repeatOnLifecycle:
//        lifecycleScope.launch {
//            viewModel.currentStockPriceAsFlow
//                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
//                .collect { uiState ->
//                    render(uiState)
//                }
//        }

    }

    override fun onStop() {
        super.onStop()

        //job?.cancel() not needed due the repeatOnLifecycle
    }

    private fun render(uiState: UiState) {
        when (uiState) {
            is UiState.Loading -> {
                binding.progressBar.setVisible()
                binding.recyclerView.setGone()
            }

            is UiState.Success -> {
                binding.recyclerView.setVisible()
                binding.lastUpdateTime.text =
                    "lastUpdateTime: ${LocalDateTime.now().toString(DateTimeFormat.fullTime())}"
                adapter.stockList = uiState.stockList
                binding.progressBar.setGone()
            }

            is UiState.Error -> {
                toast(uiState.message)
                binding.progressBar.setGone()
            }
        }
    }

    override fun getToolbarTitle() = flowUseCase4Description
}