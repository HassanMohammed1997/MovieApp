package com.hassanmohammed.movieapp.ui.fragment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hassanmohammed.movieapp.R
import com.hassanmohammed.movieapp.adapters.CastAdapter
import com.hassanmohammed.movieapp.adapters.CrewAdapter
import com.hassanmohammed.movieapp.data.NetworkResult
import com.hassanmohammed.movieapp.databinding.FragmentMovieDetailsBinding
import com.hassanmohammed.movieapp.ui.BaseFragment
import com.hassanmohammed.movieapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsFragment :
    BaseFragment<FragmentMovieDetailsBinding>(R.layout.fragment_movie_details) {
    private val mainViewModel by viewModels<MainViewModel>()
    private val crewAdapter by lazy { CrewAdapter() }
    private val castAdapter by lazy { CastAdapter() }
    private var imageUrl: String? = ""

    private val args by navArgs<MovieDetailsFragmentArgs>()
    override fun init() {
        getMovieDetails()
        getMovieCredit()
        binding.crewAdapter = crewAdapter
        binding.castAdapter = castAdapter
    }

    override fun subscribeObservers() {
        mainViewModel.movieDetails.observe(viewLifecycleOwner, {
            when (it) {
                is NetworkResult.Error -> {
                    binding.isLoading = false
                    it.errorMessage?.showErrorMessage()
                }
                is NetworkResult.Loading -> binding.isLoading = true
                is NetworkResult.Success -> {
                    binding.isLoading = false
                    imageUrl = it.data?.posterPath
                    binding.movieDetails = it.data
                }
            }
        })

        mainViewModel.movieCredit.observe(viewLifecycleOwner, {
            when (it) {
                is NetworkResult.Error -> {
                    binding.isLoading = false
                    it.errorMessage?.showErrorMessage()
                }
                is NetworkResult.Loading -> binding.isLoading = true
                is NetworkResult.Success -> {
                    crewAdapter.submitList(it.data?.crew)
                    castAdapter.submitList(it.data?.cast)
                }
            }
        })
    }

    override fun setListeners() {
        val expandableLayout = binding.includedLayout.expandableLayout
        binding.readMoreTxt.setOnClickListener {
            if (expandableLayout.isExpanded) {
                binding.readMoreTxt.text = getString(R.string.read_more)
                expandableLayout.collapse()
            } else {
                binding.readMoreTxt.text = getString(R.string.read_less)
                expandableLayout.expand()
            }
        }

        binding.movieImage.setOnClickListener {
            val action =
                MovieDetailsFragmentDirections.actionMovieDetailsFragmentToImageViewFullScreenDialog(
                    imageUrl
                )
            findNavController().navigate(action)
        }

    }

    private fun getMovieDetails() = apply { mainViewModel.getMovieDetails(args.movieID) }

    private fun getMovieCredit() = apply { mainViewModel.getMovieCredit(args.movieID) }


}