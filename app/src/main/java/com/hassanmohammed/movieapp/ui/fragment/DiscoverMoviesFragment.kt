package com.hassanmohammed.movieapp.ui.fragment

import androidx.fragment.app.viewModels
import com.hassanmohammed.movieapp.R
import com.hassanmohammed.movieapp.adapters.MovieAdapter
import com.hassanmohammed.movieapp.data.NetworkResult
import com.hassanmohammed.movieapp.databinding.FragmentDiscoverMoviesBinding
import com.hassanmohammed.movieapp.ui.BaseFragment
import com.hassanmohammed.movieapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverMoviesFragment :
    BaseFragment<FragmentDiscoverMoviesBinding>(R.layout.fragment_discover_movies) {
    private val mainViewModel by viewModels<MainViewModel>()
    private val movieAdapter by lazy { MovieAdapter() }
    override fun init() {
        binding.movieAdapter = movieAdapter
        getMovies()
    }

    override fun subscribeObservers() {
        mainViewModel.movies.observe(viewLifecycleOwner, {
            when (it) {
                is NetworkResult.Error -> {
                    binding.isLoading = false
                    it.errorMessage?.showErrorMessage()
                }
                is NetworkResult.Loading -> binding.isLoading = true
                is NetworkResult.Success -> {
                    binding.isLoading = false
                    movieAdapter.submitList(it.data?.movieResults)
                }
            }
        })
    }

    override fun setListeners() {
    }

    private fun getMovies() = apply { mainViewModel.discoverMovies() }


}