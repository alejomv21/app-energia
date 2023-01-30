package com.equitel.pruebaequitel.main

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.equitel.pruebaequitel.Almacenamiento
import com.equitel.pruebaequitel.AplicacionOrdenTrabajo
import com.equitel.pruebaequitel.SerialPlanta
import com.equitel.pruebaequitel.api.service
import com.equitel.pruebaequitel.database.EqDao
import com.equitel.pruebaequitel.database.EqDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainRepository (private val database : EqDataBase, idOrden : String){


    //val eqlist : LiveData<SerialPlanta>? = database.eqDao.getEquitel()
    suspend fun fetchSerialPlanta(idOrden: String): SerialPlanta{
        return withContext(Dispatchers.IO) {
            val serialString: String = service.getTotal(idOrden)
            val serial = parseREsultSI(serialString)
            serial
            //database.eqDao.insertAll(serial)
        }
    }

    /*suspend fun fetchPrueba(prueba : String){
        return withContext(Dispatchers.IO) {
            val serialString: String = service.getPrueba(prueba)
            Log.d("limon", serialString)
        }
    */

    suspend fun guardarAlmacenamiento(almacenamiento: Almacenamiento){
        return withContext(Dispatchers.IO){
            database.eqDao.insertFInal(almacenamiento)
            Log.d("MANZANAs", "exitoso")
        }
    }


    suspend fun fetchSerialMotor(): ArrayList<String>{
        return withContext(Dispatchers.IO) {
            val serialString: String = service.getSerialMotor()
            val convertir = ConvertirResulttados(serialString, "serialMotor")
            convertir
        }
    }

    suspend fun fetchCliente(): ArrayList<String>{
        return withContext(Dispatchers.IO) {
            val serialString: String = service.getcliente()
            val convertir = ConvertirResulttados(serialString, "cliente")
            convertir
        }
    }

    /*suspend fun fetchSede(): ArrayList<String>{
        return withContext(Dispatchers.IO) {
            val serialString: String = service.getcliente()
            val convertir = ConvertirResulttados(serialString, "sede")
            convertir
        }
    }*/

    suspend fun fetchMarcaGenerador(): ArrayList<String>{
        return withContext(Dispatchers.IO) {
            val serialString: String = service.getMarcaGenerador()
            val convertir = ConvertirResulttados(serialString, "marcaGenerador")
            convertir
        }
    }

    suspend fun fetchMarcaPlanta(): ArrayList<String>{
        return withContext(Dispatchers.IO) {
            val serialString: String = service.getMarcaPlanta()
            val convertir = ConvertirResulttados(serialString, "marcaPlanta")
            convertir
        }
    }

    suspend fun fetchModeloGenerador(): ArrayList<String>{
        return withContext(Dispatchers.IO) {
            val serialString: String = service.getModeloGenerador()
            val convertir = ConvertirResulttados(serialString, "modeloGenerador")
            convertir
        }
    }

    suspend fun fetchModeloMotor(): ArrayList<String>{
        return withContext(Dispatchers.IO) {
            val serialString: String = service.getModeloMotor()
            val convertir = ConvertirResulttados(serialString, "modeloMotor")
            convertir
        }
    }

    suspend fun fetchModeloPlanta(): ArrayList<String>{
        return withContext(Dispatchers.IO) {
            val serialString: String = service.getModeloPlanta()
            val convertir = ConvertirResulttados(serialString, "modeloPlanta")
            convertir
        }
    }

    suspend fun fetchSerialGenerador(): ArrayList<String>{
        return withContext(Dispatchers.IO) {
            val serialString: String = service.getSerialGenerador()
            val convertir = ConvertirResulttados(serialString, "serialGenerador")
            convertir
        }
    }

    suspend fun fetchTecnicos(): ArrayList<String>{
        return withContext(Dispatchers.IO) {
            val serialString: String = service.getTecnico()
            val convertir = ConvertirTecnico(serialString)
            convertir
        }
    }

    suspend fun hora(): String{
        return withContext(Dispatchers.IO){
            val sdf = SimpleDateFormat("dd/M/yyyy ")
            val currentDate = sdf.format(Date())
            Log.d("peras", currentDate)
            currentDate
        }
    }

    suspend fun fetchSerialPlantaElectrica(): ArrayList<String>{
        return withContext(Dispatchers.IO) {
            val serialString: String = service.getSerialPlanta()
            val convertir = ConvertirResulttados(serialString, "serialPlanta")
            convertir
        }
    }

    suspend fun ExtraerAlmacenamiento(): Almacenamiento{
        return withContext(Dispatchers.IO) {
            val almacenamiento : Almacenamiento = database.eqDao.getAlmacenamientoHoja2()
            //Log.d("peras", almacenamiento.idEquipo)
            almacenamiento
        }
    }

}

    private fun parseREsult(serialString: String) : SerialPlanta {
            val serie : SerialPlanta = SerialPlanta()
            val eqJsonObject = JSONObject(serialString)
            val resultData = eqJsonObject.getJSONObject( "resultData")
            val child = resultData.getJSONArray("child")
            if(child.length() != 0) {
                val terminar = child[0] as JSONObject
                val cliente = terminar.getString("cliente")
                val direccion = terminar.getString("direccion")
                val marcaMotor = terminar.getString("marcaMotor")
                val marcaGenerador = terminar.getString("marcaGenerador")
                val marcaPlanta = terminar.getString("marcaPlanta")
                val ciudad = terminar.getString("ciudad")
                val modeloMotor = terminar.getString("modeloMotor")
                val modeloGenerador = terminar.getString("modeloGenerador")
                val modeloPlanta = terminar.getString("modeloPlanta")
                val serialMotor = terminar.getString("serialMotor")
                val serialGenerador = terminar.getString("serialGenerador")
                val serialPlanta = terminar.getString("serialPlanta")
                val cpl = terminar.getString("cpl")
                val tipoControl = terminar.getString("tipoControl")
                val trcnIdcargo = terminar.getString("trcnIdcargo")

                serie.tipoUbicacion = "CABINA"
                serie.tipoEquipo = "2"
                serie.cliente = cliente
                serie.dir = direccion
                serie.MarcaMotor = marcaMotor
                serie.marcaGen = marcaGenerador
                serie.marcaPlnata = marcaPlanta
                serie.ciudad = ciudad
                serie.modMotor = modeloMotor
                serie.modGen = modeloGenerador
                serie.modPlanta = modeloPlanta
                serie.snMotor = serialMotor
                serie.snGen = serialGenerador
                serie.snPlanta = serialPlanta
                serie.cpl = cpl
                serie.kw = "2"
                serie.spec = ""
                serie.tipoControl = tipoControl
                serie.tecnicosCargo = trcnIdcargo
                serie.promotionID = ""
                serie.motivoVisita = ""

            }

        return serie
    }

private fun parseREsultSI(serialString: String) : SerialPlanta {
    val serie : SerialPlanta = SerialPlanta()
    val eqJsonObject = JSONArray(serialString)
    if(eqJsonObject.length() != 0) {
        val terminar = eqJsonObject[0] as JSONObject
        val cliente = terminar.getString("cliente")
        val direccion = terminar.getString("direccion")
        val marcaMotor = terminar.getString("marcaMotor")
        val marcaGenerador = terminar.getString("marcaGenerador")
        val marcaPlanta = terminar.getString("marcaPlanta")
        val ciudad = terminar.getString("ciudad")
        val modeloMotor = terminar.getString("modeloMotor")
        val modeloGenerador = terminar.getString("modeloGenerador")
        val modeloPlanta = terminar.getString("modeloPlanta")
        val serialMotor = terminar.getString("serialMotor")
        val serialGenerador = terminar.getString("serialGenerador")
        val serialPlanta = terminar.getString("serialPlanta")
        val cpl = terminar.getString("cpl")


        serie.tipoUbicacion = "CABINA"
        serie.tipoEquipo = "2"
        serie.cliente = cliente
        serie.dir = direccion
        serie.MarcaMotor = marcaMotor
        serie.marcaGen = marcaGenerador
        serie.marcaPlnata = marcaPlanta
        serie.ciudad = ciudad
        serie.modMotor = modeloMotor
        serie.modGen = modeloGenerador
        serie.modPlanta = modeloPlanta
        serie.snMotor = serialMotor
        serie.snGen = serialGenerador
        serie.snPlanta = serialPlanta
        serie.cpl = cpl
        serie.kw = "2"
        serie.spec = ""
        serie.tipoControl = ""
        serie.tecnicosCargo = ""
        serie.promotionID = ""
        serie.motivoVisita = ""

    }

    return serie
}

    private fun ConvertirResulttados(serialString: String, descripcion: String) : ArrayList<String>{
        val eqJsonObject = JSONArray(serialString)
        val serialMotor : ArrayList<String> = ArrayList<String>()
        for(i in 0 until eqJsonObject.length()) {
            val resultData = eqJsonObject[i] as JSONObject

            val serieMotor = resultData.getString(descripcion)
            Log.d("MANZANAs", serieMotor)
            serialMotor.add(serieMotor)
        }
        return serialMotor
    }


    private fun ConvertirTecnico(serialString: String ) : ArrayList<String>{
        val eqJsonObject = JSONArray(serialString)
        val serialMotor : ArrayList<String> = ArrayList<String>()
        for(i in 0 until eqJsonObject.length()) {
            val resultData = eqJsonObject[i] as JSONObject
            val serieMotor = resultData.getString("nombre")
            Log.d("MANZANAs", serieMotor)
            serialMotor.add(serieMotor)
        }
        return serialMotor
    }

