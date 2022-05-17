package com.equitel.pruebaequitel.Sheet2

import android.util.Log
import com.equitel.pruebaequitel.Almacenamiento
import com.equitel.pruebaequitel.database.EqDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Sheet2Repository(private val database : EqDataBase) {

    suspend fun ExtraerAlmacenamiento(): Almacenamiento {
        return withContext(Dispatchers.IO) {
            val almacenamiento : Almacenamiento = database.eqDao.getAlmacenamientoHoja2()
            //Log.d("peras", almacenamiento.marcaMotor)
            almacenamiento
        }
    }

    suspend fun guardarAlmacenamiento12(almacenamiento: Almacenamiento){
        return withContext(Dispatchers.IO){
            database.eqDao.insertFInal(almacenamiento)
            Log.d("MANZANAs", "exitoso")
        }
    }
}