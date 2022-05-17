package com.equitel.pruebaequitel.login

import com.equitel.pruebaequitel.api.service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class LoginRepository {

    suspend fun validarUsuario(usuario: String): String{
        return withContext(Dispatchers.IO){
            val constrasena : String = service.getContraseña(usuario)
            val resultado = convertirContrasena(constrasena)
            resultado
        }
    }
}

private fun convertirContrasena(contrasena: String): String{
    val eqJsonObject = JSONObject(contrasena)
    val resultData = eqJsonObject.getJSONObject( "resultData")
    val child = resultData.getJSONArray("child")
    if(child.length() == 0){
        return "usuario no es valido"
    }else{
        val resultado = child[0]
        return resultado.toString()
    }
}