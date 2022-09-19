package com.equitel.pruebaequitel.Sheet8

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.equitel.pruebaequitel.Almacenamiento
import com.equitel.pruebaequitel.database.getDataBase
import kotlinx.coroutines.launch

class Sheet8ViewModel (application: Application) : AndroidViewModel(application){

    private val dataBase = getDataBase(application)
    private val repository = Sheet8Repository(dataBase)

    private val _Almacenamiento = MutableLiveData<Almacenamiento>()
    val almacenamiento : LiveData<Almacenamiento>
        get()= _Almacenamiento



    fun extraerAlmacenamiento(){
        viewModelScope.launch {
            _Almacenamiento.value = repository.extraerAlmacenamiento()
        }
    }

    fun guardaralmacenamiento(almacenamiento: Almacenamiento){
        viewModelScope.launch {
            repository.guardarAlmacenamiento(almacenamiento)
        }
    }

    fun consultarID(id : Int?){
        viewModelScope.launch {
            _Almacenamiento.value =  repository.consultarID(id)
        }
    }

}