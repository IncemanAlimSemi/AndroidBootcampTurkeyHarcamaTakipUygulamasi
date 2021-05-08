package com.example.harcamatakipuygulamasi.addspending

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harcamatakipuygulamasi.database.Spending
import com.example.harcamatakipuygulamasi.database.SpendingDatabaseDAO
import kotlinx.coroutines.launch
import java.lang.Exception

private const val SHARED: String = "SharedPreferences"
private const val EUR: String = "EUR"
private const val USD: String = "USD"
private const val GBP: String = "GBP"

class AddSpendingViewModel(
    spendingDatabaseDAO: SpendingDatabaseDAO,
    application: Application) : ViewModel() {

    private val sharedPref: SharedPreferences = application.getSharedPreferences(SHARED, Context.MODE_PRIVATE)
    private val spendingDatabaseAddDAO = spendingDatabaseDAO

    private val _checkGoHome = MutableLiveData<Boolean?>()
    val checkGoHome: LiveData<Boolean?>
        get() = _checkGoHome

    fun createSpending(
        spendingType: Int, description: String,
        moneyType: Int, spendingText: String) {

        try {
            if (spendingType != -1 && description != "" && spendingText != "") {
                var money = spendingText.toFloat()
                if (money >= 0) {
                    val typeEUR: Float = sharedPref.getFloat(EUR, 0.100F)
                    val typeUSD = sharedPref.getFloat(USD, 0.12F)
                    val typeGBP = sharedPref.getFloat(GBP, 0.087F)
                    money /= when(moneyType) {
                        1 -> typeEUR
                        2 -> typeUSD
                        3 -> typeGBP
                        else -> 1f
                    }
                    viewModelScope.launch {
                        val spending = Spending(0L, spendingType, description,money)
                        insert(spending)
                        _checkGoHome.value = true
                    }
                }
            }
        }
        catch(ex: Exception) { Log.e("ERROR", "SpendingViewModel - createSpending()") }
    }

    private suspend fun insert(spending: Spending) {
        spendingDatabaseAddDAO.insert(spending)
    }

    fun checkGoHomeCompleted() {
        _checkGoHome.value = null
    }
}