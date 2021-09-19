package com.alexeykatsuro.task4_storage.domain

import com.alexeykatsuro.task4_storage.data.entities.Animal
import com.alexeykatsuro.task4_storage.domain.datasources.AnimalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class AnimalsRepository(private val animalDataSource: AnimalDataSource) {

    suspend fun addAnimal(animal: Animal) = withContext(Dispatchers.IO) {
        animalDataSource.addAnimal(animal)
    }

    suspend fun updateAnimal(animal: Animal) = withContext(Dispatchers.IO) {
        animalDataSource.updateAnimal(animal)
    }

    suspend fun removeAnimal(animal: Animal) = withContext(Dispatchers.IO) {
        animalDataSource.removeAnimal(animal)
    }

    fun observeAnimalList(): Flow<List<Animal>> = animalDataSource.observeAnimalList()
}