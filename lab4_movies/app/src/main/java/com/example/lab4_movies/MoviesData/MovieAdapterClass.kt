package com.example.lab4_movies.MoviesData

import MovieDataClass
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lab4_movies.MovieDetailsFragment
import com.example.lab4_movies.R

class MovieAdapterClass(private val movies: List<MovieDataClass>) : RecyclerView.Adapter<MovieAdapterClass.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.movieTitle)
        val image: ImageView = itemView.findViewById(R.id.movieImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_layout, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.title.text = movie.title
        holder.image.setImageResource(movie.imageRes)

        holder.itemView.setOnClickListener {
            val fragment = MovieDetailsFragment().apply {
                arguments = android.os.Bundle().apply {
                    putInt("movieId", movie.id)
                    putString("movieTitle", movie.title)
                    putString("movieDescription", movie.description)
                    putInt("movieImageRes", movie.imageRes)
                    putString("movieVideoUrl", movie.videoUrl)
                }
            }
            val fragmentManager = (holder.itemView.context as FragmentActivity).supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int = movies.size
}