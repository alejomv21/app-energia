package com.equitel.pruebaequitel.Sheet7

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class Sheet7ViewModelFactory (private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return Sheet7ViewModel(application) as T
    }
}