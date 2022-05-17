package com.equitel.pruebaequitel.Sheet2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.equitel.pruebaequitel.login.LoginActivity
import com.equitel.pruebaequitel.R
import com.equitel.pruebaequitel.SearchActivity
import com.equitel.pruebaequitel.Sheet3.Sheet3Activity
import com.equitel.pruebaequitel.databinding.ActivitySheet2Binding

class Sheet2Activity : AppCompatActivity() {
    lateinit var binding : ActivitySheet2Binding
    lateinit var viewModel: Sheet2ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySheet2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, Sheet2ViewModelFactory(application)).get(Sheet2ViewModel::class.java)

        /*binding.buttonEnviar.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }*/

        binding.buttonEnviar.setOnClickListener {
            println(binding.spinnnerA.selectedItem.toString())
            Log.d("limas", binding.spinnnerA.selectedItem.toString())
            val validar = validarCampos()
            if(validar == true){
                extraerDatos()
                val intent = Intent(this, Sheet3Activity::class.java)
                startActivity(intent)
            }
        }

        val tipoServicio : Array<String> = resources.getStringArray(R.array.OpcionesHoja2)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipoServicio)
        spinnerAdapter(adapter)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if(itemId == R.id.main_menu_latest){
            ultimoGuardado()
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
        binding.spinnnerJ.setAdapter(adapter)
        binding.spinnnerK.setAdapter(adapter)
        binding.spinnnerL.setAdapter(adapter)
        binding.spinnnerM.setAdapter(adapter)
        binding.spinnnerN.setAdapter(adapter)
        binding.spinnnerO.setAdapter(adapter)
        binding.spinnnerP.setAdapter(adapter)
        binding.spinnnerQ.setAdapter(adapter)
        binding.spinnnerR.setAdapter(adapter)
        binding.spinnner2A.setAdapter(adapter)
        binding.spinnner2B.setAdapter(adapter)
        binding.spinnner2C.setAdapter(adapter)
        binding.spinnner2D.setAdapter(adapter)
        binding.spinnner2E.setAdapter(adapter)
        binding.spinnner2F.setAdapter(adapter)
        binding.spinnner2G.setAdapter(adapter)
        binding.spinnner2H.setAdapter(adapter)
    }


    private fun extraerDatos(){
        viewModel.extraerAlmacenamiento()
        viewModel.almacenamiento.observe(this, Observer {
                almamacenamiento->

            almamacenamiento.nivelAceite =  binding.spinnnerA.selectedItem.toString()
            almamacenamiento.estadoRadiador =  binding.spinnnerB.selectedItem.toString()
            almamacenamiento.nivelAguaRadiador =  binding.spinnnerC.selectedItem.toString()
            almamacenamiento.aspasVentilador = binding.spinnnerD.selectedItem.toString()
            almamacenamiento.bornerBateria=  binding.spinnnerE.selectedItem.toString()
            almamacenamiento.voltajeBateria =  binding.spinnnerH.selectedItem.toString()
            almamacenamiento.cargadorFuncional =  binding.spinnnerI.selectedItem.toString()
            almamacenamiento.correasTensionadas =  binding.spinnnerJ.selectedItem.toString()
            almamacenamiento.estadoFiltroAire =  binding.spinnnerK.selectedItem.toString()
            almamacenamiento.estadoMangueras =  binding.spinnnerL.selectedItem.toString()
            almamacenamiento.estadoPrecalentador =  binding.spinnnerM.selectedItem.toString()
            almamacenamiento.estadoRacores =  binding.spinnnerN.selectedItem.toString()
            almamacenamiento.fugas =  binding.spinnnerO.selectedItem.toString()
            almamacenamiento.estadoCombustible =  binding.spinnnerP.selectedItem.toString()
            almamacenamiento.estadoTanque =  binding.spinnnerQ.selectedItem.toString()
            almamacenamiento.formaTanque =  binding.spinnnerR.selectedItem.toString()
            almamacenamiento.estadoGenerador =  binding.spinnner2A.selectedItem.toString()
            almamacenamiento.aspasVentiladorGenerador =  binding.spinnner2B.selectedItem.toString()
            almamacenamiento.conexionPotencia =  binding.spinnner2C.selectedItem.toString()
            almamacenamiento.conexionCableadocontrol =  binding.spinnner2D.selectedItem.toString()
            almamacenamiento.objetsExtrasInterior =  binding.spinnner2E.selectedItem.toString()
            almamacenamiento.puenteRectificadorGiratorio =  binding.spinnner2F.selectedItem.toString()
            almamacenamiento.estadoControl =  binding.spinnner2G.selectedItem.toString()
            almamacenamiento.estadoCuartoCabina =  binding.spinnner2H.selectedItem.toString()
            Log.d("MANZANAs", almamacenamiento.estadoControl.toString())

            viewModel.GuardarAlmacenamiento12(almamacenamiento)

        })
    }

    private fun validarCampos(): Boolean{
        val A = binding.spinnnerA.selectedItem.toString()
        val B = binding.spinnnerB.selectedItem.toString()
        val C = binding.spinnnerC.selectedItem.toString()
        val D = binding.spinnnerD.selectedItem.toString()
        val E=  binding.spinnnerE.selectedItem.toString()
        val H =  binding.spinnnerH.selectedItem.toString()
        val I =  binding.spinnnerI.selectedItem.toString()
        val J =  binding.spinnnerJ.selectedItem.toString()
        val K =  binding.spinnnerK.selectedItem.toString()
        val L =  binding.spinnnerL.selectedItem.toString()
        val M =  binding.spinnnerM.selectedItem.toString()
        val N =  binding.spinnnerN.selectedItem.toString()
        val O =  binding.spinnnerO.selectedItem.toString()
        val P =  binding.spinnnerP.selectedItem.toString()
        val Q =  binding.spinnnerQ.selectedItem.toString()
        val R =  binding.spinnnerR.selectedItem.toString()
        val A2 =  binding.spinnner2A.selectedItem.toString()
        val B2=  binding.spinnner2B.selectedItem.toString()
        val C2=  binding.spinnner2C.selectedItem.toString()
        val D2 =  binding.spinnner2D.selectedItem.toString()
        val E2 =  binding.spinnner2E.selectedItem.toString()
        val F2 =  binding.spinnner2F.selectedItem.toString()
        val G2 =  binding.spinnner2G.selectedItem.toString()
        val H2 =  binding.spinnner2H.selectedItem.toString()

        if(A == "" || B == "" || C == "" || D == "" || E == "" || H == ""|| I == ""|| J == "" || K == "" || L == "" || M == "" || N == ""
            || O == "" || P == "" || Q == ""  || R == "" || A2 == "" || B2 == "" || C2 == "" || D2 == "" || E2 == "" || F2 == "" || G2 == "" ||
            H2 == ""){
            Toast.makeText(this, "faltan campos por llenar", Toast.LENGTH_SHORT).show()
            return false
        }else{
            return true
        }
    }

    private fun ultimoGuardado(){
        viewModel.extraerAlmacenamiento()
        viewModel.almacenamiento.observe(this, Observer {
                almamacenamiento->

            val a = almamacenamiento.nivelAceite.toString()
            val nivelAceite = condicion(a)
            binding.spinnnerA.setSelection(nivelAceite)
            val b = almamacenamiento.estadoRadiador.toString()
            val estadoRadiador = condicion(b)
            binding.spinnnerB.setSelection(estadoRadiador)
            val c = almamacenamiento.nivelAguaRadiador.toString()
            val nivelAguaRadiador = condicion(c)
            binding.spinnnerC.setSelection(nivelAguaRadiador)
            val d = almamacenamiento.aspasVentilador.toString()
            val aspasVentilador = condicion(d)
            binding.spinnnerD.setSelection(aspasVentilador)
            val e = almamacenamiento.bornerBateria.toString()
            val bornerBateria = condicion(e)
            binding.spinnnerE.setSelection(bornerBateria)
            val h = almamacenamiento.voltajeBateria.toString()
            val voltajeBateria = condicion(h)
            binding.spinnnerH.setSelection(voltajeBateria)
            val i = almamacenamiento.cargadorFuncional.toString()
            val cargadorFuncional = condicion(i)
            binding.spinnnerI.setSelection(cargadorFuncional)
            val j = almamacenamiento.correasTensionadas.toString()
            val correasTensionadas = condicion(j)
            binding.spinnnerJ.setSelection(correasTensionadas)
            val k = almamacenamiento.estadoFiltroAire.toString()
            val estadoFiltroAire = condicion(k)
            binding.spinnnerK.setSelection(estadoFiltroAire)
            val l = almamacenamiento.estadoMangueras.toString()
            val estadoMangueras = condicion(l)
            binding.spinnnerL.setSelection(estadoMangueras)
            val m = almamacenamiento.estadoPrecalentador.toString()
            val estadoPrecalentador = condicion(m)
            binding.spinnnerM.setSelection(estadoPrecalentador)
            val n = almamacenamiento.estadoRacores.toString()
            val estadoRacores = condicion(n)
            binding.spinnnerN.setSelection(estadoRacores)
            val o = almamacenamiento.fugas.toString()
            val fugas = condicion(o)
            binding.spinnnerO.setSelection(fugas)
            val p = almamacenamiento.estadoCombustible.toString()
            val estadoCombustible = condicion(p)
            binding.spinnnerP.setSelection(estadoCombustible)
            val q = almamacenamiento.estadoTanque.toString()
            val estadoTanque = condicion(q)
            binding.spinnnerQ.setSelection(estadoTanque)
            val r = almamacenamiento.formaTanque.toString()
            val formaTanque = condicion(r)
            binding.spinnnerR.setSelection(formaTanque)
            val a2 = almamacenamiento.estadoGenerador.toString()
            val estadoGenerador = condicion(a2)
            binding.spinnner2A.setSelection(estadoGenerador)
            val b2 = almamacenamiento.aspasVentiladorGenerador.toString()
            val aspasVentiladorGenerador = condicion(b2)
            binding.spinnner2B.setSelection(aspasVentiladorGenerador)
            val c2 = almamacenamiento.conexionPotencia.toString()
            val conexionPotencia = condicion(c2)
            binding.spinnner2C.setSelection(conexionPotencia)
            val d2 = almamacenamiento.conexionCableadocontrol.toString()
            val conexionCableadocontrol = condicion(d2)
            binding.spinnner2D.setSelection(conexionCableadocontrol)
            val e2 = almamacenamiento.objetsExtrasInterior.toString()
            val objetsExtrasInterior = condicion(e2)
            binding.spinnner2E.setSelection(objetsExtrasInterior)
            val f2 = almamacenamiento.puenteRectificadorGiratorio.toString()
            val puenteRectificadorGiratorio = condicion(f2)
            binding.spinnner2F.setSelection(puenteRectificadorGiratorio)
            val g2 = almamacenamiento.estadoControl.toString()
            val estadoControl = condicion(g2)
            binding.spinnner2G.setSelection(estadoControl)
            val h2 = almamacenamiento.estadoCuartoCabina.toString()
            val estadoCuartoCabina = condicion(h2)
            binding.spinnner2H.setSelection(estadoCuartoCabina)

        })
    }

    private fun condicion(valor : String): Int{
        var posicion : Int = 0
        when(valor){
            "B"-> posicion = 1
            "R"-> posicion = 2
            "M"-> posicion = 3
        }
        return posicion
    }
}