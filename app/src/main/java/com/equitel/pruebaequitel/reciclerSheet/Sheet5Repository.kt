package com.equitel.pruebaequitel.reciclerSheet

import com.equitel.pruebaequitel.Almacenamiento
import com.equitel.pruebaequitel.database.EqDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class Sheet5Repository(private val database: EqDataBase) {
    /*suspend fun ListarAlmacenamiento(): MutableList<Almacenamiento>{
        return withContext(Dispatchers.IO){
            val almacenamiento : MutableList<Almacenamiento> = database.eqDao.getAlmacenamientoListas()
            almacenamiento
        }
    }*/
}