package com.alexeykatsuro.task4_storage.domain.datasources

import com.alexeykatsuro.task4_storage.utils.StorableEnum

enum class DataSourceType(override val key: String) : StorableEnum {
    Room(key = "room"),
    Cursor(key = "cursor");

    companion object {
        val default = Room

        fun formKey(key: String?): DataSourceType? {
            return values().find { it.key == key }
        }

        fun formKeyOrDefault(key: String?) = formKey(key) ?: default
    }
}