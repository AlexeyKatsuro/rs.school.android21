package com.alexeykatsuro.task4_storage.domain.preferences

import android.content.SharedPreferences
import com.alexeykatsuro.task4_storage.data.AnimalListOrderBy
import com.alexeykatsuro.task4_storage.domain.datasources.DataSourceType
import kotlinx.coroutines.flow.*

class SharedPreferencesStorage(private val sharedPreferences: SharedPreferences) :
    PreferencesStorage {

    private val preferenceKeyChangedFlow = MutableSharedFlow<String>(extraBufferCapacity = 1)

    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        preferenceKeyChangedFlow.tryEmit(key)
    }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override val isOrderingEnable: Boolean
        get() = sharedPreferences.getBoolean(PreferencesStorage.Keys.IS_ORDER_ENABLED, false)


    override val dataSourceType: DataSourceType
        get() {
            val key = sharedPreferences.getString(PreferencesStorage.Keys.DATA_SOURCE_TYPE, null)
            return DataSourceType.formKeyOrDefault(key)
        }


    override val animalOrderBy: AnimalListOrderBy?
        get() {
            val key =
                sharedPreferences.getString(PreferencesStorage.Keys.ANIMAL_LIST_ORDER_BY, null)
            return AnimalListOrderBy.formKey(key)
        }

    override fun observeOrderingEnabled(): Flow<Boolean> =
        preferenceKeyChangedFlow
            // Emit on start so that we always send the initial value
            .onStart { emit(PreferencesStorage.Keys.IS_ORDER_ENABLED) }
            .filter { it == PreferencesStorage.Keys.IS_ORDER_ENABLED }.map {
                isOrderingEnable
            }.distinctUntilChanged()

    override fun observeDataSourceType(): Flow<DataSourceType> =
        preferenceKeyChangedFlow
            // Emit on start so that we always send the initial value
            .onStart { emit(PreferencesStorage.Keys.DATA_SOURCE_TYPE) }
            .filter { it == PreferencesStorage.Keys.DATA_SOURCE_TYPE }.map {
                dataSourceType
            }.distinctUntilChanged()

    override fun observeAnimalOrderBy(): Flow<AnimalListOrderBy?> =
        preferenceKeyChangedFlow
            // Emit on start so that we always send the initial value
            .onStart { emit(PreferencesStorage.Keys.ANIMAL_LIST_ORDER_BY) }
            .filter { it == PreferencesStorage.Keys.ANIMAL_LIST_ORDER_BY }.map {
                animalOrderBy
            }.distinctUntilChanged()


}

