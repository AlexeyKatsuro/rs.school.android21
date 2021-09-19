package com.alexeykatsuro.task4_storage.domain.preferences

import com.alexeykatsuro.task4_storage.data.AnimalListOrderBy
import com.alexeykatsuro.task4_storage.domain.datasources.DataSourceType
import kotlinx.coroutines.flow.Flow

interface PreferencesStorage {

    object Keys {
        const val IS_ORDER_ENABLED = "is_order_enabled"
        const val DATA_SOURCE_TYPE = "data_source_type"
        const val ANIMAL_LIST_ORDER_BY = "animal_list_order_by"
    }

    val isOrderingEnable: Boolean
    val dataSourceType: DataSourceType
    val animalOrderBy: AnimalListOrderBy?

    fun observeOrderingEnabled(): Flow<Boolean>

    fun observeDataSourceType(): Flow<DataSourceType>

    fun observeAnimalOrderBy(): Flow<AnimalListOrderBy?>
}
