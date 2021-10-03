package com.alexeykatsuro.tast5_network.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alexeykatsuro.tast5_network.data.repository.CatRepository

class MainViewModel(
    catRepository: CatRepository
) : ViewModel() {

    val cats = catRepository.pagesOfCats(initialPage = 0, pageSize = 10).cachedIn(viewModelScope)
}