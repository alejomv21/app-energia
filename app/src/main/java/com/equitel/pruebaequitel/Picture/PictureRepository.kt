package com.equitel.pruebaequitel.Picture

import androidx.lifecycle.LiveData
import com.equitel.pruebaequitel.Almacenamiento
import com.equitel.pruebaequitel.database.EqDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PictureRepository(private val database: EqDataBase) {

    //val almacenamientoFotos : LiveData<Almacenamiento> = database.eqDao.getAlmacenamientoPictures();

    suspend fun ExtraerAlmacenamientoFotos(): Almacenamiento{
        return withContext(Dispatchers.IO){
            val almacenamiento: Almacenamiento  = database.eqDao.getAlmacenamientoHoja1()
            almacenamiento
        }
    }

    suspend fun GuardarAlmacenamientoFotos(almacenamiento: Almacenamiento){
        return withContext(Dispatchers.IO){
            database.eqDao.insertFInal(almacenamiento)
        }
    }
}