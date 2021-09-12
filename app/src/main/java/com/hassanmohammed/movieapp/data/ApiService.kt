package com.hassanmohammed.movieapp.data

import com.hassanmohammed.movieapp.models.CreditResponse
import com.hassanmohammed.movieapp.models.MovieDetailsResponse
import com.hassanmohammed.movieapp.models.MovieResponse
import com.hassanmohammed.movieapp.utils.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("page") page: Int = 1,
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieID: Int,
    ): MovieDetailsResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredit(
        @Path("movie_id") movieID: Int,
    ): CreditResponse

    @GET("search/movie")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
    ): MovieResponse

}