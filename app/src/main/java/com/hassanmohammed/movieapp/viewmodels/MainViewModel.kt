package com.hassanmohammed.movieapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hassanmohammed.movieapp.data.NetworkResult
import com.hassanmohammed.movieapp.models.MovieResponse
import com.hassanmohammed.movieapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _movies: MutableLiveData<NetworkResult<MovieResponse>> = MutableLiveData()
    val movies: LiveData<NetworkResult<MovieResponse>> get() = _movies

    fun discoverMovies() = viewModelScope.launch {
        _movies.value = NetworkResult.Loading()
        _movies.value = mainRepository.discoverMovies()
    }
}