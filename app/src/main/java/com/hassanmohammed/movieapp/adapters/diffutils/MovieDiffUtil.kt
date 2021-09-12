package com.hassanmohammed.movieapp.adapters.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.hassanmohammed.movieapp.models.MovieResponse

class MovieDiffUtil : DiffUtil.ItemCallback<MovieResponse.MovieResult>() {
    override fun areItemsTheSame(
        oldItem: MovieResponse.MovieResult,
        newItem: MovieResponse.MovieResult
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: MovieResponse.MovieResult,
        newItem: MovieResponse.MovieResult
    ): Boolean = oldItem == newItem
}