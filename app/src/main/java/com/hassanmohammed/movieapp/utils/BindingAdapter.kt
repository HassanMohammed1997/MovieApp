package com.hassanmohammed.movieapp.utils

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.hassanmohammed.movieapp.R
import com.hassanmohammed.movieapp.ui.custom.CollectionPicker
import java.util.*

class BindingAdapter {
    companion object {

        @BindingAdapter("viewGone")
        @JvmStatic
        fun viewGone(view: View, gone: Boolean) {
            view.visibility = if (gone) View.GONE else View.VISIBLE
        }

        @BindingAdapter("setGenre")
        @JvmStatic
        fun CollectionPicker.setGenres(generis: List<String>?){
            generis?.let { this.setItems(it) }
        }

        @BindingAdapter("setGenre")
        @JvmStatic
        fun CollectionPicker.setGenres(genre: String?){
            genre?.let { this.setItems(Collections.singletonList(genre)) }
        }


        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun ImageView.load(imageUrl: String?) {
            if (imageUrl.isNullOrEmpty()) {
                setImageResource(R.drawable.image_placeholder)
                return
            }

            val progressDrawable =
                CircularProgressDrawable(context)
            progressDrawable.strokeWidth = 5f
            progressDrawable.centerRadius = 30f
            progressDrawable.start()
            Glide.with(context)
                .load(String.format(IMAGE_URL, imageUrl))
                .placeholder(progressDrawable)
                .fallback(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(this)

        }


        @BindingAdapter("loadProfileImage")
        @JvmStatic
        fun ImageView.loadProfileImage(imageUrl: String?) {
            if (imageUrl.isNullOrEmpty()) {
                setImageResource(R.drawable.profile_placeholder)
                return
            }

            val progressDrawable =
                CircularProgressDrawable(context)
            progressDrawable.strokeWidth = 5f
            progressDrawable.centerRadius = 30f
            progressDrawable.start()
            Glide.with(context)
                .load(String.format(IMAGE_URL, imageUrl))
                .placeholder(progressDrawable)
                .fallback(R.drawable.profile_placeholder)
                .error(R.drawable.profile_placeholder)
                .into(this)

        }

        @BindingAdapter("setAdapter")
        @JvmStatic
        fun RecyclerView.bindRecyclerViewAdapter(
            adapter: RecyclerView.Adapter<*>?
        ) {
            this.run {
                this.adapter = adapter
                setHasFixedSize(true)
            }
        }
    }


}