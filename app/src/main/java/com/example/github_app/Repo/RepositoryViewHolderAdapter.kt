package com.example.github_app.Repo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.github_app.Commen.Repositories
import com.example.github_app.databinding.LayoutCellRepositoriesBinding

class RepositoryViewHolderAdapter(
    private val item: List<Repositories>,
) : RecyclerView.Adapter<RepositoryViewHolderAdapter.RepositoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder.create(parent,)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val repositories = item[position]
        holder.bind(repositories)
    }

    override fun getItemCount(): Int {
        return item.size
    }
    class RepositoryViewHolder(
        private var binding: LayoutCellRepositoriesBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(repositories: Repositories) {
            binding.license.text = repositories.license
            binding.title.text = repositories.name
            binding.date.text = repositories.date
            binding.description.text = repositories.description
            binding.language.text = repositories.language
        }
        companion object {
            fun create(parent: ViewGroup): RepositoryViewHolder {
                val binding = LayoutCellRepositoriesBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return RepositoryViewHolder(binding)
            }
        }
    }
}
