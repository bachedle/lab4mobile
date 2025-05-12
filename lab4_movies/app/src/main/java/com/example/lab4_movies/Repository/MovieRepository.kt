package com.example.lab4_movies.Repository

import com.example.lab4_movies.Retrofit.RetrofitClient
import com.example.lab4_movies.Retrofit.TMDbApi
import com.example.lab4_movies.MovieDataClass
import retrofit2.Response

class MovieRepository {

    private val api: TMDbApi = RetrofitClient.api

    suspend fun getPopularMovies(): Response<List<MovieDataClass>> {
        return api.getPopularMovies("your_api_key_here")
    }

    suspend fun searchMovies(query: String): Response<List<MovieDataClass>> {
        return api.searchMovies("your_api_key_here", query)
    }
}
