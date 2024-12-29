package com.example.github_app.UserFollowing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github_app.Commen.UserFollowing
import com.example.github_app.R
import com.example.github_app.databinding.LayoutCellUserFollowingBinding

class UserFollowingAdapter(
    private val items: List<UserFollowing>,
    private val onCellClicked: (String) -> Unit,
) : RecyclerView.Adapter<UserFollowingAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.create(parent, onCellClicked)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val users = items[position]
        holder.bind(users)
    }
    class UserViewHolder(
        private var binding: LayoutCellUserFollowingBinding,
        private val onCellClicked: (String) -> Unit,

        ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserFollowing) {
            binding.followerUserName.text = user.name
            loadImage(user.avatar_url)
            binding.root.setOnClickListener {
                onCellClicked(user.name)
            }
        }
        private fun loadImage(url: String) {
            if (url.isEmpty()) {
                binding.image.setImageResource(R.drawable.kotlin_svgrepo_com)
            } else {
                Glide
                    .with(binding.image)
                    .load(url)
                    .placeholder(R.drawable.kotlin_svgrepo_com)
                    .centerCrop()
                    .into(binding.image)
            }
        }
        companion object {
            fun create(parent: ViewGroup, onCellClicked: (String) -> Unit,): UserViewHolder {
                val binding = LayoutCellUserFollowingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return UserViewHolder(binding = binding, onCellClicked = onCellClicked)
            }
        }
    }
}
