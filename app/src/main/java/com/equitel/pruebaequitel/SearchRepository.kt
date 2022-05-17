package com.equitel.pruebaequitel

import com.equitel.pruebaequitel.database.EqDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRepository(private val database: EqDataBase) {
    suspend fun GuardarID(almacenamiento: Almacenamiento){
        return withContext(Dispatchers.IO){
            database.eqDao.insertFInal(almacenamiento)
        }
    }
}