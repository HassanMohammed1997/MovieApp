package com.hassanmohammed.movieapp.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
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

fun Fragment.hideSoftKeyboard() {
    val imm =
        requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = requireActivity().currentFocus
    if (view == null) {
        view = View(activity);
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0);
}

fun Activity.disableNightMode() =
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
