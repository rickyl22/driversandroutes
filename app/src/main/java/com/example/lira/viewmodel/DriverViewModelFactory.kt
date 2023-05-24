package com.example.lira.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DriverViewModelFactory(private val application: Application) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DriverViewModel::class.java)){
            return DriverViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }


}