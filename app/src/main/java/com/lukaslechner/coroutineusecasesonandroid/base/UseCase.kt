package com.lukaslechner.coroutineusecasesonandroid.base

import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.channels.usecase1.ChannelUseCase1Activity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase1.PerformSingleNetworkRequestActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase10.CooperativeCancellationActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase11.CalculationInSeveralCoroutinesActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase12.ExceptionHandlingActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase13.ContinueCoroutineWhenUserLeavesScreenActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase14.WorkManagerActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase15.PerformanceAnalysisActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2.Perform2SequentialNetworkRequestsActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2.usingcallbacks.SequentialNetworkRequestsCallbacksActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase3.PerformNetworkRequestsConcurrentlyActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase4.VariableAmountOfNetworkRequestsActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase5.NetworkRequestWithTimeoutActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase6.RetryNetworkRequestActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase7.RoomAndCoroutinesActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase8.DebuggingCoroutinesActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase9.CalculationInBackgroundActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.flow.usecase1.FlowUseCase1Activity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UseCase(
    val description: String,
    val targetActivity: Class<out AppCompatActivity>
) : Parcelable

@Parcelize
data class UseCaseCategory(val categoryName: String, val useCases: List<UseCase>) : Parcelable

const val useCase1Description = "#1 Perform single network request"
const val useCase2Description = "#2 Perform two sequential network requests"
const val useCase2UsingCallbacksDescription = "#2 using Callbacks"
const val useCase3Description = "#3 Perform several network requests concurrently"
const val useCase4Description = "#4 Perform variable amount of network requests"
const val useCase5Description = "#5 Network request with TimeOut"
const val useCase6Description = "#6 Retry Network request"
const val useCase7Description = "#7 Room and Coroutines"
const val useCase8Description = "#8 Debugging Coroutines"
const val useCase9Description = "#9 Offload expensive calculation to background thread"
const val useCase10Description = "#10 Cooperative Cancellation"
const val useCase11Description = "#11 Offload expensive calculation to several coroutines"
const val useCase12Description = "#12 Exception Handling"
const val useCase13Description = "#13 Continue Coroutine when User leaves screen"
const val useCase14Description = "#14 Using WorkManager with Coroutines"
const val useCase15Description =
    "#15 Performance Analysis of dispatchers, number of coroutines and yielding"

private val coroutinesUseCases =
    UseCaseCategory(
        "Coroutine Use Cases", listOf(
            UseCase(
                useCase1Description,
                PerformSingleNetworkRequestActivity::class.java
            ),
            UseCase(
                useCase2Description,
                Perform2SequentialNetworkRequestsActivity::class.java
            ),
            UseCase(
                useCase2UsingCallbacksDescription,
                SequentialNetworkRequestsCallbacksActivity::class.java
            ),
            UseCase(
                useCase3Description,
                PerformNetworkRequestsConcurrentlyActivity::class.java
            ),
            UseCase(
                useCase4Description,
                VariableAmountOfNetworkRequestsActivity::class.java
            ),
            UseCase(
                useCase5Description,
                NetworkRequestWithTimeoutActivity::class.java
            ),
            UseCase(
                useCase6Description,
                RetryNetworkRequestActivity::class.java
            ),
            UseCase(
                useCase7Description,
                RoomAndCoroutinesActivity::class.java
            ),
            UseCase(
                useCase8Description,
                DebuggingCoroutinesActivity::class.java
            ),
            UseCase(
                useCase9Description,
                CalculationInBackgroundActivity::class.java
            ),
            UseCase(
                useCase10Description,
                CooperativeCancellationActivity::class.java
            ),
            UseCase(
                useCase11Description,
                CalculationInSeveralCoroutinesActivity::class.java
            ),
            UseCase(
                useCase12Description,
                ExceptionHandlingActivity::class.java
            ),
            UseCase(
                useCase13Description,
                ContinueCoroutineWhenUserLeavesScreenActivity::class.java
            ),
            UseCase(
                useCase14Description,
                WorkManagerActivity::class.java
            ),
            UseCase(
                useCase15Description,
                PerformanceAnalysisActivity::class.java
            )
        )
    )

private val channelsUseCases =
    UseCaseCategory(
        "Channels Use Cases",
        listOf(
            UseCase(
                "Channels Use Case 1",
                ChannelUseCase1Activity::class.java
            )
        )
    )

private val flowUseCases =
    UseCaseCategory(
        "Flow Use Cases",
        listOf(
            UseCase(
                "Flow Use Case 1",
                FlowUseCase1Activity::class.java
            )
        )
    )

val useCaseCategories = listOf(
    coroutinesUseCases /*,
    channelsUseCases,
    flowUseCases */
)