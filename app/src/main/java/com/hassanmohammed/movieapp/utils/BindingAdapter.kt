package com.hassanmohammed.movieapp.utils

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.hassanmohammed.movieapp.R

class BindingAdapter {
    companion object {

        @BindingAdapter("viewGone")
        @JvmStatic
        fun viewGone(view: View, gone: Boolean) {
            Log.d("TAG", "viewGone: $gone")
            view.visibility = if (gone) View.GONE else View.VISIBLE
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

        @BindingAdapter("setAdapter")
        @JvmStatic
        fun RecyclerView.bindRecyclerViewAdapter(
            adapter: RecyclerView.Adapter<*>?
        ) {
            this.run {
                this.adapter = adapter
            }
        }
    }


}