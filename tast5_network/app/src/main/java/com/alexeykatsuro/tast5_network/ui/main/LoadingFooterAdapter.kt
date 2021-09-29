package com.alexeykatsuro.tast5_network.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexeykatsuro.tast5_network.databinding.LoadingFooterItemBinding


class LoadingFooterAdapter :
    ListAdapter<Boolean, LoadingFooterAdapter.ViewHolder>(ToggleDiffCallback) {

    var isLoading: Boolean = false
        set(value) {
            field = value
            submitList(if(value) listOf(value) else emptyList())
        }

    init {
    }

    class ViewHolder(
        private val itemBinding: LoadingFooterItemBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(isLoading: Boolean) {
            itemBinding.progressBar.isGone = !isLoading
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            LoadingFooterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

private object ToggleDiffCallback : DiffUtil.ItemCallback<Boolean>() {
    override fun areItemsTheSame(oldItem: Boolean, newItem: Boolean): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: Boolean, newItem: Boolean): Boolean {
        return oldItem == newItem
    }
}

