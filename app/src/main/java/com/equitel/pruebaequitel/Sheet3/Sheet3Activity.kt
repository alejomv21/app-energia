package com.equitel.pruebaequitel.Sheet3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.equitel.pruebaequitel.R
import com.equitel.pruebaequitel.Sheet2.Sheet2Activity
import com.equitel.pruebaequitel.Sheet4.Sheet4Activity
import com.equitel.pruebaequitel.databinding.ActivitySheet3Binding

class Sheet3Activity : AppCompatActivity() {
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
        spinnerAdapter(adapter)
        
        binding.buttonEnviar.setOnClickListener {
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

    private fun spinnerAdapter(adapter : ArrayAdapter<String>){
        binding.spinnnerA.setAdapter(adapter)
        binding.spinnnerB.setAdapter(adapter)
        binding.spinnnerC.setAdapter(adapter)
        binding.spinnnerD.setAdapter(adapter)
        binding.spinnnerE.setAdapter(adapter)
        binding.spinnnerF.setAdapter(adapter)
        binding.spinnnerG.setAdapter(adapter)
        binding.spinnnerH.setAdapter(adapter)
        binding.spinnnerI.setAdapter(adapter)
        binding.spinnner2A.setAdapter(adapter)
        binding.spinnner2B.setAdapter(adapter)
        binding.spinnner2C.setAdapter(adapter)
        binding.spinnner2D.setAdapter(adapter)
        binding.spinnner3C.setAdapter(adapter)
        binding.spinnner3D.setAdapter(adapter)
        binding.spinnner3E.setAdapter(adapter)
        binding.spinnner3F.setAdapter(adapter)
        binding.spinnner3G.setAdapter(adapter)
        binding.spinnner3H.setAdapter(adapter)
        binding.spinnner3I.setAdapter(adapter)
        binding.spinnner3J.setAdapter(adapter)
        binding.spinnner4A.setAdapter(adapter)
        binding.spinnner4B.setAdapter(adapter)
    }


    private fun guardarAlmacenamiento(){
        viewModel.extraerAlmacenamiento()
        viewModel.almacenamiento.observe(this, Observer {
            almacenamiento->
            almacenamiento.caidaVOltaje = binding.spinnnerA.selectedItem.toString()
            almacenamiento.presionAceite = binding.spinnnerB.selectedItem.toString()
            almacenamiento.temperaturaAgua = binding.spinnnerC.selectedItem.toString()
            almacenamiento.voltajeAlternador = binding.spinnnerD.selectedItem.toString()
            almacenamiento.instrumentoTablero = binding.spinnnerE.selectedItem.toString()
            almacenamiento.temperaturaAceite = binding.spinnnerF.selectedItem.toString()
            almacenamiento.temperaturaGases = binding.spinnnerG.selectedItem.toString()
            almacenamiento.indicadorRestriccion = binding.spinnnerH.selectedItem.toString()
            almacenamiento.oscilacionGobernador = binding.spinnnerI.selectedItem.toString()
            almacenamiento.altaTemperaturaMotor = binding.spinnner2A.selectedItem.toString()
            almacenamiento.sobreRevoluciones = binding.spinnner2B.selectedItem.toString()
            almacenamiento.bajaPresionAceite = binding.spinnner2C.selectedItem.toString()
            almacenamiento.bajoNivelRefrigerante = binding.spinnner2D.selectedItem.toString()
            almacenamiento.chequeoVolt = binding.spinnner3C.selectedItem.toString()
            almacenamiento.posicionInterruptor = binding.spinnner3D.selectedItem.toString()
            almacenamiento.switchCargador = binding.spinnner3E.selectedItem.toString()
            almacenamiento.switchControl = binding.spinnner3F.selectedItem.toString()
            almacenamiento.frecuencia = binding.spinnner3G.selectedItem.toString()
            almacenamiento.factorPotencia = binding.spinnner3H.selectedItem.toString()
            almacenamiento.kilovatios = binding.spinnner3I.selectedItem.toString()
            almacenamiento.simularFalla = binding.spinnner3J.selectedItem.toString()
            almacenamiento.enVacio = binding.spinnner4A.selectedItem.toString()
            almacenamiento.conCargas = binding.spinnner4B.selectedItem.toString()

            viewModel.guardaralmacenamiento(almacenamiento)
        })
    }


    private fun extraerAlmacenamiento(){
        viewModel.extraerAlmacenamiento()
        viewModel.almacenamiento.observe(this, Observer {
                almamacenamiento->
            val a = almamacenamiento.caidaVOltaje.toString()
            val caidaVOltaje = spinerCondicion(a)
            binding.spinnnerA.setSelection(caidaVOltaje)
            val b = almamacenamiento.presionAceite.toString()
            val presionAceite = spinerCondicion(b)
            binding.spinnnerB.setSelection(presionAceite)
            val c = almamacenamiento.temperaturaAgua.toString()
            val temperaturaAgua = spinerCondicion(c)
            binding.spinnnerC.setSelection(temperaturaAgua)
            val d = almamacenamiento.voltajeAlternador.toString()
            val voltajeAlternador = spinerCondicion(d)
            binding.spinnnerD.setSelection(voltajeAlternador)
            val e = almamacenamiento.instrumentoTablero.toString()
            val instrumentoTablero = spinerCondicion(e)
            binding.spinnnerE.setSelection(instrumentoTablero)
            val f = almamacenamiento.temperaturaAceite.toString()
            val temperaturaAceite = spinerCondicion(f)
            binding.spinnnerF.setSelection(temperaturaAceite)
            val g = almamacenamiento.temperaturaGases.toString()
            val temperaturaGases = spinerCondicion(g)
            binding.spinnnerG.setSelection(temperaturaGases)
            val h = almamacenamiento.indicadorRestriccion.toString()
            val indicadorRestriccion = spinerCondicion(h)
            binding.spinnnerH.setSelection(indicadorRestriccion)
            val i = almamacenamiento.oscilacionGobernador.toString()
            val oscilacionGobernador = spinerCondicion(i)
            binding.spinnnerI.setSelection(oscilacionGobernador)
            val a2 = almamacenamiento.altaTemperaturaMotor.toString()
            val altaTemperaturaMotor = spinerCondicion(a2)
            binding.spinnner2A.setSelection(altaTemperaturaMotor)
            val b2 = almamacenamiento.sobreRevoluciones.toString()
            val sobreRevoluciones = spinerCondicion(b2)
            binding.spinnner2B.setSelection(sobreRevoluciones)
            val c2 = almamacenamiento.bajaPresionAceite.toString()
            val bajaPresionAceite = spinerCondicion(c2)
            binding.spinnner2C.setSelection(bajaPresionAceite)
            val d2 = almamacenamiento.bajoNivelRefrigerante.toString()
            val bajoNivelRefrigerante = spinerCondicion(d2)
            binding.spinnner2D.setSelection(bajoNivelRefrigerante)
            val c3 = almamacenamiento.chequeoVolt.toString()
            val chequeoVolt = spinerCondicion(c3)
            binding.spinnner3C.setSelection(chequeoVolt)
            val d3 = almamacenamiento.posicionInterruptor.toString()
            val posicionInterruptor = spinerCondicion(d3)
            binding.spinnner3D.setSelection(posicionInterruptor)
            val e3 = almamacenamiento.switchCargador.toString()
            val switchCargador = spinerCondicion(e3)
            binding.spinnner3E.setSelection(switchCargador)
            val f3 = almamacenamiento.switchControl.toString()
            val switchControl = spinerCondicion(f3)
            binding.spinnner3F.setSelection(switchControl)
            val g3 = almamacenamiento.frecuencia.toString()
            val frecuencia = spinerCondicion(g3)
            binding.spinnner3G.setSelection(frecuencia)
            val h3 = almamacenamiento.factorPotencia.toString()
            val factorPotencia = spinerCondicion(h3)
            binding.spinnner3H.setSelection(factorPotencia)
            val i3 = almamacenamiento.kilovatios.toString()
            val kilovatios = spinerCondicion(i3)
            binding.spinnner3I.setSelection(kilovatios)
            val j3 = almamacenamiento.simularFalla.toString()
            val simularFalla = spinerCondicion(j3)
            binding.spinnner3J.setSelection(simularFalla)
            val a4 = almamacenamiento.enVacio.toString()
            val enVacio = spinerCondicion(a4)
            binding.spinnner4A.setSelection(enVacio)
            val b4 = almamacenamiento.conCargas.toString()
            val conCargas = spinerCondicion(b4)
            binding.spinnner4B.setSelection(conCargas)

        })
    }


    private fun validarCamposCompletos(): Boolean{
        val a = binding.spinnnerA.selectedItem.toString()
        val b = binding.spinnnerB.selectedItem.toString()
        val c = binding.spinnnerC.selectedItem.toString()
        val d = binding.spinnnerD.selectedItem.toString()
        val e=  binding.spinnnerE.selectedItem.toString()
        val f=  binding.spinnnerF.selectedItem.toString()
        val g=  binding.spinnnerG.selectedItem.toString()
        val h =  binding.spinnnerH.selectedItem.toString()
        val i =  binding.spinnnerI.selectedItem.toString()
        val a2 =  binding.spinnner2A.selectedItem.toString()
        val b2=  binding.spinnner2B.selectedItem.toString()
        val c2=  binding.spinnner2C.selectedItem.toString()
        val d2 =  binding.spinnner2D.selectedItem.toString()
        val c3 = binding.spinnner3C.selectedItem.toString()
        val d3 = binding.spinnner3D.selectedItem.toString()
        val e3=  binding.spinnner3E.selectedItem.toString()
        val f3 =  binding.spinnner3F.selectedItem.toString()
        val g3 =  binding.spinnner3G.selectedItem.toString()
        val h3 =  binding.spinnner3H.selectedItem.toString()
        val i3 =  binding.spinnner3J.selectedItem.toString()
        val j3 =  binding.spinnner3I.selectedItem.toString()
        val a4 =  binding.spinnner4A.selectedItem.toString()
        val b4 =  binding.spinnner4B.selectedItem.toString()

        if(a == "" || b == "" || c == "" || d == "" || e == "" || f == "" || g == "" || h == ""|| i == ""
            || a2 == "" || b2 == "" || c2 == "" || d2 == "" || c3 == "" || d3 == ""|| e3 == ""|| f3 == ""|| g3 == ""
            || h3 == ""|| i3 == ""|| j3 == ""|| a4 == ""|| b4 == ""){
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
        }
        return posicion
    }
}


