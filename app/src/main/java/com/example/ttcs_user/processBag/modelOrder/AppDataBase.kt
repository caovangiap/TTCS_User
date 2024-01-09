package com.example.ttcs_user.processBag.modelOrder

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Bag::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bag(): BagDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDataBase(context: Context): AppDatabase {
            val value = INSTANCE
            if (value != null) {
                return value
            }
            synchronized(this) {
                val instantce = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Inpormation Customer"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instantce
                return instantce
            }
        }
    }
}