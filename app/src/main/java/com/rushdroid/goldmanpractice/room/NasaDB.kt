package com.rushdroid.goldmanpractice.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rushdroid.goldmanpractice.model.NasaModel


@Database(entities = [NasaModel::class], version = 1, exportSchema = false)
abstract class NasaDB : RoomDatabase() {
    abstract fun parentDao(): NasaDao

    companion object {
        @Volatile
        private var instance: NasaDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            NasaDB::class.java, "NasaDB.db"
        )
            .allowMainThreadQueries()
            .build()
    }
}