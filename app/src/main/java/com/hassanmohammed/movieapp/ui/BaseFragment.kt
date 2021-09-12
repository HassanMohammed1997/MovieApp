package com.hassanmohammed.movieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import com.hassanmohammed.movieapp.R

abstract class BaseFragment<VB : ViewDataBinding>(@LayoutRes val layoutID: Int) : Fragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            layoutID,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        subscribeObservers()
        setListeners()


    }

    abstract fun init()
    abstract fun subscribeObservers()
    abstract fun setListeners()

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    protected fun String.showErrorMessage() {
        val snackbar = Snackbar.make(binding.root, this, LENGTH_LONG)
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.bg_error))
        snackbar.show()
    }


}