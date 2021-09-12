package com.hassanmohammed.movieapp.data

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val service: ApiService
) {

    suspend fun discoverMovies() = service.discoverMovies()
}