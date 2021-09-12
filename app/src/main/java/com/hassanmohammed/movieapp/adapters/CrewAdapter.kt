package com.hassanmohammed.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hassanmohammed.movieapp.adapters.diffutils.CrewDiffUtil
import com.hassanmohammed.movieapp.databinding.ListItemCrewBinding
import com.hassanmohammed.movieapp.models.CreditResponse

class CrewAdapter : ListAdapter<CreditResponse.Crew, CrewViewHolder>(CrewDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewViewHolder =
        CrewViewHolder.getViewHolder(parent)

    override fun onBindViewHolder(holder: CrewViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class CrewViewHolder(private val binding: ListItemCrewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun getViewHolder(parent: ViewGroup): CrewViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemCrewBinding.inflate(inflater, parent, false);
            return CrewViewHolder(binding)

        }
    }

    fun bind(crew: CreditResponse.Crew) {
        binding.crew = crew
        binding.executePendingBindings()
    }

}
