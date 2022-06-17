package com.equitel.pruebaequitel.Sheet8

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.equitel.pruebaequitel.Almacenamiento
import com.equitel.pruebaequitel.R
import com.equitel.pruebaequitel.databinding.ActivitySheet8Binding
import com.github.gcacace.signaturepad.views.SignaturePad
import java.io.ByteArrayOutputStream

class Sheet8Activity : AppCompatActivity() {
    companion object {
        const val EQ_KEYS = "alejo"
    }
    lateinit var binding : ActivitySheet8Binding
    lateinit var viewModel: Sheet8ViewModel
    private var mSignaturePad: SignaturePad? = null
    private var mClearButton: Button? = null
    private var mSaveButton: Button? = null

    private var mSignaturePadTecnico: SignaturePad? = null
    private var mClearButtonTecnico: Button? = null
    private var mSaveButtonTecnico: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySheet8Binding.inflate(layoutInflater)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, Sheet8ViewModelFactory(application)).get(Sheet8ViewModel::class.java)

        val almacenamiento = intent?.extras?.getParcelable<Almacenamiento>(EQ_KEYS)!!

        val buenoMalo : Array<String> = resources.getStringArray(R.array.BuenoMalo)
        val buenoMaloadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, buenoMalo)

        val tiempo : Array<String> = resources.getStringArray(R.array.tiempo)
        val timepoadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tiempo)

        val onOf : Array<String> = resources.getStringArray(R.array.OnOf)
        val onOfMaloadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, onOf)

        val manOfOut : Array<String> = resources.getStringArray(R.array.ManOfOut)
        val manOfOutadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, manOfOut)

        val tipoServicio : Array<String> = resources.getStringArray(R.array.OpcionesHoja2)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipoServicio)

        val siNo : Array<String> = resources.getStringArray(R.array.SiNo)
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_list_item_1, siNo)

        spinerHoja3(adapter, buenoMaloadapter, onOfMaloadapter, manOfOutadapter, adapter1, timepoadapter)


        insertar(almacenamiento)

        mSignaturePad = binding.signaturePad as SignaturePad
        mClearButton = binding.clearButton as Button
        mSaveButton = binding.saveButton as Button
        mClearButton!!.setOnClickListener { mSignaturePad!!.clear() }
        mSaveButton!!.setOnClickListener {
            Toast.makeText(this, "FIRMA DIGITAL GUARDADA", Toast.LENGTH_SHORT).show()
            val signatureBitmap = mSignaturePad!!.signatureBitmap
            guardarData(signatureBitmap, almacenamiento)
        }

        mSignaturePadTecnico = binding.signaturePadTecnico as SignaturePad
        mClearButtonTecnico = binding.clearButtonTecnico as Button
        mSaveButtonTecnico = binding.saveButtonTecnico as Button
        mClearButtonTecnico!!.setOnClickListener { mSignaturePadTecnico!!.clear() }
        mSaveButtonTecnico!!.setOnClickListener {
            Toast.makeText(this, "FIRMA DIGITAL GUARDADA", Toast.LENGTH_SHORT).show()
            val signatureBitmapTecnico = mSignaturePadTecnico!!.signatureBitmap
            guardarData(signatureBitmapTecnico, almacenamiento)
        }

        binding.buttonEnviar3.setOnClickListener {
            Toast.makeText(this, "INFORME FINAL ALMACENADO", Toast.LENGTH_SHORT).show()
            guardarAlmacenamiento(almacenamiento)
        }


    }


    private fun insertar(almacenamiento: Almacenamiento){
        val a1H3 = almacenamiento.caidaVOltaje.toString()
        val caidaVoltaje = condicion(a1H3)
        binding.spinnnerAHoja3.setSelection(caidaVoltaje)

        binding.EditCaidaVoltajeHoja3.setText(almacenamiento.caidaVoltajeMedida.toString())

        val b1H3 = almacenamiento.presionAceite.toString()
        val presionAceite = condicion(b1H3)
        binding.spinnnerBHoja3.setSelection(presionAceite)

        binding.EditPresionAceiteHoja3.setText(almacenamiento.presionAceiteMedida.toString())

        val c1H3 = almacenamiento.temperaturaAgua.toString()
        val temperaturaAgua = condicion(c1H3)
        binding.spinnnerCHoja3.setSelection(temperaturaAgua)

        binding.EditTemperaturaAguaHoja3.setText(almacenamiento.presionAceiteMedida.toString())

        val d1H3 = almacenamiento.voltajeAlternador.toString()
        val voltajeAlternador = condicion(d1H3)
        binding.spinnnerDHoja3.setSelection(voltajeAlternador)

        binding.EditVoltajeAlternadorHoja3.setText(almacenamiento.voltajeAlternadorMedida.toString())

        val f1H3 = almacenamiento.temperaturaAceite.toString()
        val temperaturaAceite = condicion(f1H3)
        binding.spinnnerFHoja3.setSelection(temperaturaAceite)

        binding.EditTemperaturaAceiteHoja3.setText(almacenamiento.temperaturaAceiteMedida.toString())

        val g1H3 = almacenamiento.temperaturaGases.toString()
        val temperaturaGases = condicion(g1H3)
        binding.spinnnerGHoja3.setSelection(temperaturaGases)

        binding.EditTemperaGasesEscapeHoja3.setText(almacenamiento.temperaturaGasesMedida.toString())

        val h1H3 = almacenamiento.indicadorRestriccion.toString()
        val indicadorRestriccion = condicion(h1H3)
        binding.spinnnerHHoja3.setSelection(indicadorRestriccion)

        binding.EditIndicadoRestrccionAireHoja3.setText(almacenamiento.indicadoresRestriccionMedida.toString())

        val i1H3 = almacenamiento.oscilacionVelocidad.toString()
        val oscilacionVelocidad = condicionSiNo(i1H3)
        binding.spinnnerIHoja3.setSelection(oscilacionVelocidad)

        //funciona hasta este punto

        val a2H3 = almacenamiento.altaTemperaturaMotor.toString()
        val altaTemperaturaMotor = condicionBM(a2H3)
        binding.spinnner2AHoja3.setSelection(altaTemperaturaMotor)

        val b2H3 = almacenamiento.sobreRevoluciones.toString()
        val sobreRevoluciones = condicionBM(b2H3)
        binding.spinnner2BHoja3.setSelection(sobreRevoluciones)

        val c2H3 = almacenamiento.bajaPresionAceite.toString()
        val bajaPresionAceite = condicionBM(c2H3)
        binding.spinnner2CHoja3.setSelection(bajaPresionAceite)

        val d2H3 = almacenamiento.bajoNivelRefrigerante.toString()
        val bajoNivelRefrigerante = condicionBM(d2H3)
        binding.spinnner2DHoja3.setSelection(bajoNivelRefrigerante)

        //prueba1

        binding.EditfasesA1Hoja3.setText(almacenamiento.voltaje1.toString())

        binding.EditfasesA1MedidaHoja3.setText(almacenamiento.voltaje1Medida.toString())

        binding.EditfasesA2Hoja3.setText(almacenamiento.voltaje2.toString())

        binding.EditfasesA2MedidaHoja3.setText(almacenamiento.voltaje2Medida.toString())

        binding.EditfasesA3Hoja3.setText(almacenamiento.voltaje3.toString())

        binding.EditfasesA3MedidaHoja3.setText(almacenamiento.voltaje3Medida.toString())

        binding.EditfasesB1Hoja3.setText(almacenamiento.corrienteAmperios1.toString())

        binding.EditfasesB1MedidaHoja3.setText(almacenamiento.corrienteAmperios1Medida.toString())

        binding.EditfasesB2Hoja3.setText(almacenamiento.corrienteAmperios2.toString())

        binding.EditfasesB2MedidaHoja3.setText(almacenamiento.corrienteAmperios2Medida.toString())

        binding.EditfasesB3Hoja3.setText(almacenamiento.corrienteAmperios3.toString())

        binding.EditfasesB3MedidaHoja3.setText(almacenamiento.corrienteAmperios3Medida.toString())

        binding.EditfasesB4Hoja3.setText(almacenamiento.corrienteAmperios4.toString())

        binding.EditfasesB4MedidaHoja3.setText(almacenamiento.corrienteAmperios4Medida.toString())

        val d3H3 = almacenamiento.posicionInterruptor.toString()
        val posicionInterruptor = condicionOnOf(d3H3)
        binding.spinnner3DHoja3.setSelection(posicionInterruptor)

        //prueba2

        val e3H3 = almacenamiento.switchCargador.toString()
        val switchCargador = condicionOnOf(e3H3)
        binding.spinnner3EHoja3.setSelection(switchCargador)

        val f3H3 = almacenamiento.switchControl.toString()
        val switchControl = condicionManOfOut(f3H3)
        binding.spinnner3FHoja3.setSelection(switchControl)

        val g3H3 = almacenamiento.frecuencia.toString()
        val frecuencia = condicion(g3H3)
        binding.spinnner3GHoja3.setSelection(frecuencia)

        //prueba3
        binding.EditFrecuenicaMedidaHoja3.setText(almacenamiento.frecuenciaMedida.toString())



        binding.EditFactorPoteciaMedidaHoja3.setText(almacenamiento.factorPotenciaMedida.toString())



        //prueba3.1


        //prueba4

        binding.EditKilovatiosMedidaHoja3.setText(almacenamiento.kilovatios.toString())

        val a4H3 = almacenamiento.enVacio.toString()
        val enVacio = condicionBM(a4H3)
        binding.spinnner4AHoja3.setSelection(enVacio)

        val b4H3 = almacenamiento.conCargas.toString()
        val conCargas = condicionBM(b4H3)
        binding.spinnner4BHoja3.setSelection(conCargas)

        binding.EditConCargasMedidaHoja3.setText(almacenamiento.conCargasMedida.toString())

        binding.EditRecomendacionesHoja3.setText(almacenamiento.recomendaciones.toString())

        binding.EditsalidaTA1.setText(almacenamiento.horaSalidaAM)
        binding.EditsalidaTA2.setText(almacenamiento.horaSalidaPM)
        binding.EditsalidaTA3.setText(almacenamiento.horaSalidaDD)
        binding.EditsalidaTA4.setText(almacenamiento.horaSalidaMM)
        binding.EditsalidaTA5.setText(almacenamiento.horaSalidaAA)

        binding.EditsalidaCA1.setText(almacenamiento.horaSalidaClienteAM)
        binding.EditsalidaCA2.setText(almacenamiento.horaSalidaClientePM)
        binding.EditsalidaCA3.setText(almacenamiento.horaSalidaClienteDD)
        binding.EditsalidaCA4.setText(almacenamiento.horaSalidaClienteMM)
        binding.EditsalidaCA5.setText(almacenamiento.horaSalidaClienteAA)

        binding.EditllegadaCA1.setText(almacenamiento.horaLlegadaAM)
        binding.EditllegadaCA2.setText(almacenamiento.horaLlegadaPM)
        binding.EditllegadaCA3.setText(almacenamiento.horaLlegadaDD)
        binding.EditllegadaCA4.setText(almacenamiento.horaLlegadaMM)
        binding.EditllegadaCA5.setText(almacenamiento.horaLlegadaAA)

        binding.EditatencionCA1.setText(almacenamiento.horaAtencionAm)
        binding.EditatencionCA2.setText(almacenamiento.horaAtencionPM)
        binding.EditatencionCA3.setText(almacenamiento.horaAtencionDD)
        binding.EditatencionCA4.setText(almacenamiento.horaAtencionMM)
        binding.EditatencionCA5.setText(almacenamiento.horaAtencionAA)

        binding.EditServCumplimiento1.setText(almacenamiento.calificacionClienteServicio)
        binding.EditOrdenAseo1.setText(almacenamiento.calificacionClienteOrden)
        binding.EditUsoElementosProteccion.setText(almacenamiento.calificacionClienteElementos)

        //hoja 7

        val a1 = almacenamiento.estadoConexion.toString()
        val estadoConexion = condicion(a1)
        binding.spinnnerA.setSelection(estadoConexion)

        val b1 = almacenamiento.sensores.toString()
        val sensores = condicion(b1)
        binding.spinnnerB.setSelection(sensores)

        binding.EditAlarma.setText(almacenamiento.alarmas.toString())

        binding.EditObservaciones.setText(almacenamiento.observaciones.toString())

        val e1 = almacenamiento.muestraAceite.toString()
        val muestraAceite = condicionSiNo(e1)
        binding.spinnnerE.setSelection(muestraAceite)

        val f1 = almacenamiento.muestraCombustible.toString()
        val muestraCombustible = condicionSiNo(f1)
        binding.spinnnerF.setSelection(muestraCombustible)

        val g1 = almacenamiento.muestraRefrigerante.toString()
        val muestraRefrigerante = condicionSiNo(g1)
        binding.spinnnerG.setSelection(muestraRefrigerante)

        binding.EditUltimoLavado.setText(almacenamiento.ultimoLavadoTanque.toString())

        binding.EditUltimoTanqueo.setText(almacenamiento.ultimoTanqueo.toString())

        binding.EditCapacidadTanque.setText(almacenamiento.capacidadTanqueo.toString())

        val k1 = almacenamiento.casco.toString()
        val casco = condicionSiNo(k1)
        binding.spinnnerK.setSelection(casco)

        val l1 = almacenamiento.guantes.toString()
        val guantes = condicionSiNo(l1)
        binding.spinnnerL.setSelection(guantes)

        val m1 = almacenamiento.overol.toString()
        val overol = condicionSiNo(m1)
        binding.spinnnerM.setSelection(overol)

        val nD1 = almacenamiento.gafas.toString()
        val gafas = condicionSiNo(nD1)
        binding.spinnnerNDuplicado.setSelection(gafas)

        binding.EditOtros.setText(almacenamiento.otros)

        val pD1 = almacenamiento.transporteATimepo.toString()
        val transporteATimepo = condicionSiNo(pD1)
        binding.spinnnerODuplicado.setSelection(transporteATimepo)

        val n1 = almacenamiento.insumosCompletos.toString()
        val insumosCompletos = condicionSiNo(n1)
        binding.spinnnerN.setSelection(insumosCompletos)

        val o1 = almacenamiento.pendienteRecogerInsumos.toString()
        val pendienteRecogerInsumos = condicionSiNo(o1)
        binding.spinnnerO.setSelection(pendienteRecogerInsumos)

        val p1 = almacenamiento.tiempoEsperaIngreso.toString()
        val tiempoEsperaIngreso = condicionTiempo(p1)
        binding.spinnnerP.setSelection(tiempoEsperaIngreso)

        val q1 = almacenamiento.tiempoEsperaSalida.toString()
        val tiempoEsperaSalida = condicionTiempo(q1)
        binding.spinnnerQ.setSelection(tiempoEsperaSalida)

        val r1 = almacenamiento.serviciosCotizar.toString()
        val serviciosCotizar = condicion(r1)
        binding.spinnnerR.setSelection(serviciosCotizar)

        binding.EditTipoServicio.setText(almacenamiento.tipoServicioRealizado.toString())

        val b2 = almacenamiento.otrosServicios.toString()
        val otrosServicios = condicion(b2)
        binding.spinnner2B.setSelection(otrosServicios)


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

    private fun condicionTiempo(valor : String): Int{
        var posicion : Int = 0
        when(valor){
            "16–30MIN"-> posicion = 1
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

    private fun spinerHoja3(adapter : ArrayAdapter<String>, buenoMaloadapter: ArrayAdapter<String>, onOfMaloadapter: ArrayAdapter<String>, manOfOutadapter: ArrayAdapter<String>, adapter1: ArrayAdapter<String>, timepoAdapter: ArrayAdapter<String> ){
        binding.spinnnerAHoja3.setAdapter(adapter)
        binding.spinnnerBHoja3.setAdapter(adapter)
        binding.spinnnerCHoja3.setAdapter(adapter)
        binding.spinnnerDHoja3.setAdapter(adapter)
        binding.spinnnerFHoja3.setAdapter(adapter)
        binding.spinnnerGHoja3.setAdapter(adapter)
        binding.spinnnerHHoja3.setAdapter(adapter)
        binding.spinnnerIHoja3.setAdapter(adapter1)
        binding.spinnner2AHoja3.setAdapter(buenoMaloadapter)
        binding.spinnner2BHoja3.setAdapter(buenoMaloadapter)
        binding.spinnner2CHoja3.setAdapter(buenoMaloadapter)
        binding.spinnner2DHoja3.setAdapter(buenoMaloadapter)
        binding.spinnner3DHoja3.setAdapter(onOfMaloadapter)
        binding.spinnner3EHoja3.setAdapter(onOfMaloadapter)
        binding.spinnner3FHoja3.setAdapter(manOfOutadapter)
        binding.spinnner3GHoja3.setAdapter(adapter)
        binding.spinnner3HHoja3.setAdapter(adapter)
        binding.spinnner3IHoja3.setAdapter(adapter)
        binding.spinnner4AHoja3.setAdapter(buenoMaloadapter)
        binding.spinnner4BHoja3.setAdapter(buenoMaloadapter)

        //hoja7
        binding.spinnnerA.setAdapter(adapter)
        binding.spinnnerB.setAdapter(adapter)
        binding.spinnnerE.setAdapter(adapter1)
        binding.spinnnerF.setAdapter(adapter1)
        binding.spinnnerG.setAdapter(adapter1)
        binding.spinnnerK.setAdapter(adapter1)
        binding.spinnnerL.setAdapter(adapter1)
        binding.spinnnerM.setAdapter(adapter1)
        binding.spinnnerODuplicado.setAdapter(adapter1)
        binding.spinnnerNDuplicado.setAdapter(adapter1)
        binding.spinnnerN.setAdapter(adapter)
        binding.spinnnerO.setAdapter(adapter1)
        binding.spinnnerP.setAdapter(timepoAdapter)
        binding.spinnnerQ.setAdapter(timepoAdapter)
        binding.spinnnerR.setAdapter(adapter)
        binding.spinnner2B.setAdapter(adapter)
    }


    private fun guardarAlmacenamiento(almacenamiento: Almacenamiento){
        almacenamiento.caidaVOltaje = binding.spinnnerAHoja3.selectedItem.toString()
        almacenamiento.caidaVoltajeMedida = binding.EditCaidaVoltajeHoja3.text.toString()
        almacenamiento.presionAceite = binding.spinnnerBHoja3.selectedItem.toString()
        almacenamiento.presionAceiteMedida = binding.EditPresionAceiteHoja3.text.toString()
        almacenamiento.temperaturaAgua = binding.spinnnerCHoja3.selectedItem.toString()
        almacenamiento.temperaturaAguaMedida = binding.EditTemperaturaAguaHoja3.text.toString()
        almacenamiento.voltajeAlternador = binding.spinnnerDHoja3.selectedItem.toString()
        almacenamiento.voltajeAlternadorMedida = binding.EditVoltajeAlternadorHoja3.text.toString()
        almacenamiento.temperaturaAceite = binding.spinnnerFHoja3.selectedItem.toString()
        almacenamiento.temperaturaAceiteMedida = binding.EditTemperaturaAceiteHoja3.text.toString()
        almacenamiento.temperaturaGases = binding.spinnnerGHoja3.selectedItem.toString()
        almacenamiento.temperaturaGasesMedida = binding.EditTemperaGasesEscapeHoja3.text.toString()
        almacenamiento.indicadorRestriccion = binding.spinnnerHHoja3.selectedItem.toString()
        almacenamiento.indicadoresRestriccionMedida = binding.EditIndicadoRestrccionAireHoja3.text.toString()
        almacenamiento.oscilacionVelocidad = binding.spinnnerIHoja3.selectedItem.toString()
        almacenamiento.altaTemperaturaMotor = binding.spinnner2AHoja3.selectedItem.toString()
        almacenamiento.sobreRevoluciones = binding.spinnner2BHoja3.selectedItem.toString()
        almacenamiento.bajaPresionAceite = binding.spinnner2CHoja3.selectedItem.toString()
        almacenamiento.bajoNivelRefrigerante = binding.spinnner2DHoja3.selectedItem.toString()
        almacenamiento.voltaje1 = binding.EditfasesA1Hoja3.text.toString()
        almacenamiento.voltaje1Medida = binding.EditfasesA1MedidaHoja3.text.toString()
        almacenamiento.voltaje2 = binding.EditfasesA2Hoja3.text.toString()
        almacenamiento.voltaje2Medida = binding.EditfasesA2MedidaHoja3.text.toString()
        almacenamiento.voltaje3 = binding.EditfasesA3Hoja3.text.toString()
        almacenamiento.voltaje3Medida =  binding.EditfasesA3MedidaHoja3.text.toString()
        almacenamiento.corrienteAmperios1 = binding.EditfasesB1Hoja3.text.toString()
        almacenamiento.corrienteAmperios1Medida = binding.EditfasesB1MedidaHoja3.text.toString()
        almacenamiento.corrienteAmperios2 = binding.EditfasesB2Hoja3.text.toString()
        almacenamiento.corrienteAmperios2Medida = binding.EditfasesB2MedidaHoja3.text.toString()
        almacenamiento.corrienteAmperios3 = binding.EditfasesB3Hoja3.text.toString()
        almacenamiento.corrienteAmperios3Medida = binding.EditfasesB3MedidaHoja3.text.toString()
        almacenamiento.corrienteAmperios4 = binding.EditfasesB4Hoja3.text.toString()
        almacenamiento.corrienteAmperios4Medida = binding.EditfasesB4MedidaHoja3.text.toString()
        almacenamiento.posicionInterruptor = binding.spinnner3DHoja3.selectedItem.toString()
        almacenamiento.switchCargador = binding.spinnner3EHoja3.selectedItem.toString()
        almacenamiento.switchControl = binding.spinnner3FHoja3.selectedItem.toString()
        almacenamiento.frecuencia = binding.spinnner3GHoja3.selectedItem.toString()
        almacenamiento.frecuenciaMedida = binding.EditFrecuenicaMedidaHoja3.text.toString()
        almacenamiento.factorPotencia = binding.spinnner3HHoja3.selectedItem.toString()
        almacenamiento.factorPotenciaMedida = binding.EditFactorPoteciaMedidaHoja3.text.toString()
        almacenamiento.kilovatios = binding.spinnner3IHoja3.selectedItem.toString()
        almacenamiento.kilovatiosMedida = binding.EditKilovatiosMedidaHoja3.text.toString()
        almacenamiento.enVacio = binding.spinnner4AHoja3.selectedItem.toString()
        almacenamiento.conCargas = binding.spinnner4BHoja3.selectedItem.toString()
        almacenamiento.conCargasMedida = binding.EditConCargasMedidaHoja3.text.toString()
        almacenamiento.recomendaciones = binding.EditRecomendacionesHoja3.text.toString()

        //hoja 4

        almacenamiento.horaSalidaAM = binding.EditsalidaTA1.text.toString()
        almacenamiento.horaSalidaPM = binding.EditsalidaTA2.text.toString()
        almacenamiento.horaSalidaDD = binding.EditsalidaTA3.text.toString()
        almacenamiento.horaSalidaMM = binding.EditsalidaTA4.text.toString()
        almacenamiento.horaSalidaAA = binding.EditsalidaTA5.text.toString()
        almacenamiento.horaLlegadaAM = binding.EditllegadaCA1.text.toString()
        almacenamiento.horaLlegadaPM = binding.EditllegadaCA2.text.toString()
        almacenamiento.horaLlegadaDD = binding.EditllegadaCA3.text.toString()
        almacenamiento.horaLlegadaMM = binding.EditllegadaCA4.text.toString()
        almacenamiento.horaLlegadaAA = binding.EditllegadaCA5.text.toString()
        almacenamiento.horaAtencionAm = binding.EditatencionCA1.text.toString()
        almacenamiento.horaAtencionPM = binding.EditatencionCA2.text.toString()
        almacenamiento.horaAtencionDD = binding.EditatencionCA3.text.toString()
        almacenamiento.horaAtencionMM = binding.EditatencionCA4.text.toString()
        almacenamiento.horaAtencionAA = binding.EditatencionCA5.text.toString()
        almacenamiento.horaSalidaClienteAM = binding.EditsalidaCA1.text.toString()
        almacenamiento.horaSalidaClientePM = binding.EditsalidaCA2.text.toString()
        almacenamiento.horaSalidaClienteDD = binding.EditsalidaCA3.text.toString()
        almacenamiento.horaSalidaClienteMM = binding.EditsalidaCA4.text.toString()
        almacenamiento.horaSalidaClienteAA = binding.EditsalidaCA5.text.toString()
        almacenamiento.calificacionClienteServicio = binding.EditServCumplimiento1.text.toString()
        almacenamiento.calificacionClienteOrden = binding.EditOrdenAseo1.text.toString()
        almacenamiento.calificacionClienteElementos = binding.EditUsoElementosProteccion.text.toString()

        //hoja 7

        almacenamiento.estadoConexion =  binding.spinnnerA.selectedItem.toString()
        almacenamiento.sensores =  binding.spinnnerB.selectedItem.toString()
        almacenamiento.alarmas =  binding.EditAlarma.text.toString()
        almacenamiento.observaciones =  binding.EditObservaciones.text.toString()
        almacenamiento.muestraAceite =  binding.spinnnerE.selectedItem.toString()
        almacenamiento.muestraCombustible =  binding.spinnnerF.selectedItem.toString()
        almacenamiento.muestraRefrigerante =  binding.spinnnerG.selectedItem.toString()
        almacenamiento.ultimoLavadoTanque =  binding.EditUltimoLavado.text.toString()
        almacenamiento.ultimoTanqueo =  binding.EditUltimoTanqueo.text.toString()
        almacenamiento.capacidadTanqueo =  binding.EditCapacidadTanque.text.toString()
        almacenamiento.casco =  binding.spinnnerK.selectedItem.toString()
        almacenamiento.guantes =  binding.spinnnerL.selectedItem.toString()
        almacenamiento.overol =  binding.spinnnerM.selectedItem.toString()
        almacenamiento.gafas =  binding.spinnnerNDuplicado.selectedItem.toString()
        almacenamiento.otros =  binding.EditOtros.text.toString()
        almacenamiento.transporteATimepo =  binding.spinnnerODuplicado.selectedItem.toString()
        almacenamiento.insumosCompletos =  binding.spinnnerN.selectedItem.toString()
        almacenamiento.pendienteRecogerInsumos =  binding.spinnnerO.selectedItem.toString()
        almacenamiento.tiempoEsperaIngreso =  binding.spinnnerP.selectedItem.toString()
        almacenamiento.tiempoEsperaSalida =  binding.spinnnerQ.selectedItem.toString()
        almacenamiento.serviciosCotizar =  binding.spinnnerR.selectedItem.toString()
        almacenamiento.tipoServicioRealizado =  binding.EditTipoServicio.text.toString()
        almacenamiento.otrosServicios =  binding.spinnner2B.selectedItem.toString()
    }

    private fun guardarData(signatureBitmap: Bitmap, almacenamiento: Almacenamiento){
            val sigatu = signatureBitmap.toByteArray()
            almacenamiento.data = sigatu
            viewModel.guardaralmacenamiento(almacenamiento)
    }




    fun Bitmap.toByteArray():ByteArray{
        ByteArrayOutputStream().apply {
            compress(Bitmap.CompressFormat.JPEG,10,this)
            return toByteArray()
        }
    }


}