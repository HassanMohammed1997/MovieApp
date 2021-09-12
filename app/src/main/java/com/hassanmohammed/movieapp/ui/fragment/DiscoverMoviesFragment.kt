package com.hassanmohammed.movieapp.ui.fragment

import android.app.SearchManager
import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
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
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_discover_movies, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(
            searchManager.getSearchableInfo(requireActivity().componentName)
        )
        searchView.imeOptions = EditorInfo.IME_ACTION_SEARCH
        searchView.setIconifiedByDefault(true)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.isNotEmpty())
                        search(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        searchView.setOnCloseListener {
            getMovies()
            false
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun setListeners() {
    }

    private fun getMovies() = apply { mainViewModel.discoverMovies() }

    private fun search(query: String) = apply { mainViewModel.search(query) }


}