package com.hassanmohammed.movieapp.data

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val service: ApiService
) {

    suspend fun discoverMovies() = service.discoverMovies()

    suspend fun getMovieDetails(movieID: Int) = service.getMovieDetails(movieID)

    suspend fun getMovieCredit(movieID: Int) = service.getMovieCredit(movieID)
}