package com.alexeykatsuro.task4_storage.utils

import android.content.Context
import com.alexeykatsuro.task4_storage.R
import com.alexeykatsuro.task4_storage.data.AnimalListOrderBy
import com.alexeykatsuro.task4_storage.domain.datasources.DataSourceType

// File contains Enum's extensions methods that converts Enum's values to human readable text

fun AnimalListOrderBy.localize(context: Context): String {
    val res = when (this) {
        AnimalListOrderBy.Name -> R.string.add_animal_input_label_name
        AnimalListOrderBy.Age -> R.string.add_animal_input_label_age
        AnimalListOrderBy.Breed -> R.string.add_animal_input_label_breed
    }
    return context.getString(res)
}

fun DataSourceType.localize(context: Context): String {
    val res = when (this) {
        DataSourceType.Room -> R.string.enum_dbms_room
        DataSourceType.Cursor -> R.string.enum_dbms_cursor
    }
    return context.getString(res)
}