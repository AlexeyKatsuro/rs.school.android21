package com.alexeykatsuro.task5_network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexeykatsuro.task5_network.data.repository.CatRepository
import com.alexeykatsuro.task5_network.ui.main.MainViewModel

class ViewModelFactory(
    private val catRepository: CatRepository
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(catRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}