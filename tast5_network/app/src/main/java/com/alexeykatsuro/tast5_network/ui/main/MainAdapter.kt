package com.alexeykatsuro.tast5_network.ui.main

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alexeykatsuro.tast5_network.R
import com.alexeykatsuro.tast5_network.data.dto.CatDto
import com.alexeykatsuro.tast5_network.databinding.CatItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

typealias OnClick = (CatDto) -> Unit

class MainAdapter(private val scope: CoroutineScope, private val callbacks: Callbacks) :
    ListAdapter<CatDto, MainAdapter.ViewHolder>(DiffCallback) {

    interface Callbacks {
        fun onShareClick(item: CatDto)
        fun onClick(item: CatDto)
    }


    class ViewHolder(
        private val scope: CoroutineScope,
        private val itemBinding: CatItemBinding,
        private val callbacks: Callbacks,
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {


        private var currentCat: CatDto? = null
        private var resultDrawable: Drawable? = null

        init {
            itemBinding.apply {
                catImage.setOnClickListener {
                    currentCat?.let(callbacks::onClick)
                }
                shareButton.setOnClickListener {
                    currentCat?.let(callbacks::onShareClick)
                }
            }
        }


        fun bind(item: CatDto) {
            currentCat = item
            itemBinding.also { ui ->
                ui.catImage.layoutParams.apply {
                    height = item.height ?: height
                    width = item.width ?: width
                }

                scope.launch {
                    ui.shareButton.isGone = true
                    ui.catImage.load(item.url) {
                        placeholder(R.drawable.ic_github_logo)
                    }.await()
                    ui.catImage.drawable
                    ui.shareButton.isGone = false
                }


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            CatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(scope, itemBinding, callbacks)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

private object DiffCallback : DiffUtil.ItemCallback<CatDto>() {
    override fun areItemsTheSame(oldItem: CatDto, newItem: CatDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CatDto, newItem: CatDto): Boolean {
        return oldItem == newItem
    }
}

