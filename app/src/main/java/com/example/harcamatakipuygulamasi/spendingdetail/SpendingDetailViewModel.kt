package com.example.harcamatakipuygulamasi.spendingdetail

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harcamatakipuygulamasi.database.Spending
import com.example.harcamatakipuygulamasi.database.SpendingDatabaseDAO
import kotlinx.coroutines.launch

private const val SHARED: String = "SharedPreferences"
private const val EUR: String = "EUR"
private const val USD: String = "USD"
private const val GBP: String = "GBP"

class SpendingDetailViewModel(
    spendingDatabaseDAO: SpendingDatabaseDAO,
    application: Application,
    selectedSpending: Spending,
    selectedType: Int
) : ViewModel() {

    private val sharedPref: SharedPreferences = application.getSharedPreferences(SHARED, Context.MODE_PRIVATE)
    private val spendingDatabaseDetailDAO = spendingDatabaseDAO
    private val spending = selectedSpending
    private val type = selectedType

    private val _spendingMoney = MutableLiveData<String>()
    val spendingMoney: LiveData<String>
        get() = _spendingMoney

    private val _checkGoHome = MutableLiveData<Boolean?>()
    val checkGoHome: LiveData<Boolean?>
        get() = _checkGoHome

    init {
        setSpendingDetail()
    }

    private fun setSpendingDetail() {
        val typeEUR  = sharedPref.getFloat(EUR, 0.100F)
        val typeUSD = sharedPref.getFloat(USD, 0.12F)
        val typeGBP = sharedPref.getFloat(GBP, 0.087F)

        val money = when(type)
        {
            1 -> "%.0f".format(spending.theMoneySpent * typeEUR)
            2 -> "%.0f".format(spending.theMoneySpent * typeUSD)
            3 -> "%.0f".format(spending.theMoneySpent * typeGBP)
            else -> "%.0f".format(spending.theMoneySpent)
        }

        val moneyString = when(type)
        {
            1 -> "$money €"
            2 -> "$money $"
            3 -> "$money £"
            else -> "$money ₺"
        }
        _spendingMoney.value = moneyString
    }
    fun delete()
    {
        viewModelScope.launch {
            spendingDatabaseDetailDAO.delete(spending)
            goBackToHome()
        }
    }

    fun goBackToHome() { _checkGoHome.value = true }

    fun goBackToHomeCompleted() { _checkGoHome.value = null }
}