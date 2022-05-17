package com.equitel.pruebaequitel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel(){

    val repository = LoginRepository()

    private val _Constrasena = MutableLiveData<String>()
    val contrasena : LiveData<String>
        get()= _Constrasena

    fun validarContrasena(usuario: String){
        viewModelScope.launch {
            _Constrasena.value = repository.validarUsuario(usuario)
        }
    }
}