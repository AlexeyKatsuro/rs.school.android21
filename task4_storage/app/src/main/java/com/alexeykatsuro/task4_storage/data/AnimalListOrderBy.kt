package com.alexeykatsuro.task4_storage.data

import com.alexeykatsuro.task4_storage.data.entities.Animal
import com.alexeykatsuro.task4_storage.utils.StorableEnum

enum class AnimalListOrderBy(override val key: String) : StorableEnum {
    Name(key = Animal.nameProperty),
    Age(key = Animal.ageProperty),
    Breed(key = Animal.breedProperty);

    companion object {

        fun formKey(key: String?): AnimalListOrderBy? {
            return values().find { it.key == key }
        }
    }

    fun compare(a: Animal, b: Animal): Int = when (this) {
        Name -> a.name.compareTo(b.name)
        Age -> a.age.compareTo(b.age)
        Breed -> a.breed.compareTo(b.breed)
    }
}
