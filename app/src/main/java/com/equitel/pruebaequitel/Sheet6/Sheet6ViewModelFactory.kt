package com.equitel.pruebaequitel.Sheet6

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class Sheet6ViewModelFactory (private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return Sheet6ViewModel(application) as T
    }
}