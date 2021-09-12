package com.hassanmohammed.movieapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hassanmohammed.movieapp.models.MovieResponse
import com.hassanmohammed.movieapp.utils.NETWORK_PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val STARTING_PAGE_INDEX = 1

class MoviePagingDataSource @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : PagingSource<Int, MovieResponse.MovieResult>() {
    override fun getRefreshKey(state: PagingState<Int, MovieResponse.MovieResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse.MovieResult> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = remoteDataSource.discoverMovies(position)
            val repos = response.movieResults
            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}