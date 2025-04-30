package com.manabboro.assignment.ui.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.manabboro.assignment.databinding.ItemMovieBinding
import com.manabboro.assignment.model.Movie

class MovieAdapter(private val onItemClick: (Movie) -> Unit) :
    PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.imdbID == newItem.imdbID
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.textViewTitle.text = movie.title
            binding.textViewReleaseDate.text = movie.year
            Glide.with(binding.root.context).load(movie.poster).into(binding.imageViewPoster)
            binding.root.setOnClickListener { onItemClick(movie) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}
