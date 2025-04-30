package com.manabboro.assignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.manabboro.assignment.databinding.ItemLoadStateBinding

class MovieLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<MovieLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(private val binding: ItemLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.visibility = if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
                retryButton.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
                errorMsg.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE

                if (loadState is LoadState.Error) {
                    errorMsg.text = loadState.error.localizedMessage
                }

                retryButton.setOnClickListener { retry.invoke() }
            }
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLoadStateBinding.inflate(inflater, parent, false)
        return LoadStateViewHolder(binding)
    }
}
