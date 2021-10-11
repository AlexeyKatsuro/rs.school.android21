package com.alexeykatsuro.task5_network.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexeykatsuro.task5_network.R
import com.alexeykatsuro.task5_network.databinding.FooterItemBinding


class FooterAdapter(private val onRetryClick: () -> Unit) :
    LoadStateAdapter<FooterAdapter.ViewHolder>() {

    class ViewHolder(
        parent: ViewGroup,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.footer_item, parent, false)
    ) {

        private val itemBinding = FooterItemBinding.bind(itemView)

        init {
            itemBinding.retryButton.setOnClickListener {
                retryCallback()
            }
        }

        fun bind(loadState: LoadState) {
            itemBinding.also { ui ->
                ui.progressBar.isVisible = loadState is LoadState.Loading
                ui.retryButton.isVisible = loadState is LoadState.Error
                ui.errorMsg.isVisible =
                    !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                ui.errorMsg.text = (loadState as? LoadState.Error)?.error?.message
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {

        return ViewHolder(parent, onRetryClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}

