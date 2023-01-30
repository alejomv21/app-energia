package com.equitel.pruebaequitel.Sheet3

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.equitel.pruebaequitel.R
import com.equitel.pruebaequitel.Sheet2.Sheet2Activity
import com.equitel.pruebaequitel.Sheet4.Sheet4Activity
import com.equitel.pruebaequitel.databinding.ActivitySheet3Binding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException
import java.util.*

class Sheet3Activity : AppCompatActivity() {
    //firebase
    lateinit var binding : ActivitySheet3Binding
    lateinit var viewModel: Sheet3ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySheet3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,
        Sheet3ViewModelFactory(application)).get(Sheet3ViewModel::class.java)

        val tipoServicio : Array<String> = resources.getStringArray(R.array.OpcionesHoja2)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipoServicio)

        val siNo : Array<String> = resources.getStringArray(R.array.SiNo)
        val siNoadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, siNo)

        val buenoMaloNA : Array<String> = resources.getStringArray(R.array.buenoMaloNA)
        val buenoMaloNAadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, buenoMaloNA)


        val siNoNa : Array<String> = resources.getStringArray(R.array.siNoNa)
        val siNoNaAadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, siNoNa)

        val buenoMalo : Array<String> = resources.getStringArray(R.array.BuenoMalo)
        val buenoMaloadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, buenoMalo)

        val onOf : Array<String> = resources.getStringArray(R.array.OnOf)
        val onOfMaloadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, onOf)

        val manOfOut : Array<String> = resources.getStringArray(R.array.ManOfOut)
        val manOfOutadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, manOfOut)

        spinnerAdapter(adapter, siNoadapter, buenoMaloadapter, onOfMaloadapter, manOfOutadapter, buenoMaloNAadapter, siNoNaAadapter)

        //enviar
        binding.buttonEnviar.setOnClickListener {
            Toast.makeText(this, "ELEMENTOS GUARDADOS ", Toast.LENGTH_SHORT).show()
            guardarAlmacenamiento()
            val intent = Intent(this, Sheet4Activity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if(itemId == R.id.main_menu_latest){
            extraerAlmacenamiento()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun spinnerAdapter(adapter : ArrayAdapter<String>, siNoadapter: ArrayAdapter<String>, buenoMaloadapter: ArrayAdapter<String>,onOfMaloadapter: ArrayAdapter<String>,
                               manOfOutadapter: ArrayAdapter<String>, buenoMaloNAadapter: ArrayAdapter<String>, siNoNaAadapter: ArrayAdapter<String>){
        //binding.spinnnerA.setAdapter(adapter)
        binding.spinnnerB.setAdapter(adapter)
        binding.spinnnerC.setAdapter(adapter)
        binding.spinnnerD.setAdapter(adapter)
        binding.spinnnerF.setAdapter(buenoMaloNAadapter)
        binding.spinnnerG.setAdapter(buenoMaloNAadapter)
        binding.spinnnerH.setAdapter(adapter)
        binding.spinnnerI.setAdapter(siNoadapter)
        binding.spinnner2A.setAdapter(siNoNaAadapter)
        binding.spinnner2B.setAdapter(buenoMaloadapter)
        binding.spinnner2C.setAdapter(siNoNaAadapter)
        binding.spinnner2D.setAdapter(siNoNaAadapter)
        binding.spinnner3D.setAdapter(onOfMaloadapter)
        binding.spinnner3E.setAdapter(onOfMaloadapter)
        binding.spinnner3F.setAdapter(manOfOutadapter)
        binding.spinnner3G.setAdapter(adapter)
        binding.spinnner3H.setAdapter(buenoMaloNAadapter)
        binding.spinnner4A.setAdapter(siNoadapter)
        binding.spinnner4B.setAdapter(siNoadapter)
    }


    private fun guardarAlmacenamiento(){
        viewModel.extraerAlmacenamiento()
        viewModel.almacenamiento.observe(this, Observer {
            almacenamiento->
            //almacenamiento.caidaVOltaje = binding.spinnnerA.selectedItem.toString()
            almacenamiento.caidaVoltajeMedida = binding.EditCaidaVoltaje.text.toString()
            almacenamiento.presionAceite = binding.spinnnerB.selectedItem.toString()
            almacenamiento.presionAceiteMedida = binding.EditPresionAceite.text.toString()
            almacenamiento.temperaturaAgua = binding.spinnnerC.selectedItem.toString()
            almacenamiento.temperaturaAguaMedida = binding.EditTemperaturaAgua.text.toString()
            almacenamiento.voltajeAlternador = binding.spinnnerD.selectedItem.toString()
            almacenamiento.voltajeAlternadorMedida = binding.EditVoltajeAlternador.text.toString()
            almacenamiento.temperaturaAceite = binding.spinnnerF.selectedItem.toString()
            almacenamiento.temperaturaAceiteMedida = binding.EditTemperaturaAceite.text.toString()
            almacenamiento.temperaturaGases = binding.spinnnerG.selectedItem.toString()
            almacenamiento.temperaturaGasesMedida = binding.EditTemperaGasesEscape.text.toString()
            almacenamiento.indicadorRestriccion = binding.spinnnerH.selectedItem.toString()
            almacenamiento.indicadoresRestriccionMedida = binding.EditIndicadoRestrccionAire.text.toString()
            almacenamiento.oscilacionVelocidad = binding.spinnnerI.selectedItem.toString()
            almacenamiento.altaTemperaturaMotor = binding.spinnner2A.selectedItem.toString()
            almacenamiento.estadoTajetasControl = binding.spinnner2B.selectedItem.toString()
            almacenamiento.bajaPresionAceite = binding.spinnner2C.selectedItem.toString()
            almacenamiento.bajoNivelRefrigerante = binding.spinnner2D.selectedItem.toString()
            almacenamiento.voltaje1 = binding.EditfasesA1.isChecked()
            almacenamiento.voltaje1Medida = binding.EditfasesA1Medida.text.toString()
            almacenamiento.voltaje2 = binding.EditfasesA2.isChecked()
            almacenamiento.voltaje2Medida = binding.EditfasesA2Medida.text.toString()
            almacenamiento.voltaje3 = binding.EditfasesA3.isChecked()
            almacenamiento.voltaje3Medida =  binding.EditfasesA3Medida.text.toString()
            almacenamiento.corrienteAmperios1 = binding.EditfasesB1.isChecked()
            almacenamiento.corrienteAmperios1Medida = binding.EditfasesB1Medida.text.toString()
            almacenamiento.corrienteAmperios2 = binding.EditfasesB2.isChecked()
            almacenamiento.corrienteAmperios2Medida = binding.EditfasesB2Medida.text.toString()
            almacenamiento.corrienteAmperios3 = binding.EditfasesB3.isChecked()
            almacenamiento.corrienteAmperios3Medida = binding.EditfasesB3Medida.text.toString()
            almacenamiento.corrienteAmperios4 = binding.EditfasesB4.isChecked()
            almacenamiento.corrienteAmperios4Medida = binding.EditfasesB4Medida.text.toString()
            almacenamiento.posicionInterruptor = binding.spinnner3D.selectedItem.toString()
            almacenamiento.switchCargador = binding.spinnner3E.selectedItem.toString()
            almacenamiento.switchControl = binding.spinnner3F.selectedItem.toString()
            almacenamiento.frecuencia = binding.spinnner3G.selectedItem.toString()
            almacenamiento.frecuenciaMedida = binding.EditFrecuenicaMedida.text.toString()
            almacenamiento.factorPotencia = binding.spinnner3H.selectedItem.toString()
            almacenamiento.factorPotenciaMedida = binding.EditFactorPoteciaMedida.text.toString()
            almacenamiento.kilovatiosMedida = binding.EditKilovatiosMedida.text.toString()
            almacenamiento.enVacio = binding.spinnner4A.selectedItem.toString()
            almacenamiento.conCargas = binding.spinnner4B.selectedItem.toString()
            almacenamiento.conCargasMedida = binding.EditConCargasMedida.text.toString()
            almacenamiento.recomendaciones = binding.EditRecomendaciones.text.toString()
            viewModel.guardaralmacenamiento(almacenamiento)
        })
    }


    private fun extraerAlmacenamiento(){
        viewModel.extraerAlmacenamiento()
        viewModel.almacenamiento.observe(this, Observer {
                almamacenamiento->
            /*val a = almamacenamiento.caidaVOltaje.toString()
            val caidaVOltaje = spinerCondicion(a)
            binding.spinnnerA.setSelection(caidaVOltaje)*/
            val b = almamacenamiento.presionAceite.toString()
            val presionAceite = spinerCondicion(b)
            binding.spinnnerB.setSelection(presionAceite)
            val c = almamacenamiento.temperaturaAgua.toString()
            val temperaturaAgua = spinerCondicion(c)
            binding.spinnnerC.setSelection(temperaturaAgua)
            val d = almamacenamiento.voltajeAlternador.toString()
            val voltajeAlternador = spinerCondicion(d)
            binding.spinnnerD.setSelection(voltajeAlternador)
            val f = almamacenamiento.temperaturaAceite.toString()
            val temperaturaAceite = spinerCondicion(f)
            binding.spinnnerF.setSelection(temperaturaAceite)
            val g = almamacenamiento.temperaturaGases.toString()
            val temperaturaGases = spinerCondicion(g)
            binding.spinnnerG.setSelection(temperaturaGases)
            val h = almamacenamiento.indicadorRestriccion.toString()
            val indicadorRestriccion = spinerCondicion(h)
            binding.spinnnerH.setSelection(indicadorRestriccion)
            val i = almamacenamiento.oscilacionVelocidad.toString()
            val oscilacionGobernador = spinerCondicion(i)
            binding.spinnnerI.setSelection(oscilacionGobernador)
            val a2 = almamacenamiento.altaTemperaturaMotor.toString()
            val altaTemperaturaMotor = spinerCondicion(a2)
            binding.spinnner2A.setSelection(altaTemperaturaMotor)
            val b2 = almamacenamiento.estadoTajetasControl.toString()
            val estadoTajetasControl = spinerCondicion(b2)
            binding.spinnner2B.setSelection(estadoTajetasControl)
            val c2 = almamacenamiento.bajaPresionAceite.toString()
            val bajaPresionAceite = spinerCondicion(c2)
            binding.spinnner2C.setSelection(bajaPresionAceite)
            val d2 = almamacenamiento.bajoNivelRefrigerante.toString()
            val bajoNivelRefrigerante = spinerCondicion(d2)
            binding.spinnner2D.setSelection(bajoNivelRefrigerante)
            val d3 = almamacenamiento.posicionInterruptor.toString()
            val posicionInterruptor = spinerCondicion(d3)
            binding.spinnner3D.setSelection(posicionInterruptor)
            val e3 = almamacenamiento.switchCargador.toString()
            val switchCargador = spinerCondicion(e3)
            binding.spinnner3E.setSelection(switchCargador)
            val f3 = almamacenamiento.switchControl.toString()
            val switchControl = condicionManOfOut(f3)
            binding.spinnner3F.setSelection(switchControl)
            val g3 = almamacenamiento.frecuencia.toString()
            val frecuencia = spinerCondicion(g3)
            binding.spinnner3G.setSelection(frecuencia)
            val h3 = almamacenamiento.factorPotencia.toString()
            val factorPotencia = spinerCondicion(h3)
            binding.spinnner3H.setSelection(factorPotencia)
            val a4 = almamacenamiento.enVacio.toString()
            val enVacio = spinerCondicion(a4)
            binding.spinnner4A.setSelection(enVacio)
            val b4 = almamacenamiento.conCargas.toString()
            val conCargas = spinerCondicion(b4)
            binding.spinnner4B.setSelection(conCargas)

        })
    }


    private fun validarCamposCompletos(): Boolean{
        //val a = binding.spinnnerA.selectedItem.toString()
        val b = binding.spinnnerB.selectedItem.toString()
        val c = binding.spinnnerC.selectedItem.toString()
        val d = binding.spinnnerD.selectedItem.toString()
        val f=  binding.spinnnerF.selectedItem.toString()
        val g=  binding.spinnnerG.selectedItem.toString()
        val h =  binding.spinnnerH.selectedItem.toString()
        val i =  binding.spinnnerI.selectedItem.toString()
        val a2 =  binding.spinnner2A.selectedItem.toString()
        val b2=  binding.spinnner2B.selectedItem.toString()
        val c2=  binding.spinnner2C.selectedItem.toString()
        val d2 =  binding.spinnner2D.selectedItem.toString()
        val d3 = binding.spinnner3D.selectedItem.toString()
        val e3=  binding.spinnner3E.selectedItem.toString()
        val f3 =  binding.spinnner3F.selectedItem.toString()
        val g3 =  binding.spinnner3G.selectedItem.toString()
        val h3 =  binding.spinnner3H.selectedItem.toString()
        val a4 =  binding.spinnner4A.selectedItem.toString()
        val b4 =  binding.spinnner4B.selectedItem.toString()

        if(b == "" || c == "" || d == "" || f == "" || g == "" || h == ""|| i == ""
            || a2 == "" || b2 == "" || c2 == "" || d2 == "" || d3 == ""|| e3 == ""|| f3 == ""|| g3 == ""
            || h3 == ""|| a4 == ""|| b4 == ""){
            Toast.makeText(this, "faltan campos por llenar", Toast.LENGTH_SHORT).show()
            return false
        }else{
            return true
        }
    }

    private fun spinerCondicion(valor: String): Int{
        var posicion : Int = 0
        when(valor){
            "B"-> posicion = 1
            "R"-> posicion = 2
            "M"-> posicion = 3
            "NA"-> posicion = 4
        }
        return posicion
    }

    private fun condicionManOfOut(valor : String): Int{
        var posicion : Int = 0
        when(valor){
            "MAN"-> posicion = 1
            "OFF"-> posicion = 2
            "AUT"-> posicion = 3
        }
        return posicion
    }
}


