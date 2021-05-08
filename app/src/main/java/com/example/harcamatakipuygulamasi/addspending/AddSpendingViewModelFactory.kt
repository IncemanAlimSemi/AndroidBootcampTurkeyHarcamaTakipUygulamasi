package com.example.harcamatakipuygulamasi.addspending

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.harcamatakipuygulamasi.database.SpendingDatabaseDAO
import java.lang.IllegalArgumentException

class AddSpendingViewModelFactory(
    private val spendingDatabaseDAO: SpendingDatabaseDAO,
    private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddSpendingViewModel::class.java))
            return AddSpendingViewModel(spendingDatabaseDAO, application) as T
        throw IllegalArgumentException("Unknown")
    }
}