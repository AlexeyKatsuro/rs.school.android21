package com.alexeykatsuro.task4_storage.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alexeykatsuro.task4_storage.App
import com.alexeykatsuro.task4_storage.data.animalsList
import com.alexeykatsuro.task4_storage.data.dao.AnimalDao
import com.alexeykatsuro.task4_storage.data.entities.Animal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Animal class
@Database(entities = [Animal::class], version = 1)
abstract class AppRoomDatabase : RoomDatabase(), AppDatabase {

    abstract override fun animalDao(): AnimalDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            val application = context.applicationContext as App
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    application,
                    AppRoomDatabase::class.java,
                    AppDatabase.NAME
                ).addCallback(PrepopulateDatabaseCallback(application.applicationScope)).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class PrepopulateDatabaseCallback(
        private val scope: CoroutineScope,
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database)
                }
            }
        }

        private suspend fun populateDatabase(roomDatabase: AppRoomDatabase) {
            roomDatabase.animalDao().insertAll(animalsList())
        }
    }
}