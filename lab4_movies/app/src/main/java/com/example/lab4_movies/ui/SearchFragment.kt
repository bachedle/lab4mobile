package com.example.lab4_movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab4_movies.network.RetrofitClient
import com.example.lab4_movies.databinding.FragmentSearchBinding
import com.example.lab4_movies.responses.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = MovieAdapter(emptyList())
        binding.rvMovies.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMovies.adapter = adapter

        binding.btnSearch.setOnClickListener {
            val query = binding.etQuery.text.toString().trim()
            if (query.isNotEmpty()) getMovies(query)
        }
    }

    private fun getMovies(query: String) {
        RetrofitClient.apiService
            .getMovies(
                query = query,
                apiKey = "0dbea607de7c211f923d12eecee63806"
            )
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        val movies = response.body()?.results ?: emptyList()
                        adapter.updateData(movies)
                    } else {
                        Toast.makeText(requireContext(),
                            "Error: ${response.code()}",
                            Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Toast.makeText(requireContext(),
                        "Failed: ${t.message}",
                        Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}