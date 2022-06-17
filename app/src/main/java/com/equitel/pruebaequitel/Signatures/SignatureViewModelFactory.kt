package com.equitel.pruebaequitel.Signatures

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SignatureViewModelFactory (private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignaturesViewModel(application) as T
    }
}