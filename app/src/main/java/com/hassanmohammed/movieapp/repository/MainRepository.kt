package com.hassanmohammed.movieapp.repository

import com.hassanmohammed.movieapp.data.RemoteDataSource
import com.hassanmohammed.movieapp.utils.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
){

    suspend fun discoverMovies() = safeApiCall { remoteDataSource.discoverMovies() }
}