package com.alexeykatsuro.task4_storage.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.alexeykatsuro.task4_storage.data.entities.Animal
import com.alexeykatsuro.task4_storage.domain.AnimalsRepository
import com.alexeykatsuro.task4_storage.domain.preferences.PreferencesStorage
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class MainViewModel(
    private val animalsRepository: AnimalsRepository,
    preferencesStorage: PreferencesStorage
) : ViewModel() {

    // LiveData that created as combination of three [Flow] objects
    // 1th is List of Animals Flow from Database
    // and rest is Flows of Settings from [PreferencesStorage].
    /*
    This is necessary in order to reactively update the data on the screen in cases when the data has been changed in the database or in the settings
     */
    val animalsLiveData: LiveData<List<Animal>> =
        combine(
            animalsRepository.observeAnimalList(),
            preferencesStorage.observeOrderingEnabled(),
            preferencesStorage.observeAnimalOrderBy(),
        ) { animals, isOrderEnabled, orderBy ->
            // Here data from three flow is connected and you should return some value
            // as output next to next flow

            // In our case we sort our list of animals if it needed
            if (isOrderEnabled && orderBy != null) animals.sortedWith(orderBy::compare)
            else animals
        }.asLiveData()

    fun remove(animal: Animal) {
        viewModelScope.launch {
            kotlin.runCatching {
                animalsRepository.removeAnimal(animal)
            }
        }
    }
}


