package com.alexeykatsuro.task4_storage.ui.add

import androidx.lifecycle.*
import com.alexeykatsuro.task4_storage.data.entities.Animal
import com.alexeykatsuro.task4_storage.domain.AnimalsRepository
import com.alexeykatsuro.task4_storage.domain.preferences.PreferencesStorage
import com.alexeykatsuro.task4_storage.ui.main.MainViewModel
import kotlinx.coroutines.launch

class AddAnimalViewModel(private val animalsRepository: AnimalsRepository) : ViewModel() {

    private val _onResult = MutableLiveData<Boolean>()
    val onResult: LiveData<Boolean>
        get() = _onResult

    fun updateAnimal(animal: Animal) {
        viewModelScope.launch {
            _onResult.value = kotlin.runCatching {
                animalsRepository.updateAnimal(animal)
            }.isSuccess
        }
    }

    fun addAnimal(animal: Animal) {
        viewModelScope.launch {
            _onResult.value = kotlin.runCatching {
                animalsRepository.addAnimal(animal)
            }.isSuccess
        }
    }
}

class ViewModelFactory(
    private val animalsRepository: AnimalsRepository,
    private val preferencesStorage: PreferencesStorage
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(animalsRepository, preferencesStorage) as T
        } else if (modelClass.isAssignableFrom(AddAnimalViewModel::class.java)) {
            return AddAnimalViewModel(animalsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}