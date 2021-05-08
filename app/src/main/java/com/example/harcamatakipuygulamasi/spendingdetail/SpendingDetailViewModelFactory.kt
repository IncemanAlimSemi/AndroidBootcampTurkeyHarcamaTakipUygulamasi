package com.example.harcamatakipuygulamasi.spendingdetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.harcamatakipuygulamasi.database.Spending
import com.example.harcamatakipuygulamasi.database.SpendingDatabaseDAO

class SpendingDetailViewModelFactory(
    private val spendingDatabaseDAO: SpendingDatabaseDAO,
    private val application: Application,
    private val spending: Spending,
    private val type: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        if(modelClass.isAssignableFrom(SpendingDetailViewModel::class.java))
            return SpendingDetailViewModel(spendingDatabaseDAO, application, spending, type) as T
        throw IllegalArgumentException("Unknown")
    }
}