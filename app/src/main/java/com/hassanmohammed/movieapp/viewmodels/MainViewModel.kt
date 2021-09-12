package com.hassanmohammed.movieapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hassanmohammed.movieapp.data.NetworkResult
import com.hassanmohammed.movieapp.models.CreditResponse
import com.hassanmohammed.movieapp.models.MovieDetailsResponse
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

    private val _movieDetails: MutableLiveData<NetworkResult<MovieDetailsResponse>> =
        MutableLiveData()
    val movieDetails: LiveData<NetworkResult<MovieDetailsResponse>> get() = _movieDetails

    private val _movieCredit: MutableLiveData<NetworkResult<CreditResponse>> = MutableLiveData()
    val movieCredit: LiveData<NetworkResult<CreditResponse>> get() = _movieCredit

    fun discoverMovies() = viewModelScope.launch {
        _movies.value = NetworkResult.Loading()
        _movies.value = mainRepository.discoverMovies()
    }

    fun getMovieDetails(movieID: Int) = viewModelScope.launch {
        _movieDetails.value = NetworkResult.Loading()
        _movieDetails.value = mainRepository.getMovieDetails(movieID)

    }

    fun getMovieCredit(movieID: Int) = viewModelScope.launch {
        _movieCredit.value = NetworkResult.Loading()
        _movieCredit.value = mainRepository.getMovieCredit(movieID)
    }

    fun search(query: String) = viewModelScope.launch {
        _movies.value = NetworkResult.Loading()
        _movies.value = mainRepository.search(query)
    }
}