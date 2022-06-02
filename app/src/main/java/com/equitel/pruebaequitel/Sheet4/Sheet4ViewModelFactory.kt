package com.equitel.pruebaequitel.Sheet4

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class Sheet4ViewModelFactory (private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return Sheet4ViewModel(application) as T
    }
}