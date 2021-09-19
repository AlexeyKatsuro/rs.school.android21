package com.alexeykatsuro.task4_storage.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.alexeykatsuro.task4_storage.data.animalsList
import com.alexeykatsuro.task4_storage.data.dao.AnimalDao
import com.alexeykatsuro.task4_storage.data.entities.Animal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext


class AppSQLiteHelperDatabase(private val sqLiteOpenHelper: SQLiteOpenHelper) : AppDatabase {

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppSQLiteHelperDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = AppSQLiteHelperDatabase(
                    Helper(context, 1, AppDatabase.NAME, animalsList())
                )
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private var _animalDao: AnimalDao? = null

    override fun animalDao(): AnimalDao {

        return if (_animalDao != null) {
            _animalDao!!
        } else {
            synchronized(this) {
                if (_animalDao == null) {
                    _animalDao = AnimalSqlHelperDao(sqLiteOpenHelper)
                }
                return _animalDao!!
            }
        }
    }

    class Helper(
        context: Context,
        val version: Int,
        val name: String,
        private val prePopulation: List<Animal> = emptyList(),
    ) : SQLiteOpenHelper(context, name, null, version) {


        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL("CREATE TABLE IF NOT EXISTS ${Animal.tableName} (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ${Animal.nameProperty} TEXT NOT NULL, ${Animal.ageProperty} INTEGER NOT NULL, ${Animal.breedProperty} TEXT NOT NULL)")
            prePopulation.forEach { animal ->
                db.insert(
                    Animal.tableName,
                    null,
                    animal.toContentValues()
                )
            }
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS ${Animal.tableName}")
            onCreate(db)
        }

    }

}

fun Animal.toContentValues() = ContentValues().apply {
    if (id != 0L) {
        put("id", id)
    }
    put(Animal.nameProperty, name)
    put(Animal.ageProperty, age)
    put(Animal.breedProperty, breed)
}

fun Animal.Companion.fromCursor(cursor: Cursor): Animal {

    val idIndex = cursor.getColumnIndex("id")
    val nameIndex = cursor.getColumnIndex(nameProperty)
    val ageIndex = cursor.getColumnIndex(ageProperty)
    val breedIndex = cursor.getColumnIndex(breedProperty)

    val id = cursor.getLong(idIndex)
    val name = cursor.getString(nameIndex)
    val age = cursor.getInt(ageIndex)
    val breed = cursor.getString(breedIndex)

    return Animal(
        id = id,
        name = name,
        age = age,
        breed = breed
    )
}

class AnimalSqlHelperDao(private val helper: SQLiteOpenHelper) : AnimalDao {

    override suspend fun insert(animal: Animal): Unit = withContext(Dispatchers.IO) {
        helper.writableDatabase.use { db ->
            db.insert(
                Animal.tableName,
                null,
                animal.toContentValues()
            )
        }
    }

    override suspend fun update(animal: Animal): Unit = withContext(Dispatchers.IO) {
        helper.writableDatabase.use { db ->
            val where = "id=?"
            val whereArgs = arrayOf("${animal.id}")
            db.update(
                Animal.tableName,
                animal.toContentValues(),
                where,
                whereArgs
            )
        }
    }

    override suspend fun remove(animal: Animal) {
        helper.writableDatabase.use { db ->
            val where = "id=?"
            val whereArgs = arrayOf("${animal.id}")
            db.delete(
                Animal.tableName,
                where,
                whereArgs
            )
        }
    }

    override suspend fun insertAll(animals: List<Animal>): Unit = withContext(Dispatchers.IO) {
        helper.writableDatabase.use { db ->
            animals.forEach { animal ->
                db.insert(
                    Animal.tableName,
                    null,
                    animal.toContentValues()
                )
            }

        }
    }

    override fun observeAnimalList(): Flow<List<Animal>> = flow {
        // I suffered a lot with cursor's ContentObserver, as a result, I could not subscribe to the update
        // the onChange callback didn't call :(
        helper.writableDatabase.use { db ->
            db.rawQuery("SELECT * FROM ${Animal.tableName}", null).use { cursor ->
                val list = mutableListOf<Animal>()
                if (cursor.moveToFirst()) {
                    do {
                        list.add(Animal.fromCursor(cursor))
                    } while (cursor.moveToNext())
                }
                emit(list)
            }
        }
    }

}