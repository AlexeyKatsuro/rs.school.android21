package com.alexeykatsuro.tast5_network.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alexeykatsuro.tast5_network.R
import com.alexeykatsuro.tast5_network.data.dto.CatDto
import com.alexeykatsuro.tast5_network.databinding.MainFragmentBinding
import com.alexeykatsuro.tast5_network.utils.requireApp
import kotlinx.coroutines.flow.collect


class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels() {
        requireApp().viewModelFactory
    }

    private val viewBinding by viewBinding(MainFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.also { ui ->

            val mainAdapter = MainAdapter(viewLifecycleOwner.lifecycleScope, callbacks = object : MainAdapter.Callbacks {
                override fun onShareClick(item: CatDto) {
                }

                override fun onClick(item: CatDto) {
                }

            })

            val footerAdapter = LoadingFooterAdapter()
            val adapter = ConcatAdapter(mainAdapter,footerAdapter)
            ui.imageList.apply {
                this.adapter = adapter
                addOnScrollListener(PagingLoadMoreListener {
                    viewModel.loadMore()
                })
            }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.result.collect { images ->
                    mainAdapter.submitList(images)
                }
            }
            viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                footerAdapter.isLoading = isLoading
            }
        }
    }

}

class PagingLoadMoreListener(private val onLoadMore: () -> Unit) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        (recyclerView.layoutManager as? LinearLayoutManager)?.let { layoutManager ->
            val visibleItemCount: Int = layoutManager.childCount
            val totalItemCount: Int = layoutManager.itemCount
            val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
            Log.d("TAG", "visibleItemCount: $visibleItemCount")
            Log.d("TAG", "totalItemCount: $totalItemCount")
            Log.d("TAG", "firstVisibleItemPosition: $firstVisibleItemPosition")
            if (totalItemCount - 10 < firstVisibleItemPosition) {
                Log.d("TAG", "onLoadMore call")
                onLoadMore()
            }
        }

    }
}