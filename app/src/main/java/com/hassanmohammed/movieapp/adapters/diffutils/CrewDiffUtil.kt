package com.hassanmohammed.movieapp.adapters.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.hassanmohammed.movieapp.models.CreditResponse

class CrewDiffUtil : DiffUtil.ItemCallback<CreditResponse.Crew>() {
    override fun areItemsTheSame(
        oldItem: CreditResponse.Crew,
        newItem: CreditResponse.Crew
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: CreditResponse.Crew,
        newItem: CreditResponse.Crew
    ): Boolean = oldItem == newItem
}