package com.alexeykatsuro.task4_storage

import android.app.Application
import androidx.preference.PreferenceManager
import com.alexeykatsuro.task4_storage.data.db.AppRoomDatabase
import com.alexeykatsuro.task4_storage.data.db.AppSQLiteHelperDatabase
import com.alexeykatsuro.task4_storage.domain.AnimalsRepository
import com.alexeykatsuro.task4_storage.domain.datasources.AnimalDataSource
import com.alexeykatsuro.task4_storage.domain.datasources.DataSourceType
import com.alexeykatsuro.task4_storage.domain.preferences.SharedPreferencesStorage
import com.alexeykatsuro.task4_storage.ui.add.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * This application class used like container of Dependencies
 *
 * Note: for production solutions use [Dagger2](https://developer.android.com/training/dependency-injection/dagger-android)
 *
 * Also see: [What is Dependency injection?](https://habr.com/ru/post/350068/)
 */
class App : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    val preferencesStorage by lazy {
        SharedPreferencesStorage(
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        )
    }

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy {
        when (preferencesStorage.dataSourceType) {
            DataSourceType.Room -> AppRoomDatabase.getDatabase(this)
            DataSourceType.Cursor -> AppSQLiteHelperDatabase.getDatabase(this)
        }
    }
    val dataSource by lazy { AnimalDataSource(database.animalDao()) }
    val animalsRepository by lazy { AnimalsRepository(dataSource) }

    val viewModelFactory by lazy {
        ViewModelFactory(
            animalsRepository = animalsRepository,
            preferencesStorage = preferencesStorage
        )
    }

}