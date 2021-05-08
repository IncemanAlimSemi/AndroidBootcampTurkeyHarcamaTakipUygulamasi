package com.example.harcamatakipuygulamasi.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SpendingDatabaseDAO {
    @Insert
    suspend fun insert(spending: Spending)

    @Delete
    suspend fun delete(spending: Spending)

    @Query("SELECT * FROM spending_table WHERE id = :id")
    suspend fun get(id: Long): Spending?

    @Query("SELECT * FROM spending_table ORDER BY id DESC")
    fun getAllSpending(): LiveData<List<Spending>>

    @Query("DELETE FROM spending_table")
    suspend fun clear()
}