package com.alexeykatsuro.task4_storage.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alexeykatsuro.task4_storage.R
import com.alexeykatsuro.task4_storage.databinding.MainFragmentBinding
import com.alexeykatsuro.task4_storage.utils.navigator
import com.alexeykatsuro.task4_storage.utils.requireApp
import com.alexeykatsuro.task4_storage.utils.requireMainActivity


class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewModel: MainViewModel by viewModels {
        requireApp().viewModelFactory
    }

    private val viewBinding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireMainActivity().supportActionBar?.apply {
            setTitle(R.string.main_screen_title)
        }
        viewBinding.apply {
            val animalsAdapter = AnimalsAdapter(
                onClick = { animal ->
                    navigator.navigateToEditAnimalFragment(animal)
                },
                onLongClick = { animal ->
                    viewModel.remove(animal)
                },
            )
            animalList.setHasFixedSize(true)
            animalList.adapter = animalsAdapter


            addButton.setOnClickListener {
                navigator.navigateToAddAnimalFragment()
            }

            viewModel.animalsLiveData.observe(viewLifecycleOwner) { animals ->
                animalsAdapter.submitList(animals)
            }
        }


    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_order_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort -> {
                navigator.navigateToSettingFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}