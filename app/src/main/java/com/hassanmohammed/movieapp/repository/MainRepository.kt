package com.hassanmohammed.movieapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hassanmohammed.movieapp.data.MoviePagingDataSource
import com.hassanmohammed.movieapp.data.RemoteDataSource
import com.hassanmohammed.movieapp.models.MovieResponse
import com.hassanmohammed.movieapp.utils.NETWORK_PAGE_SIZE
import com.hassanmohammed.movieapp.utils.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) {

    fun discoverMovies(query: String?): Flow<PagingData<MovieResponse.MovieResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingDataSource(remoteDataSource, query) }
        ).flow

    }

    suspend fun getMovieDetails(movieID: Int) =
        safeApiCall { remoteDataSource.getMovieDetails(movieID) }

    suspend fun getMovieCredit(movieID: Int) =
        safeApiCall { remoteDataSource.getMovieCredit(movieID) }

}