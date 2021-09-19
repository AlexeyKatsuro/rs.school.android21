package com.alexeykatsuro.task4_storage.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Animal.tableName)
data class Animal(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = nameProperty) val name: String,
    @ColumnInfo(name = ageProperty) val age: Int,
    @ColumnInfo(name = breedProperty) val breed: String,
) {
    companion object {
        const val tableName = "Animal"
        const val nameProperty = "name"
        const val ageProperty = "age"
        const val breedProperty = "breed"
    }
}




