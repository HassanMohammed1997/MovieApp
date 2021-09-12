package com.hassanmohammed.movieapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.hassanmohammed.movieapp.R
import com.hassanmohammed.movieapp.utils.BindingAdapter.Companion.load
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageViewFullScreenDialog : DialogFragment() {
    private val args by navArgs<ImageViewFullScreenDialogArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_full_screen_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ImageView>(R.id.image).load(args.imageUrl)
        view.findViewById<ImageButton>(R.id.close_btn).setOnClickListener { dismiss() }

    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialogStyle
    }

}