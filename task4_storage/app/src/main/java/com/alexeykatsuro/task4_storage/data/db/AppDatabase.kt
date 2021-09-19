package com.alexeykatsuro.task4_storage.data.db

import com.alexeykatsuro.task4_storage.data.dao.AnimalDao

interface AppDatabase {
    companion object {
        const val NAME = "animals_database"
    }

    fun animalDao(): AnimalDao
}