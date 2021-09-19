package com.alexeykatsuro.task4_storage.domain.datasources

import com.alexeykatsuro.task4_storage.data.dao.AnimalDao
import com.alexeykatsuro.task4_storage.data.entities.Animal
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class AnimalDataSource(private val animalDao: AnimalDao) {

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    suspend fun addAnimal(animal: Animal) = animalDao.insert(animal)

    suspend fun updateAnimal(animal: Animal) = animalDao.update(animal)

    suspend fun removeAnimal(animal: Animal) = animalDao.remove(animal)

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    fun observeAnimalList(): Flow<List<Animal>> = animalDao.observeAnimalList()
}