package com.equitel.pruebaequitel.Sheet3

import com.equitel.pruebaequitel.Almacenamiento
import com.equitel.pruebaequitel.database.EqDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Sheet3Repository (private val database : EqDataBase){

    suspend fun extraerAlmacenamiento(): Almacenamiento{
        return withContext(Dispatchers.IO){
            val almacenamiento: Almacenamiento = database.eqDao.getAlmacenamientoHoja2()
            almacenamiento
        }
    }

    suspend fun guardarAlmacenamiento(almacenamiento: Almacenamiento){
        return withContext(Dispatchers.IO){
            database.eqDao.insertFInal(almacenamiento)
        }
    }

}