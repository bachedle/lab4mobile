package com.example.lab4_movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.lab4_movies.databinding.FragmentMovieDetailsBinding
import com.example.lab4_movies.MovieDataClass

class MovieDetails : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = arguments?.getSerializable("movie") as? MovieDataClass
        movie?.let { displayMovieDetails(it) }
    }

    private fun displayMovieDetails(movie: MovieDataClass) {
        binding.apply {
            movieTitle.text = movie.title
            movieDescription.text = movie.description

            Glide.with(this@MovieDetailsFragment)
                .load(movie.imageRes)
                .into(moviePoster)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
