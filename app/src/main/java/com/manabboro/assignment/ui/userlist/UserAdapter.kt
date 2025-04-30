package com.manabboro.assignment.ui.userlist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.manabboro.assignment.databinding.ItemUserBinding
import com.manabboro.assignment.model.User

class UserAdapter(private val onItemClick: (User) -> Unit) :
    PagingDataAdapter<User, UserAdapter.UserViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.textViewName.text = "${user.first_name} ${user.last_name}"
            Glide.with(binding.root.context).load(user.avatar).into(binding.imageViewAvatar);
            binding.root.setOnClickListener { onItemClick(user) }
        }
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }
}
