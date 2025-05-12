package com.example.lab4_movies.MoviesData

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lab4_movies.MovieDataClass
import com.example.lab4_movies.R

class MovieAdapterClass(private val onClick: (MovieDataClass) -> Unit) : RecyclerView.Adapter<MovieAdapterClass.MovieViewHolder>() {

    private var movies = listOf<MovieDataClass>()

    fun submitList(movies: List<MovieDataClass>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_layout, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.title.text = movie.title
        Glide.with(holder.itemView).load(movie.imageRes).into(holder.image)

        holder.itemView.setOnClickListener {
            onClick(movie)
        }
    }

    override fun getItemCount() = movies.size

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.movieTitle)
        val image: ImageView = itemView.findViewById(R.id.movieImage)
    }
}
