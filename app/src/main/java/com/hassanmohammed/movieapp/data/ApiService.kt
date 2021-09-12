package com.hassanmohammed.movieapp.data

import com.hassanmohammed.movieapp.models.MovieResponse
import com.hassanmohammed.movieapp.utils.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("api_key") key: String = API_KEY
    ) : MovieResponse

}