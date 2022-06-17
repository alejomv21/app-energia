package com.equitel.pruebaequitel.Sheet8

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class Sheet8ViewModelFactory (private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return Sheet8ViewModel(application) as T
    }
}