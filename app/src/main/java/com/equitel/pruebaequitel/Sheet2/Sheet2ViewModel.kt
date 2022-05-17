package com.equitel.pruebaequitel.Sheet2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.equitel.pruebaequitel.Almacenamiento
import com.equitel.pruebaequitel.database.getDataBase
import kotlinx.coroutines.launch

class Sheet2ViewModel(application: Application) : AndroidViewModel(application) {


    private val databaseSheet2 = getDataBase(application)
    private val repository = Sheet2Repository(databaseSheet2)

    private val _almacenamiento = MutableLiveData<Almacenamiento>()
    val almacenamiento : LiveData<Almacenamiento>
        get() = _almacenamiento

    fun GuardarAlmacenamiento12(almacenamiento: Almacenamiento){
        viewModelScope.launch {
            repository.guardarAlmacenamiento12(almacenamiento)
        }
    }

    fun extraerAlmacenamiento() {
        viewModelScope.launch {
            _almacenamiento.value  = repository.ExtraerAlmacenamiento()
        }
    }
}