package com.alexeykatsuro.tast5_network.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.alexeykatsuro.tast5_network.data.dto.CatDto
import com.alexeykatsuro.tast5_network.domain.repository.CatRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val catRepository: CatRepository
) : ViewModel() {

    private var pageNumber = 1
    private val _result = MutableStateFlow(pageNumber)

    @ExperimentalCoroutinesApi
    val result: Flow<List<CatDto>> = _result.map { number ->
        Log.d("TAG", "fetchCats")
        var list: List<CatDto> = emptyList()
        catRepository.fetchCats(limit = 10, page = number).fold(
            onSuccess = {
                Log.d("TAG", "${it}")
                list = (it)
            },
            onFailure = {
                Log.d("TAG", it.message, it)
            }
        )
        _isLoading.postValue(false)
        list
    }.scan(emptyList<CatDto>()) { accumulator, value ->
        accumulator + value
    }.onEach {
        Log.d("TAG", "onEach ${it}")
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    fun loadMore() {
        Log.d("TAG", "loadMore call")
        if (_result.tryEmit(pageNumber)) {
            pageNumber++
            _isLoading.postValue(true)
        }

    }


}