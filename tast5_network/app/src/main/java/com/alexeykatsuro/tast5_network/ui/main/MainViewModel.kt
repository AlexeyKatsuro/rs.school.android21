package com.alexeykatsuro.tast5_network.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.alexeykatsuro.tast5_network.data.dto.CatDto
import com.alexeykatsuro.tast5_network.domain.repository.CatRepository

class MainViewModel(
    private val catRepository: CatRepository
) : ViewModel() {
    val result: LiveData<List<CatDto>> = liveData {
        catRepository.fetchCats(limit = 5, page = 1).onSuccess {
            emit(it)
            Log.d("TAG", "$it")
        }.onFailure {
            Log.d("TAG", "$it",it)
        }
    }

}