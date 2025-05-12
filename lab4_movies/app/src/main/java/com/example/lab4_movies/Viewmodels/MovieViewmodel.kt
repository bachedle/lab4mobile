package com.example.lab4_movies.Viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab4_movies.MovieDataClass
import com.example.lab4_movies.Repository.MovieRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieViewmodel : ViewModel() {

    private val repository = MovieRepository()

    private val _movies = MutableLiveData<List<MovieDataClass>>()
    val movies: LiveData<List<MovieDataClass>> = _movies

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadPopularMovies() {
        _loading.value = true
        viewModelScope.launch {
            val response = repository.getPopularMovies()
            _loading.value = false
            if (response.isSuccessful) {
                _movies.value = response.body()
            } else {
                _error.value = "Failed to load movies"
            }
        }
    }

    fun searchMovies(query: String) {
        _loading.value = true
        viewModelScope.launch {
            val response = repository.searchMovies(query)
            _loading.value = false
            if (response.isSuccessful) {
                _movies.value = response.body()
            } else {
                _error.value = "Failed to search movies"
            }
        }
    }
}
