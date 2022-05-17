package com.equitel.pruebaequitel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.equitel.pruebaequitel.database.getDataBase
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application){
    private val database = getDataBase(application)
    private val repository = SearchRepository(database)

    fun GuardarItem(almacenamiento: Almacenamiento){
        viewModelScope.launch {
            repository.GuardarID(almacenamiento)
        }
    }
}