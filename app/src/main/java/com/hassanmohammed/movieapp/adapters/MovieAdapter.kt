package com.hassanmohammed.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hassanmohammed.movieapp.adapters.diffutils.MovieDiffUtil
import com.hassanmohammed.movieapp.databinding.ListItemMovieBinding
import com.hassanmohammed.movieapp.models.MovieResponse
import com.hassanmohammed.movieapp.ui.fragment.DiscoverMoviesFragmentDirections

class MovieAdapter : ListAdapter<MovieResponse.MovieResult, MovieViewHolder>(MovieDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder.getViewHolder(parent)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class MovieViewHolder(private val binding: ListItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun getViewHolder(parent: ViewGroup): MovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemMovieBinding.inflate(inflater, parent, false)
            return MovieViewHolder(binding)
        }
    }

    fun bind(movie: MovieResponse.MovieResult) {
        binding.movie = movie
        binding.executePendingBindings()
        itemView.setOnClickListener {
            val action =
                DiscoverMoviesFragmentDirections.actionDiscoverMoviesFragmentToMovieDetailsFragment(
                    movieID = movie.id
                )
            it.findNavController().navigate(action)
        }
    }

}
