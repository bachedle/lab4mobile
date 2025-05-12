package com.example.lab4_movies.network

import com.example.lab4_movies.responses.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/movie")

    fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Call<MovieResponse>
}