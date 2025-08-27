package com.lukaslechner.coroutineusecasesonandroid.base

import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.channels.usecase1.ChannelUseCase1Activity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase1.PerformSingleNetworkRequestActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase10.CooperativeCancellationActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase11.CalculationInMultipleBackgroundThreadsActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase12.ExceptionHandlingActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase13.ContinueCoroutineWhenUserLeavesScreenActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase14.WorkManagerActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2.Perform2SequentialNetworkRequestsActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase3.PerformNetworkRequestsConcurrentlyActivity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase4.VariableAmountOfNetworkRequestsConcurrentlyActivity
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
const val useCase3Description = "#3 Perform several network requests concurrently"

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
                useCase3Description,
                PerformNetworkRequestsConcurrentlyActivity::class.java
            ),
            UseCase(
                "Perform variable amount of network requests concurrently",
                VariableAmountOfNetworkRequestsConcurrentlyActivity::class.java
            ),
            UseCase(
                "Network request with TimeOut",
                NetworkRequestWithTimeoutActivity::class.java
            ),
            UseCase(
                "Retry Network request",
                RetryNetworkRequestActivity::class.java
            ),
            UseCase(
                "Room and Coroutines",
                RoomAndCoroutinesActivity::class.java
            ),
            UseCase(
                "Debugging Coroutines",
                DebuggingCoroutinesActivity::class.java
            ),
            UseCase(
                "Perform Calculation in the background",
                CalculationInBackgroundActivity::class.java
            ),
            UseCase(
                "Cooperative Cancellation",
                CooperativeCancellationActivity::class.java
            ),
            UseCase(
                "Perform Calculation in multiple background threads",
                CalculationInMultipleBackgroundThreadsActivity::class.java
            ),
            UseCase(
                "Exception Handling",
                ExceptionHandlingActivity::class.java
            ),
            UseCase(
                "Continue Coroutine when User leaves screen",
                ContinueCoroutineWhenUserLeavesScreenActivity::class.java
            ),
            UseCase(
                "Using WorkManager with Coroutines",
                WorkManagerActivity::class.java
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