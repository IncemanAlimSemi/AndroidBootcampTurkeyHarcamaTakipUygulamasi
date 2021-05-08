package com.example.harcamatakipuygulamasi.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "spending_table")
data class Spending(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val typeOfSpending: Int,
    val descriptionOfSpending: String,
    var theMoneySpent: Float
): Parcelable
