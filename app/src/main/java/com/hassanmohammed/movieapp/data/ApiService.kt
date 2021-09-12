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
        @Query("api_key") key: String = API_KEY
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieID: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieDetailsResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredit(
        @Path("movie_id") movieID: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): CreditResponse

    @GET("search/movie")
    suspend fun search(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = API_KEY,
    ): MovieResponse

}