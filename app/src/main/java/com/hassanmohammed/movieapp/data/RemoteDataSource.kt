package com.hassanmohammed.movieapp.data

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val service: ApiService
) {

    suspend fun discoverMovies(page: Int) = service.discoverMovies(page = page)

    suspend fun getMovieDetails(movieID: Int) = service.getMovieDetails(movieID)

    suspend fun getMovieCredit(movieID: Int) = service.getMovieCredit(movieID)

    suspend fun search(query: String, page: Int) = service.search(query, page)
}