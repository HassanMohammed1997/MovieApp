package com.hassanmohammed.movieapp.data

/**
 *Created by Hassan Mohammed on 6/28/21
 */
sealed class NetworkResult<T>(
    val data: T? = null,
    val errorMessage: String? = null
) {
    class Success<T>(data: T?) : NetworkResult<T>(data)
    class Error<T>(errorMessage: String?) : NetworkResult<T>(errorMessage = errorMessage)
    class Loading<T> : NetworkResult<T>()
}
