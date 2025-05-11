package com.example.lab4_movies

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab4_movies.MoviesData.MovieAdapterClass
import com.example.lab4_movies.databinding.ActivityMainBinding
import com.example.lab4_movies.Viewmodels.MovieViewmodel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MovieViewmodel by viewModels()
    private lateinit var movieAdapter: MovieAdapterClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSearch()
        observeViewModel()

        // Load popular movies initially
        viewModel.loadPopularMovies()
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter { movie ->
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra("movie", movie)
            startActivity(intent)
        }

        binding.moviesRecyclerView.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setupSearch() {
        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.searchEditText.text.toString()
                if (query.isNotEmpty()) {
                    viewModel.searchMovies(query)
                }
                true
            } else {
                false
            }
        }
    }

    private fun observeViewModel() {
        viewModel.movies.observe(this) { movies ->
            movieAdapter.submitList(movies)
        }

        viewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) android.view.View.VISIBLE else android.view.View.GONE
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}