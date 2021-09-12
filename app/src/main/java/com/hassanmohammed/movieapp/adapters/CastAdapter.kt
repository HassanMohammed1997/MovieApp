package com.hassanmohammed.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hassanmohammed.movieapp.adapters.diffutils.CastDifUtil
import com.hassanmohammed.movieapp.databinding.ListItemCastBinding
import com.hassanmohammed.movieapp.models.CreditResponse

class CastAdapter : ListAdapter<CreditResponse.Cast, CastViewHolder>(CastDifUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder =
        CastViewHolder.getViewHolder(parent)

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class CastViewHolder(private val binding: ListItemCastBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun getViewHolder(parent: ViewGroup): CastViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemCastBinding.inflate(inflater, parent, false);
            return CastViewHolder(binding)

        }
    }

    fun bind(crew: CreditResponse.Cast) {
        binding.cast = crew
        binding.executePendingBindings()
    }

}