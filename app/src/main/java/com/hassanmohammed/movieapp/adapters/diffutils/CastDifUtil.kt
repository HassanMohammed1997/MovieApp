package com.hassanmohammed.movieapp.adapters.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.hassanmohammed.movieapp.models.CreditResponse

class CastDifUtil : DiffUtil.ItemCallback<CreditResponse.Cast>() {
    override fun areItemsTheSame(
        oldItem: CreditResponse.Cast,
        newItem: CreditResponse.Cast
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: CreditResponse.Cast,
        newItem: CreditResponse.Cast
    ): Boolean = oldItem == newItem
}