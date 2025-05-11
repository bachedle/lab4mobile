package com.example.lab4_movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import MovieDataClass
import com.example.lab4_movies.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : Fragment() {
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

        setupBackButton()

        val movie = arguments?.getSerializable("movie") as? Movie
        movie?.let { displayMovieDetails(it) }
    }

    private fun setupBackButton() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun displayMovieDetails(movie: Movie) {
        binding.apply {
            movieTitle.text = movie.title
            movieReleaseDate.text = "Release Date: ${movie.releaseDate}"
            movieRating.text = "Rating: ${movie.voteAverage}/10"
            movieOverview.text = movie.overview

            Glide.with(this@MovieDetailsFragment)
                .load(movie.posterUrl)
                .into(moviePoster)

            Glide.with(this@MovieDetailsFragment)
                .load(movie.backdropUrl)
                .into(movieBackdrop)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}