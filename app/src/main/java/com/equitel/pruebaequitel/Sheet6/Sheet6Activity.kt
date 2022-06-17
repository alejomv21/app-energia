package com.equitel.pruebaequitel.Sheet6

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.equitel.pruebaequitel.Almacenamiento
import com.equitel.pruebaequitel.R
import com.equitel.pruebaequitel.Sheet4.Sheet4Activity
import com.equitel.pruebaequitel.Sheet8.Sheet8Activity
import com.equitel.pruebaequitel.Signatures.SignatureActivity
import com.equitel.pruebaequitel.databinding.ActivitySheet6Binding
import com.github.gcacace.signaturepad.views.SignaturePad
import java.io.ByteArrayOutputStream

class Sheet6Activity : AppCompatActivity() {
    companion object {
        const val EQ_KEY = "earthquake"
    }
    lateinit var binding : ActivitySheet6Binding
    lateinit var viewModel: Sheet6ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySheet6Binding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, Sheet6ViewModelFactory(application)).get(Sheet6ViewModel::class.java)

        val siNo : Array<String> = resources.getStringArray(R.array.SiNo)
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_list_item_1, siNo)

        val vHs : Array<String> = resources.getStringArray(R.array.vHs)
        val adaptervhs = ArrayAdapter(this, android.R.layout.simple_list_item_1, vHs)

        val aDisyuntores : Array<String> = resources.getStringArray(R.array.vHs)
        val adapterDisyuntores = ArrayAdapter(this, android.R.layout.simple_list_item_1, aDisyuntores)

        val aPrecalentador : Array<String> = resources.getStringArray(R.array.precalentador)
        val adapterPrecalentador= ArrayAdapter(this, android.R.layout.simple_list_item_1, aPrecalentador)

        val aVoltaje : Array<String> = resources.getStringArray(R.array.voltaje)
        val adapterVoltaje= ArrayAdapter(this, android.R.layout.simple_list_item_1, aVoltaje)

        val tipoServicio : Array<String> = resources.getStringArray(R.array.OpcionesHoja2)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipoServicio)


        spinnerAdapter(adapter, adapter1,adaptervhs, adapterDisyuntores, adapterPrecalentador, adapterVoltaje)
        //spinerHoja3(buenoMaloadapter, onOfMaloadapter, manOfOutadapter, adapter, adapter1)

        val almacenamiento = intent?.extras?.getParcelable<Almacenamiento>(EQ_KEY)!!
        insertarHojas(almacenamiento)

        //firma()

        binding.ButtonEnviar.setOnClickListener {
            Toast.makeText(this, "SIGUIENTE", Toast.LENGTH_SHORT).show()
            guardarAlmacenamiento(almacenamiento)
        }




    }

    private fun spinnerAdapter(adapter : ArrayAdapter<String>, adapter1: ArrayAdapter<String>, adaptervhs: ArrayAdapter<String>, adapterDisyuntores : ArrayAdapter<String>, adapterPrecalentador: ArrayAdapter<String>,
                               adapterVoltaje: ArrayAdapter<String>){
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
        binding.spinnnerNDuplicado.setAdapter(adapter)
        binding.spinnnerN.setAdapter(adapter)
        binding.spinnnerO.setAdapter(adapter1)
        binding.spinnnerP.setAdapter(adapter)
        binding.spinnnerQ.setAdapter(adaptervhs)
        binding.spinnnerR.setAdapter(adapter)
        binding.spinnner2A.setAdapter(adapter)
        binding.spinnner2B.setAdapter(adapter)
        binding.spinnner2C.setAdapter(adapter)
        binding.spinnner2D.setAdapter(adapter)
        binding.spinnner2E.setAdapter(adapter)
        binding.spinnner2F.setAdapter(adapter)
        binding.spinnner2G.setAdapter(adapter)
        binding.spinnner2H.setAdapter(adapter)
        binding.spinnner3C.setAdapter(adapterDisyuntores)
        binding.spinnner3D.setAdapter(adapter)
        binding.spinnner3E.setAdapter(adapter1)
        binding.spinnner3G.setAdapter(adapterPrecalentador)
        binding.spinnner3H.setAdapter(adapterVoltaje)
        binding.spinnner3I.setAdapter(adapter)
        binding.spinnner3J.setAdapter(adapter)

    }



    private fun insertarHojas(almacenamiento: Almacenamiento){
        binding.Editciudad.setText(almacenamiento.sede)
        binding.EditSede.setText(almacenamiento.sede)
        binding.Editciudad.setText(almacenamiento.ciudad)
        binding.EditFecha.setText(almacenamiento.fecha)
        binding.EditOCliente.setText(almacenamiento.cliente.toString())
        binding.EditDir.setText(almacenamiento.direccion.toString())
        binding.EditSede.setText(almacenamiento.sede.toString())
        binding.EditServicioSolicitado.setText(almacenamiento.servicioSolicitadoPor.toString())
        binding.EditCargo.setText(almacenamiento.cargo.toString())
        binding.EditEmail.setText(almacenamiento.email.toString())
        binding.EditCell.setText(almacenamiento.tel.toString())
        binding.EditMarcaMotor.setText(almacenamiento.marcaMotor.toString())
        binding.EditMarcaGen.setText(almacenamiento.marcaGenerador.toString())
        binding.EditMarcaPlanta.setText(almacenamiento.marcaPlanta.toString())
        binding.EditModMotor.setText(almacenamiento.modMotor.toString())
        binding.EditModGen.setText(almacenamiento.modGenerador.toString())
        binding.EditModPlanta.setText(almacenamiento.modPlanta.toString())
        binding.EditSerialMotor.setText(almacenamiento.serialMotor.toString())
        binding.EditSerialGenerador.setText(almacenamiento.serialGenerador.toString())
        binding.EditSerialPlanta.setText(almacenamiento.serialPlanta.toString())
        binding.EditClp.setText(almacenamiento.clp.toString())
        binding.EditTipoBateria.setText(almacenamiento.tipoBateria.toString())
        binding.EditCantidad.setText(almacenamiento.cantidad.toString())
        binding.EditKW.setText(almacenamiento.kw.toString())
        binding.EditSpec.setText(almacenamiento.spec.toString())
        binding.EditNArranques.setText(almacenamiento.nArranques.toString())
        binding.EditHorasMotor.setText(almacenamiento.horasMotor.toString())
        binding.EditTipoControl.setText(almacenamiento.tipoControl.toString())
        binding.EditHorasControl.setText(almacenamiento.horasControl.toString())
        binding.EditTecnicosCargo1.setText(almacenamiento.tecnicos.toString())
        binding.EditTecnicosCargo2.setText(almacenamiento.tecnicos.toString())
        binding.EditPromotionID.setText(almacenamiento.promotionID.toString())
        binding.EditMotivoVisita.setText(almacenamiento.motivoVisita.toString())
        val a = almacenamiento.nivelAceite.toString()
        val nivelAceite = condicion(a)
        binding.spinnnerA.setSelection(nivelAceite)
        binding.EditNivelAceiteMedida.setText(almacenamiento.nivelAceiteMedida.toString())
        val b = almacenamiento.estadoRadiador.toString()
        val estadoRadiador = condicion(b)
        binding.spinnnerB.setSelection(estadoRadiador)
        val c = almacenamiento.nivelAguaRadiador.toString()
        val nivelAguaRadiador = condicion(c)
        binding.spinnnerC.setSelection(nivelAguaRadiador)
        val d = almacenamiento.aspasVentilador.toString()
        val aspasVentilador = condicion(d)
        binding.spinnnerD.setSelection(aspasVentilador)
        val e = almacenamiento.bornerBateria.toString()
        val bornerBateria = condicion(e)
        binding.spinnnerE.setSelection(bornerBateria)

        val h = almacenamiento.voltajeBateria.toString()
        val voltajeBateria = condicion(h)
        binding.spinnnerH.setSelection(voltajeBateria)
        binding.EditVoltajeBateria.setText(almacenamiento.voltajeBateriaMedida.toString())
        val i = almacenamiento.cargadorFuncional.toString()
        val cargadorFuncional = condicion(i)
        binding.spinnnerI.setSelection(cargadorFuncional)

        binding.EditCargadorFuncional.setText(almacenamiento.cargadorFuncionaMedida.toString())
        val j = almacenamiento.correasTensionadas.toString()
        val correasTensionadas = condicion(j)
        binding.spinnnerJ.setSelection(correasTensionadas)
        val k = almacenamiento.estadoFiltroAire.toString()
        val estadoFiltroAire = condicion(k)
        binding.spinnnerK.setSelection(estadoFiltroAire)
        val l = almacenamiento.estadoMangueras.toString()
        val estadoMangueras = condicion(l)
        binding.spinnnerL.setSelection(estadoMangueras)
        val m = almacenamiento.estadoPrecalentador.toString()
        val estadoPrecalentador = condicion(m)
        binding.spinnnerM.setSelection(estadoPrecalentador)

        val mDobles = almacenamiento.estadoTapaRadiador.toString()
        val estadoTapaRadiador = condicion(mDobles)
        binding.spinnnerNDuplicado.setSelection(estadoTapaRadiador)

        var oDoble = almacenamiento.presionTapaRadiador.toString()
        val presionTapaRadiador = condicion(oDoble)
        binding.spinnnerODuplicado.setSelection(presionTapaRadiador)

        binding.EditPDuplicado.setText(almacenamiento.dimensiones)

        val n = almacenamiento.estadoRacores.toString()
        val estadoRacores = condicion(n)
        binding.spinnnerN.setSelection(estadoRacores)
        val o = almacenamiento.fugas.toString()
        val fugas = condicion(o)
        binding.spinnnerO.setSelection(fugas)
        val p = almacenamiento.estadoCombustible.toString()
        val estadoCombustible = condicion(p)
        binding.spinnnerP.setSelection(estadoCombustible)

        binding.EditEstadoCombustible.setText(almacenamiento.estadoCombustibleMedida)

        val q = almacenamiento.estadoTanque.toString()
        val estadoTanque = condicion(q)
        binding.spinnnerQ.setSelection(estadoTanque)

        binding.EditEstadoTanque.setText(almacenamiento.estadoTanqueMedida)
        val r = almacenamiento.formaTanque.toString()
        val formaTanque = condicion(r)
        binding.spinnnerR.setSelection(formaTanque)
        val a2 = almacenamiento.estadoGenerador.toString()
        val estadoGenerador = condicion(a2)
        binding.spinnner2A.setSelection(estadoGenerador)
        val b2 = almacenamiento.aspasVentiladorGenerador.toString()
        val aspasVentiladorGenerador = condicion(b2)
        binding.spinnner2B.setSelection(aspasVentiladorGenerador)
        val c2 = almacenamiento.conexionPotencia.toString()
        val conexionPotencia = condicion(c2)
        binding.spinnner2C.setSelection(conexionPotencia)
        val d2 = almacenamiento.conexionCableadocontrol.toString()
        val conexionCableadocontrol = condicion(d2)
        binding.spinnner2D.setSelection(conexionCableadocontrol)
        val e2 = almacenamiento.objetsExtrasInterior.toString()
        val objetsExtrasInterior = condicion(e2)
        binding.spinnner2E.setSelection(objetsExtrasInterior)
        val f2 = almacenamiento.puenteRectificadorGiratorio.toString()
        val puenteRectificadorGiratorio = condicion(f2)
        binding.spinnner2F.setSelection(puenteRectificadorGiratorio)
        val g2 = almacenamiento.estadoControl.toString()
        val estadoControl = condicion(g2)
        binding.spinnner2G.setSelection(estadoControl)
        val h2 = almacenamiento.estadoCuartoCabina.toString()
        val estadoCuartoCabina = condicion(h2)
        binding.spinnner2H.setSelection(estadoCuartoCabina)

        binding.Edit3A.setText(almacenamiento.marcaATS.toString())
        binding.Edit3B.setText(almacenamiento.modelosATS.toString())

        val c3 = almacenamiento.tipoDisyuntores.toString()
        val tipoDisyuntores = condicion(c3)
        binding.spinnner3C.setSelection(tipoDisyuntores)

        val d3 = almacenamiento.funcionamientoATS.toString()
        val funcionamientoATS = condicion(d3)
        binding.spinnner3D.setSelection(funcionamientoATS)

        val e3 = almacenamiento.poseePrecalentador.toString()
        val poseePrecalentador = condicion(e3)
        binding.spinnner3E.setSelection(poseePrecalentador)

        binding.EditTrabajoRealizado.setText(almacenamiento.trabajoRealizado.toString())

    }


    private fun guardarAlmacenamiento(almacenamiento: Almacenamiento){
        almacenamiento.cliente = binding.EditOCliente.text.toString()
        almacenamiento.direccion = binding.EditDir.text.toString()
        almacenamiento.servicioSolicitadoPor =  binding.EditServicioSolicitado.text.toString()
        almacenamiento.cargo = binding.EditCargo.text.toString()
        almacenamiento.email = binding.EditEmail.text.toString()
        almacenamiento.tel = binding.EditCell.text.toString()
        almacenamiento.sede = binding.EditSede.text.toString()
        almacenamiento.ciudad = binding.Editciudad.text.toString()
        almacenamiento.marcaMotor = binding.EditMarcaMotor.text.toString()
        almacenamiento.marcaGenerador = binding.EditMarcaGen.text.toString()
        almacenamiento.marcaPlanta = binding.EditMarcaPlanta.text.toString()
        almacenamiento.modPlanta = binding.EditModPlanta.text.toString()
        almacenamiento.modMotor = binding.EditModMotor.text.toString()
        almacenamiento.modGenerador = binding.EditModGen.text.toString()
        almacenamiento.serialMotor = binding.EditSerialMotor.text.toString()
        almacenamiento.serialGenerador = binding.EditSerialGenerador.text.toString()
        almacenamiento.serialPlanta = binding.EditSerialPlanta.text.toString()
        almacenamiento.clp = binding.EditClp.text.toString()
        almacenamiento.tipoBateria = binding.EditTipoBateria.text.toString()
        almacenamiento.cantidad = binding.EditCantidad.text.toString()
        almacenamiento.kw = binding.EditKW.text.toString()
        almacenamiento.spec = binding.EditSpec.text.toString()
        almacenamiento.nArranques = binding.EditNArranques.text.toString()
        almacenamiento.horasMotor = binding.EditHorasMotor.text.toString()
        almacenamiento.tipoControl = binding.EditTipoControl.text.toString()
        almacenamiento.horasControl = binding.EditHorasControl.text.toString()
        almacenamiento.tecnicos = binding.EditTecnicosCargo1.text.toString()
        almacenamiento.promotionID = binding.EditPromotionID.text.toString()
        almacenamiento.fecha = binding.EditFecha.text.toString()
        almacenamiento.motivoVisita = binding.EditMotivoVisita.text.toString()

        //hoja2
        almacenamiento.nivelAceite =  binding.spinnnerA.selectedItem.toString()
        almacenamiento.nivelAceiteMedida = binding.EditNivelAceiteMedida.text.toString()
        almacenamiento.estadoRadiador =  binding.spinnnerB.selectedItem.toString()
        almacenamiento.nivelAguaRadiador =  binding.spinnnerC.selectedItem.toString()
        almacenamiento.aspasVentilador = binding.spinnnerD.selectedItem.toString()
        almacenamiento.bornerBateria=  binding.spinnnerE.selectedItem.toString()
        almacenamiento.voltajeBateria =  binding.spinnnerH.selectedItem.toString()
        almacenamiento.voltajeBateriaMedida =  binding.EditVoltajeBateria.text.toString()
        almacenamiento.cargadorFuncional =  binding.spinnnerI.selectedItem.toString()
        almacenamiento.cargadorFuncionaMedida =  binding.EditCargadorFuncional.text.toString()
        almacenamiento.correasTensionadas =  binding.spinnnerJ.selectedItem.toString()
        almacenamiento.estadoFiltroAire =  binding.spinnnerK.selectedItem.toString()
        almacenamiento.estadoMangueras =  binding.spinnnerL.selectedItem.toString()
        almacenamiento.estadoPrecalentador =  binding.spinnnerM.selectedItem.toString()
        almacenamiento.estadoRacores =  binding.spinnnerN.selectedItem.toString()
        almacenamiento.fugas =  binding.spinnnerO.selectedItem.toString()
        almacenamiento.estadoCombustible =  binding.spinnnerP.selectedItem.toString()
        almacenamiento.estadoCombustibleMedida =  binding.EditEstadoCombustible.text.toString()
        almacenamiento.estadoTanque =  binding.spinnnerQ.selectedItem.toString()
        almacenamiento.estadoTanqueMedida =  binding.EditEstadoTanque.text.toString()
        almacenamiento.formaTanque =  binding.spinnnerR.selectedItem.toString()
        almacenamiento.estadoGenerador =  binding.spinnner2A.selectedItem.toString()
        almacenamiento.aspasVentiladorGenerador =  binding.spinnner2B.selectedItem.toString()
        almacenamiento.conexionPotencia =  binding.spinnner2C.selectedItem.toString()
        almacenamiento.conexionCableadocontrol =  binding.spinnner2D.selectedItem.toString()
        almacenamiento.objetsExtrasInterior =  binding.spinnner2E.selectedItem.toString()
        almacenamiento.puenteRectificadorGiratorio =  binding.spinnner2F.selectedItem.toString()
        almacenamiento.estadoControl =  binding.spinnner2G.selectedItem.toString()
        almacenamiento.estadoCuartoCabina =  binding.spinnner2H.selectedItem.toString()
        almacenamiento.marcaATS = binding.Edit3A.text.toString()
        almacenamiento.modelosATS = binding.Edit3B.text.toString()
        almacenamiento.tipoDisyuntores =  binding.spinnner3C.selectedItem.toString()
        almacenamiento.funcionamientoATS =  binding.spinnner3D.selectedItem.toString()
        almacenamiento.poseePrecalentador =  binding.spinnner3E.selectedItem.toString()
        almacenamiento.modeloPrecalentador =  binding.spinnner3G.selectedItem.toString()
        almacenamiento.voltajeOperacion =  binding.spinnner3H.selectedItem.toString()
        almacenamiento.estadoPrecalentadorATS =  binding.spinnner3I.selectedItem.toString()
        almacenamiento.estadoManguerasATS =  binding.spinnner3J.selectedItem.toString()
        almacenamiento.trabajoRealizado = binding.EditTrabajoRealizado.text.toString()

        viewModel.guardaralmacenamiento(almacenamiento)
        openDetailActivity(almacenamiento)
    }

    private fun openDetailActivity(almacenamiento: Almacenamiento) {
        val intent = Intent(this, Sheet8Activity::class.java)
        intent.putExtra(Sheet8Activity.EQ_KEYS, almacenamiento)
        startActivity(intent)
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

    private fun condicionSiNo(valor : String): Int{
        var posicion : Int = 0
        when(valor){
            "Sí"-> posicion = 1
            "No"-> posicion = 2
        }
        return posicion
    }

    private fun condicionBM(valor : String): Int{
        var posicion : Int = 0
        when(valor){
            "B"-> posicion = 1
            "M"-> posicion = 2
        }
        return posicion
    }

    private fun condicionOnOf(valor : String): Int{
        var posicion : Int = 0
        when(valor){
            "ON"-> posicion = 1
            "OF"-> posicion = 2
        }
        return posicion
    }

    private fun condicionManOfOut(valor : String): Int{
        var posicion : Int = 0
        when(valor){
            "MAN"-> posicion = 1
            "OF"-> posicion = 2
            "OUT"-> posicion = 3
        }
        return posicion
    }



}