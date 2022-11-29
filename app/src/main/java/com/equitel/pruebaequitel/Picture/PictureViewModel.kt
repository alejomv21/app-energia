package com.equitel.pruebaequitel.Picture

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.equitel.pruebaequitel.Almacenamiento
import com.equitel.pruebaequitel.database.getDataBase
import kotlinx.coroutines.launch

class PictureViewModel(application : Application): AndroidViewModel(application) {
    private val datebasePicture = getDataBase(application)
    private val repository = PictureRepository(datebasePicture)

    //val almacenamientoPictures = repository.almacenamientoFotos

    private val _almacenamiento = MutableLiveData<Almacenamiento>()
            val almacenamiento : LiveData<Almacenamiento>
            get() = _almacenamiento

    fun guardarAlmacenamientoFotos(almacenamiento: Almacenamiento){
        viewModelScope.launch {
            repository.GuardarAlmacenamientoFotos(almacenamiento)
        }
    }

    fun extraerAlmacenamientoFotos(){
        viewModelScope.launch {
            _almacenamiento.value = repository.ExtraerAlmacenamientoFotos()
        }
    }
}