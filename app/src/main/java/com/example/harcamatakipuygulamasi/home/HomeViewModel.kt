package com.example.harcamatakipuygulamasi.home

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.harcamatakipuygulamasi.api.Api
import com.example.harcamatakipuygulamasi.api.ApiResponse
import com.example.harcamatakipuygulamasi.database.Spending
import com.example.harcamatakipuygulamasi.database.SpendingDatabaseDAO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val SHARED: String = "SharedPreferences"
private const val USER_NAME: String = "userName"
private const val USER_GENDER: String = "userGender"
private const val EUR: String = "EUR"
private const val USD: String = "USD"
private const val GBP: String = "GBP"

class HomeViewModel(
    spendingDatabaseDAO: SpendingDatabaseDAO,
    application: Application) : ViewModel() {



    private val applicationHome = application
    private var sharedPref : SharedPreferences = applicationHome.getSharedPreferences(SHARED, Context.MODE_PRIVATE)
    private val spendingDatabaseHomeDAO = spendingDatabaseDAO

    var allSpending = spendingDatabaseHomeDAO.getAllSpending()

    private val typeEUR: Float = sharedPref.getFloat(EUR, 0.100F)
    private var typeUSD = sharedPref.getFloat(USD, 0.12F)
    private var typeGBP = sharedPref.getFloat(GBP, 0.087F)

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName

    private val _userGender = MutableLiveData<Int>()
    val userGender: LiveData<Int>
        get() = _userGender

    private var _currentTypeOfMoney = MutableLiveData(1)
    val currentTypeOfMoney: LiveData<Int>
        get() = _currentTypeOfMoney

    private val _totalSpending = MutableLiveData<String>()
    val totalSpending: LiveData<String>
        get() = _totalSpending

    private val _goSpendingDetail = MutableLiveData<Spending>()
    val goSpendingDetail: LiveData<Spending>
        get() = _goSpendingDetail

    private var _status = MutableLiveData<Boolean?>()
    val status: LiveData<Boolean?>
        get() = _status

    init {
        getUser()
        updateSpending()
    }

    private fun getUser() {
        _userName.value = sharedPref.getString(USER_NAME, "")
        _userGender.value = sharedPref.getInt(USER_GENDER, -1)
    }

    fun getCurrentTypeOfMoney(): Int {
        return this._currentTypeOfMoney.value?:1
    }

    fun setCurrentTypeOfMoney(newType: Int) {
        this._currentTypeOfMoney.value = newType
    }

    fun getCurrentSpendingRate(): Float {

        return when (this._currentTypeOfMoney.value) {
            1 -> typeEUR
            2 -> typeUSD
            3 -> typeGBP
            else -> 1f
        }
    }

    fun totalSpending(total: Float) {

        val totalMoney = when(getCurrentTypeOfMoney()) {
            1 -> "%.0f".format(total * typeEUR)
            2 -> "%.0f".format(total * typeUSD)
            3 -> "%.0f".format(total * typeGBP)
            else -> "%.0f".format(total)
        }
        _totalSpending.value = when(getCurrentTypeOfMoney()) {
            1 -> "$totalMoney €"
            2 -> "$totalMoney $"
            3 -> "$totalMoney £"
            else -> "$totalMoney ₺"
        }
    }


    private fun updateSpending() {
        Api.retrofitService.getUpdateRates().enqueue(object: Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                with(sharedPref.edit()) {
                    response.body()?.conversion_rates?.EUR?.let { putFloat(EUR, it) }
                    response.body()?.conversion_rates?.USD?.let { putFloat(USD, it) }
                    response.body()?.conversion_rates?.GBP?.let { putFloat(GBP, it) }
                    apply()
                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                _status.value = true
                Log.e("API ERROR", t.message?:"Unknown Error")
            }
        })
    }

    fun statusComplete() {
        _status.value = null
    }

    fun onSpending(spending: Spending) {
        _goSpendingDetail.value = spending
    }

    fun onSpendingComplete() {
        _goSpendingDetail.value = null
    }


}