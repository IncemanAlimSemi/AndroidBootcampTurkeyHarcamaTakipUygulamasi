package com.example.harcamatakipuygulamasi.userprofile

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserProfileViewModelFactory(private val  application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserProfileViewModel::class.java))
            return UserProfileViewModel(application) as T
        throw IllegalArgumentException("Unknown")
    }
}