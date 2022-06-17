package com.equitel.pruebaequitel.reciclerSheet

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.equitel.pruebaequitel.Almacenamiento
import com.equitel.pruebaequitel.database.getDataBase
import kotlinx.coroutines.launch

private val TAG = Sheet5ViewModel::class.java.simpleName
class Sheet5ViewModel(aplication : Application): AndroidViewModel(aplication) {
    private val databaseSheet5 = getDataBase(aplication)
    private val repository = Sheet5Repository(databaseSheet5)

    //private val _almacenamiento = MutableLiveData<MutableList<Almacenamiento>>()
    //val almacenamiento : LiveData<MutableList<Almacenamiento>>
    //    get() = _almacenamiento

    val almacenamiento = repository.eqList


    /*init {
        viewModelScope.launch {
            try{
                //_almacenamiento.value = repository.ListarAlmacenamiento()
            }catch (e: UnknownError){
                Log.d(TAG, "No conexión")
            }
        }
    }*/
}