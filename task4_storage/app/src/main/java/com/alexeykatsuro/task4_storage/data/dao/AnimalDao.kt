package com.alexeykatsuro.task4_storage.data.dao

import androidx.room.*
import com.alexeykatsuro.task4_storage.data.entities.Animal
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(animal: Animal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animals: List<Animal>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(animal: Animal)

    @Delete
    suspend fun remove(animal: Animal)

    @Query("SELECT * FROM animal ORDER BY id DESC")
    fun observeAnimalList(): Flow<List<Animal>>
}