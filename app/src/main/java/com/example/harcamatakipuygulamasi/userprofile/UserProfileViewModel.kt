package com.example.harcamatakipuygulamasi.userprofile

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val SHARED: String = "SharedPreferences"
private const val USER_NAME: String = "userName"
private const val USER_GENDER: String = "userGender"

class UserProfileViewModel(application: Application) : ViewModel() {

    private val sharedPref = application.getSharedPreferences(SHARED, Context.MODE_PRIVATE)

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName

    private val _userGender = MutableLiveData<Int>()
    val userGender: LiveData<Int>
        get() = _userGender

    private val _checkGoHome = MutableLiveData<Boolean?>()
    val checkGoHome: LiveData<Boolean?>
        get() = _checkGoHome

    init {
        getUser()
    }

    private fun getUser() {
        _userName.value = sharedPref.getString(USER_NAME, "")
        _userGender.value = sharedPref.getInt(USER_GENDER, -1)
    }

    fun saveUserProfile(name: String, gender: Int) {
        if (name != "") {
            with(sharedPref.edit()) {
                putString(USER_NAME, name)
                putInt(USER_GENDER, gender)
                apply()
            }
        }

        _checkGoHome.value = true
    }

    fun saveUserProfileComplete() {
        _checkGoHome.value = null
    }
}