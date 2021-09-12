package com.hassanmohammed.movieapp.ui.fragment

import android.app.SearchManager
import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.hassanmohammed.movieapp.R
import com.hassanmohammed.movieapp.adapters.MovieAdapter
import com.hassanmohammed.movieapp.adapters.MoviesLoadStateAdapter
import com.hassanmohammed.movieapp.databinding.FragmentDiscoverMoviesBinding
import com.hassanmohammed.movieapp.ui.BaseFragment
import com.hassanmohammed.movieapp.utils.hideSoftKeyboard
import com.hassanmohammed.movieapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscoverMoviesFragment :
    BaseFragment<FragmentDiscoverMoviesBinding>(R.layout.fragment_discover_movies) {
    private val mainViewModel by viewModels<MainViewModel>()
    private val movieAdapter by lazy { MovieAdapter() }
    override fun init() {
        setHasOptionsMenu(true)
        binding.movies.adapter = movieAdapter.withLoadStateHeaderAndFooter(
            header = MoviesLoadStateAdapter { movieAdapter.retry() },
            footer = MoviesLoadStateAdapter { movieAdapter.retry() }
        )
        getMoviesOrSearch()
    }

    override fun subscribeObservers() {
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
                hideSoftKeyboard()
                query?.let {
                    if (it.isNotEmpty())
                        getMoviesOrSearch(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        searchView.setOnCloseListener {
            hideSoftKeyboard()
            getMoviesOrSearch()
            false
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun setListeners() {
        lifecycleScope.launch {
            movieAdapter.loadStateFlow.collect { loadState ->
                binding.isLoading = loadState.source.refresh is LoadState.Loading
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        requireContext(),
                        "\\uD83D\\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun getMoviesOrSearch(query: String? = null) {
        lifecycleScope.launchWhenCreated {
            mainViewModel.discoverMovies(query).collect {
                movieAdapter.submitData(it)
            }
        }
    }


}