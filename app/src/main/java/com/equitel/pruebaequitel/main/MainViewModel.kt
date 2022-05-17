package com.equitel.pruebaequitel.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.equitel.pruebaequitel.Almacenamiento
import com.equitel.pruebaequitel.SerialPlanta
import com.equitel.pruebaequitel.api.ApiResponseStatus
import com.equitel.pruebaequitel.database.getDataBase
import kotlinx.coroutines.*
import java.net.UnknownHostException

private val TAG = MainViewModel::class.java.simpleName
class MainViewModel(application: Application, ordenTrabajo : String) : AndroidViewModel(application) {

    private val database = getDataBase(application)
    private val repository = MainRepository(database, ordenTrabajo)

    private val _serialMotor =  MutableLiveData<ArrayList<String>>()
    val serialMotor : LiveData<ArrayList<String>>
            get() = _serialMotor

    private val _cliente =  MutableLiveData<ArrayList<String>>()
    val cliente : LiveData<ArrayList<String>>
        get() = _cliente

    private val _MarcaGenerador=  MutableLiveData<ArrayList<String>>()
    val MarcaGenerador : LiveData<ArrayList<String>>
        get() = _MarcaGenerador

    private val _Marcaplanta=  MutableLiveData<ArrayList<String>>()
    val Marcaplanta : LiveData<ArrayList<String>>
        get() = _Marcaplanta

    private val _ModeloGenerador=  MutableLiveData<ArrayList<String>>()
    val ModeloGenerador : LiveData<ArrayList<String>>
        get() = _ModeloGenerador

    private val _ModeloMotor=  MutableLiveData<ArrayList<String>>()
    val ModeloMotor : LiveData<ArrayList<String>>
        get() = _ModeloMotor

    private val _ModeloPlanta=  MutableLiveData<ArrayList<String>>()
    val ModeloPlanta : LiveData<ArrayList<String>>
        get() = _ModeloPlanta

    private val _SerialGenerador=  MutableLiveData<ArrayList<String>>()
    val SerialGenerador : LiveData<ArrayList<String>>
        get() = _SerialGenerador

    private val _SerialPlanta=  MutableLiveData<ArrayList<String>>()
    val SerialPlanta : LiveData<ArrayList<String>>
        get() = _SerialPlanta

    private val _status = MutableLiveData<ApiResponseStatus>()
    val status: LiveData<ApiResponseStatus>
        get() = _status
    private val _eqList = MutableLiveData<SerialPlanta>()
    val eqList : LiveData<SerialPlanta>
        get() = _eqList

    private val _tecnico = MutableLiveData<ArrayList<String>>()
    val tecnico : LiveData<ArrayList<String>>
        get() = _tecnico

    private val _hora = MutableLiveData<String>()
    val hora : LiveData<String>
        get() = _hora
    private val _almacenamiento = MutableLiveData<Almacenamiento>()
    val almacenamiento : LiveData<Almacenamiento>
        get() = _almacenamiento

    init {
        viewModelScope.launch {
            try {
                _status.value = ApiResponseStatus.LOADING
                _eqList.value =  repository.fetchSerialPlanta(ordenTrabajo)
                _serialMotor.value = repository.fetchSerialMotor()
                _status.value = ApiResponseStatus.DONE
                //_cliente.value = repository.fetchCliente()
                _MarcaGenerador.value = repository.fetchMarcaGenerador()
                _Marcaplanta.value = repository.fetchMarcaPlanta()
                _ModeloMotor.value = repository.fetchModeloMotor()
                _ModeloPlanta.value = repository.fetchModeloPlanta()
                _SerialGenerador.value = repository.fetchSerialGenerador()
                _SerialPlanta.value = repository.fetchSerialPlantaElectrica()
                //repository.guardarAlmacenamiento(almacenamiento)
                _hora.value =  repository.hora()
                _tecnico.value = repository.fetchTecnicos()

            } catch (e: UnknownHostException) {
                _status.value = ApiResponseStatus.NOT_INTERNET_CONNECTION
                Log.d(TAG, "No Internet Conection", e)
            }
        }
    }

    fun GuardarAlmacenamiento(almacenamiento: Almacenamiento){
        viewModelScope.launch {
            repository.guardarAlmacenamiento(almacenamiento)
        }
    }

    fun extraerAlmacenamiento() {
        viewModelScope.launch {
            _almacenamiento.value  = repository.ExtraerAlmacenamiento()
        }
    }

    fun prueba(prueba : String){
        viewModelScope.launch {
            repository.fetchPrueba(prueba)
        }
    }



}