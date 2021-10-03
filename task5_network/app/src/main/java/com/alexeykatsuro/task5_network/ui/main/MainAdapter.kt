package com.alexeykatsuro.task5_network.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.clear
import coil.load
import com.alexeykatsuro.task5_network.R
import com.alexeykatsuro.task5_network.data.dto.CatDto
import com.alexeykatsuro.task5_network.databinding.CatItemBinding
import kotlinx.coroutines.launch


class MainAdapter(private val host: Fragment, private val callbacks: Callbacks) :
    PagingDataAdapter<CatDto, MainAdapter.ViewHolder>(DiffCallback) {

    private object DiffCallback : DiffUtil.ItemCallback<CatDto>() {
        override fun areItemsTheSame(oldItem: CatDto, newItem: CatDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CatDto, newItem: CatDto): Boolean {
            return oldItem == newItem
        }
    }


    interface Callbacks {
        fun onShareClick(item: CatDto)
        fun onClick(imageView: ImageView, item: CatDto)
    }


    class ViewHolder(
        parent: ViewGroup,
        private val host: Fragment,
        private val callbacks: Callbacks,
    ) :
        RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cat_item, parent, false)
        ) {


        private val itemBinding: CatItemBinding = CatItemBinding.bind(itemView)
        private var currentCat: CatDto? = null

        init {

            itemBinding.apply {
                catImage.setOnClickListener { view ->
                    currentCat?.let { item ->
                        callbacks.onClick(view as ImageView, item)
                    }
                }
                shareButton.setOnClickListener {
                    currentCat?.let(callbacks::onShareClick)
                }
            }
        }


        fun bind(item: CatDto?) {
            currentCat = item

            itemBinding.also { ui ->
                ui.catImage.transitionName = item?.url
                if (item != null) {
                    ui.catImage.layoutParams.apply {
                        height = item.height ?: height
                        width = item.width ?: width
                    }

                    host.viewLifecycleOwner.lifecycleScope.launch {
                        ui.shareButton.isGone = true
                        ui.catImage.load(item.url) {
                            placeholder(R.drawable.ic_github_logo)
                        }.await()
                        ui.catImage.drawable
                        ui.shareButton.isGone = false
                    }


                } else {
                    ui.catImage.clear()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, host, callbacks)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


