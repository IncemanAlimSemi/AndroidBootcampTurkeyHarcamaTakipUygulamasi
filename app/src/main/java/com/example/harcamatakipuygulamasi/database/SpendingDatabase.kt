package com.example.harcamatakipuygulamasi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Spending::class], version = 1, exportSchema = false)
abstract class SpendingDatabase : RoomDatabase() {
    abstract val spendingDatabaseDAO: SpendingDatabaseDAO
    companion object {
        @Volatile
        private var INSTANCE: SpendingDatabase? = null

        fun getInstance(context: Context): SpendingDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SpendingDatabase::class.java,
                        "spending_database")
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}