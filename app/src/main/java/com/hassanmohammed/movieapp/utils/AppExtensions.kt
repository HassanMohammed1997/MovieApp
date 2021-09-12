package com.hassanmohammed.movieapp.utils

import android.util.Log
import com.hassanmohammed.movieapp.data.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): NetworkResult<T> {
    return withContext(dispatcher) {
        try {
            NetworkResult.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> NetworkResult.Error("Please check your internet connection")
                else -> {
                    NetworkResult.Error("Something went error")
                }
            }
        }
    }
}