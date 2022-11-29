package com.equitel.pruebaequitel.Sheet8

import com.equitel.pruebaequitel.Almacenamiento
import com.equitel.pruebaequitel.api.CloudFunctions
import com.equitel.pruebaequitel.api.service
import com.equitel.pruebaequitel.database.EqDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import javax.security.auth.callback.Callback

class Sheet8Repository (private val database : EqDataBase){

    suspend fun extraerAlmacenamiento(): Almacenamiento {
        return withContext(Dispatchers.IO){
            val almacenamiento: Almacenamiento = database.eqDao.getAlmacenamientoHoja2()
            almacenamiento
        }
    }

    suspend fun guardarAlmacenamiento(almacenamiento: Almacenamiento){
        return withContext(Dispatchers.IO){
            database.eqDao.update(almacenamiento)
        }
    }

    suspend fun consultarID(id: Int?): Almacenamiento{
        return withContext(Dispatchers.IO){
            database.eqDao.getAlmacenamientoID(id)
        }
    }

    /*suspend fun enviarDataPdf(almacenamiento: Almacenamiento){
        return withContext(Dispatchers.IO){
            val response = CloudFunctions.retrofitService.postEnvioPdf(almacenamiento)
        }
    }*/

}