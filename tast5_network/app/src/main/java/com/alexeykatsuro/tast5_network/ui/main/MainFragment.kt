package com.alexeykatsuro.tast5_network.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alexeykatsuro.tast5_network.R
import com.alexeykatsuro.tast5_network.databinding.MainFragmentBinding
import com.alexeykatsuro.tast5_network.utils.requireApp

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
            viewModel.result.observe(viewLifecycleOwner) {cats ->
                ui.message.text = cats.joinToString(separator = "\n") {
                    it.url.orEmpty()
                }
            }
        }
    }

}