package com.alexeykatsuro.task5_network.ui.main

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alexeykatsuro.task5_network.R
import com.alexeykatsuro.task5_network.data.dto.CatDto
import com.alexeykatsuro.task5_network.databinding.MainFragmentBinding
import com.alexeykatsuro.task5_network.utils.filterWithPrevious
import com.alexeykatsuro.task5_network.utils.requireApp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map


class MainFragment : Fragment(R.layout.main_fragment) {


    private val viewModel: MainViewModel by viewModels {
        requireApp().viewModelFactory
    }

    private val viewBinding by viewBinding(MainFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.also { ui ->

            val mainAdapter = MainAdapter(host = this,
                callbacks = object : MainAdapter.Callbacks {
                    override fun onShareClick(item: CatDto) {

                    }

                    override fun onClick(imageView: ImageView, item: CatDto) {
                        val navDestination =
                            MainFragmentDirections.showViewImageFragment(item.url!!)
                        val extras = FragmentNavigatorExtras(
                            imageView to item.url
                        )
                        findNavController().navigate(navDestination, extras)
                    }

                })
            ui.swipeRefresh.setOnRefreshListener { mainAdapter.refresh() }
            val footerAdapter = FooterAdapter(onRetryClick = { mainAdapter.retry() })
            mainAdapter.addLoadStateListener { loadStates ->
                val state = loadStates.append.takeIf {
                    it !is LoadState.NotLoading
                } ?: loadStates.refresh
                footerAdapter.loadState = state
            }
            ui.imageList.adapter = ConcatAdapter(mainAdapter, footerAdapter)

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.cats.collectLatest {
                    mainAdapter.submitData(it)
                }
            }

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                mainAdapter.loadStateFlow.collectLatest { loadStates ->
                    ui.swipeRefresh.isRefreshing = loadStates.mediator?.refresh is LoadState.Loading
                }
            }

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                mainAdapter.loadStateFlow.map { it.refresh }.distinctUntilChanged()
                    .filterWithPrevious { previous, last ->
                        previous is LoadState.Loading && last is LoadState.NotLoading
                    }.collect {
                        ui.imageList.scrollToPosition(0)
                    }
            }
        }
    }

}