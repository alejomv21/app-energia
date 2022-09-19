package com.equitel.pruebaequitel.Sheet8

import android.Manifest
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.equitel.pruebaequitel.Almacenamiento
import com.equitel.pruebaequitel.R
import com.equitel.pruebaequitel.Sheet7.Sheet7Activity
import com.equitel.pruebaequitel.TimePicket
import com.equitel.pruebaequitel.databinding.ActivitySheet8Binding
import com.github.gcacace.signaturepad.views.SignaturePad
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.gu.toolargetool.TooLargeTool
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*

class Sheet8Activity : AppCompatActivity() {

    companion object {
        const val EQ_KEYS = "alejo"
    }
    lateinit var generatePDFBtn: Button

    //firebase
    private val PICK_IMAGE_REQUEST = 71
    private var downloadUri : Uri? = null
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    // declaring width and height
    // for our PDF file.
    var pageHeight = 2000
    //var pageWidth = 792
    var pageWidth = 1000
    lateinit var almacenamiento : Almacenamiento
    // creating a bitmap variable
    // for storing our images
    lateinit var bmp: Bitmap
    lateinit var scaledbmp: Bitmap

    // on below line we are creating a
    // constant code for runtime permissions.
    var PERMISSION_CODE = 101
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
        //TooLargeTool.startLogging(this);

        viewModel = ViewModelProvider(this, Sheet8ViewModelFactory(application)).get(Sheet8ViewModel::class.java)

        almacenamiento = intent?.extras?.getParcelable<Almacenamiento>(EQ_KEYS)!!

        val buenoMalo : Array<String> = resources.getStringArray(R.array.buenoMaloNA)
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

        val siNoNa : Array<String> = resources.getStringArray(R.array.siNoNa)
        val siNoNaAadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, siNoNa)

        val servicio_a_cotizar : Array<String> = resources.getStringArray(R.array.servicio_a_cotizar)
        val servicio_a_cotizarAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, servicio_a_cotizar)

        val tipo_servicio_realizado : Array<String> = resources.getStringArray(R.array.tipo_servicio_realizado)
        val tipo_servicio_realizadoAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipo_servicio_realizado)

        spinerHoja3(adapter, buenoMaloadapter, onOfMaloadapter, manOfOutadapter, adapter1, timepoadapter, siNoNaAadapter, servicio_a_cotizarAdapter, tipo_servicio_realizadoAdapter)


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
            guardarDataTecnico(signatureBitmapTecnico, almacenamiento)
        }

        binding.buttonEnviar3.setOnClickListener {
            Toast.makeText(this, "INFORME FINAL ALMACENADO", Toast.LENGTH_SHORT).show()
            guardarAlmacenamiento(almacenamiento)
        }

        Calendario()
        Reloj()

        generatePDFBtn = binding.buttonPDF

        // on below line we are initializing our bitmap and scaled bitmap.
        bmp = BitmapFactory.decodeResource(resources, R.drawable.image_visita)
        //scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false)
        scaledbmp = Bitmap.createScaledBitmap(bmp, 1000, 2000, false)

        // on below line we are checking permission
        if (checkPermissions()) {
            // if permission is granted we are displaying a toast message.
            Toast.makeText(this, "Permissions Granted..", Toast.LENGTH_SHORT).show()
        } else {
            // if the permission is not granted
            // we are calling request permission method.
            requestPermission()
        }

        // on below line we are adding on click listener for our generate button.
        generatePDFBtn.setOnClickListener {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                // on below line we are calling generate
                // PDF method to generate our PDF file
                extraerAlmacenamientoPDF()
            }
        }


    }

    private fun extraerAlmacenamientoPDF(){
        viewModel.consultarID(almacenamiento.id)
        viewModel.almacenamiento.observe(this, androidx.lifecycle.Observer {
                almamacenamientoPDF->

                generatePDF(almamacenamientoPDF)
        })
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
        val altaTemperaturaMotor = condicionSiNo(a2H3)
        binding.spinnner2AHoja3.setSelection(altaTemperaturaMotor)

        val b2H3 = almacenamiento.sobreRevoluciones.toString()
        val sobreRevoluciones = condicionSiNo(b2H3)
        binding.spinnner2BHoja3.setSelection(sobreRevoluciones)

        val c2H3 = almacenamiento.bajaPresionAceite.toString()
        val bajaPresionAceite = condicionSiNo(c2H3)
        binding.spinnner2CHoja3.setSelection(bajaPresionAceite)

        val d2H3 = almacenamiento.bajoNivelRefrigerante.toString()
        val bajoNivelRefrigerante = condicionSiNo(d2H3)
        binding.spinnner2DHoja3.setSelection(bajoNivelRefrigerante)

        //prueba1

        binding.EditfasesA1Hoja3.setChecked(almacenamiento.voltaje1)

        binding.EditfasesA1MedidaHoja3.setText(almacenamiento.voltaje1Medida.toString())

        binding.EditfasesA2Hoja3.setChecked(almacenamiento.voltaje2)

        binding.EditfasesA2MedidaHoja3.setText(almacenamiento.voltaje2Medida.toString())

        binding.EditfasesA3Hoja3.setChecked(almacenamiento.voltaje3)

        binding.EditfasesA3MedidaHoja3.setText(almacenamiento.voltaje3Medida.toString())

        binding.EditfasesB1Hoja3.setChecked(almacenamiento.corrienteAmperios1)

        binding.EditfasesB1MedidaHoja3.setText(almacenamiento.corrienteAmperios1Medida.toString())

        binding.EditfasesB2Hoja3.setChecked(almacenamiento.corrienteAmperios2)

        binding.EditfasesB2MedidaHoja3.setText(almacenamiento.corrienteAmperios2Medida.toString())

        binding.EditfasesB3Hoja3.setChecked(almacenamiento.corrienteAmperios3)

        binding.EditfasesB3MedidaHoja3.setText(almacenamiento.corrienteAmperios3Medida.toString())

        binding.EditfasesB4Hoja3.setChecked(almacenamiento.corrienteAmperios4)

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

        val h3 = almacenamiento.factorPotencia.toString()
        val factorPotencia = condicion(h3)
        binding.spinnner3HHoja3.setSelection(factorPotencia)

        binding.EditFactorPoteciaMedidaHoja3.setText(almacenamiento.factorPotenciaMedida.toString())



        //prueba3.1


        //prueba4

        binding.EditKilovatiosMedidaHoja3.setText(almacenamiento.kilovatios.toString())

        val a4H3 = almacenamiento.enVacio.toString()
        val enVacio = condicionSiNo(a4H3)
        binding.spinnner4AHoja3.setSelection(enVacio)

        val b4H3 = almacenamiento.conCargas.toString()
        val conCargas = condicionSiNo(b4H3)
        binding.spinnner4BHoja3.setSelection(conCargas)

        binding.EditConCargasMedidaHoja3.setText(almacenamiento.conCargasMedida.toString())

        binding.EditRecomendacionesHoja3.setText(almacenamiento.recomendaciones.toString())


        binding.EditFecha1.setText(almacenamiento.fechaSalidaTecnico.toString())
        binding.EditReloj1.setText(almacenamiento.horaSalidaTecnico.toString())
        binding.EditFecha2.setText(almacenamiento.fechaSalidaCliente.toString())
        binding.EditReloj2.setText(almacenamiento.horaSalidaCliente.toString())
        binding.EditFecha3.setText(almacenamiento.fechaLLegadaCliente.toString())
        binding.EditReloj3.setText(almacenamiento.horaLlegadaCliente.toString())
        binding.EditFecha4.setText(almacenamiento.fechaAtencionCliente.toString())
        binding.EditReloj4.setText(almacenamiento.horaAtencionCliente.toString())


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

        binding.EditOtrosServicios.setText(almacenamiento.otrosServicios.toString())

        val ats1 = almacenamiento.atsTrabajosrealizados.toString()
        val atsTrabajosrealizados = condicionSiNo(ats1)
        binding.spinnnerAtsTrealizar.setSelection(atsTrabajosrealizados)

        val ats2 = almacenamiento.atsTrabajosAlturas.toString()
        val atsTrabajosAlturas = condicionSiNo(ats2)
        binding.spinnnerAtsTalturas.setSelection(atsTrabajosAlturas)

        val ats3 = almacenamiento.atsTrabajosConfinados.toString()
        val atsTrabajosConfinados = condicionSiNo(ats3)
        binding.spinnnerAtsTConfinados.setSelection(atsTrabajosConfinados)

        val ats4 = almacenamiento.atsTrabajosCalientes.toString()
        val atsTrabajosCalientes = condicionSiNo(ats4)
        binding.spinnnerAtscaliente.setSelection(atsTrabajosCalientes)


    }

    private fun condicion(valor : String): Int{
        var posicion : Int = 0
        when(valor){
            "B"-> posicion = 1
            "R"-> posicion = 2
            "M"-> posicion = 3
            "NA"-> posicion = 4
        }
        return posicion
    }

    private fun condicionSiNo(valor : String): Int{
        var posicion : Int = 0
        when(valor){
            "Sí"-> posicion = 1
            "No"-> posicion = 2
            "NA"-> posicion = 3
        }
        return posicion
    }

    private fun condicionTiempo(valor : String): Int{
        var posicion : Int = 0
        when(valor){
            "0-15 MIN"-> posicion = 1
            "16–30 MIN"-> posicion = 2
            "31-60 MIN<"-> posicion = 3
            "más de 1 Hora"-> posicion = 4
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
            "OFF"-> posicion = 2
            "AUT"-> posicion = 3
        }
        return posicion
    }

    private fun spinerHoja3(adapter : ArrayAdapter<String>, buenoMaloadapter: ArrayAdapter<String>, onOfMaloadapter: ArrayAdapter<String>, manOfOutadapter: ArrayAdapter<String>, adapter1: ArrayAdapter<String>, timepoAdapter: ArrayAdapter<String>, siNoNaAadapter: ArrayAdapter<String>, servicio_a_cotizarAdapter: ArrayAdapter<String>, tipo_servicio_realizadoAdapter: ArrayAdapter<String>){
        binding.spinnnerAHoja3.setAdapter(adapter)
        binding.spinnnerBHoja3.setAdapter(adapter)
        binding.spinnnerCHoja3.setAdapter(adapter)
        binding.spinnnerDHoja3.setAdapter(adapter)
        binding.spinnnerFHoja3.setAdapter(buenoMaloadapter)
        binding.spinnnerGHoja3.setAdapter(buenoMaloadapter)
        binding.spinnnerHHoja3.setAdapter(adapter)
        binding.spinnnerIHoja3.setAdapter(adapter1)
        binding.spinnner2AHoja3.setAdapter(siNoNaAadapter)
        binding.spinnner2BHoja3.setAdapter(siNoNaAadapter)
        binding.spinnner2CHoja3.setAdapter(siNoNaAadapter)
        binding.spinnner2DHoja3.setAdapter(siNoNaAadapter)
        binding.spinnner3DHoja3.setAdapter(onOfMaloadapter)
        binding.spinnner3EHoja3.setAdapter(onOfMaloadapter)
        binding.spinnner3FHoja3.setAdapter(manOfOutadapter)
        binding.spinnner3GHoja3.setAdapter(adapter)
        binding.spinnner3HHoja3.setAdapter(buenoMaloadapter)
        binding.spinnner4AHoja3.setAdapter(adapter1)
        binding.spinnner4BHoja3.setAdapter(adapter1)

        //hoja7
        binding.spinnnerA.setAdapter(adapter)
        binding.spinnnerB.setAdapter(adapter)
        binding.spinnnerE.setAdapter(adapter1)
        binding.spinnnerF.setAdapter(adapter1)
        binding.spinnnerG.setAdapter(adapter1)
        binding.spinnnerK.setAdapter(adapter1)
        binding.spinnnerL.setAdapter(adapter1)
        binding.spinnnerM.setAdapter(adapter1)
        binding.spinnnerODuplicado.setAdapter(siNoNaAadapter)
        binding.spinnnerNDuplicado.setAdapter(adapter1)
        binding.spinnnerN.setAdapter(siNoNaAadapter)
        binding.spinnnerO.setAdapter(adapter1)
        binding.spinnnerP.setAdapter(timepoAdapter)
        binding.spinnnerQ.setAdapter(timepoAdapter)
        binding.spinnnerR.setAdapter(servicio_a_cotizarAdapter)
        binding.spinnnerTipoServicioRealizado.setAdapter(tipo_servicio_realizadoAdapter)
        binding.spinnnerAtsTrealizar.setAdapter(adapter1)
        binding.spinnnerAtsTalturas.setAdapter(adapter1)
        binding.spinnnerAtsTConfinados.setAdapter(adapter1)
        binding.spinnnerAtscaliente.setAdapter(adapter1)
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
        almacenamiento.voltaje1 = binding.EditfasesA1Hoja3.isChecked()
        almacenamiento.voltaje1Medida = binding.EditfasesA1MedidaHoja3.text.toString()
        almacenamiento.voltaje2 = binding.EditfasesA2Hoja3.isChecked()
        almacenamiento.voltaje2Medida = binding.EditfasesA2MedidaHoja3.text.toString()
        almacenamiento.voltaje3 = binding.EditfasesA3Hoja3.isChecked()
        almacenamiento.voltaje3Medida =  binding.EditfasesA3MedidaHoja3.text.toString()
        almacenamiento.corrienteAmperios1 = binding.EditfasesB1Hoja3.isChecked()
        almacenamiento.corrienteAmperios1Medida = binding.EditfasesB1MedidaHoja3.text.toString()
        almacenamiento.corrienteAmperios2 = binding.EditfasesB2Hoja3.isChecked()
        almacenamiento.corrienteAmperios2Medida = binding.EditfasesB2MedidaHoja3.text.toString()
        almacenamiento.corrienteAmperios3 = binding.EditfasesB3Hoja3.isChecked()
        almacenamiento.corrienteAmperios3Medida = binding.EditfasesB3MedidaHoja3.text.toString()
        almacenamiento.corrienteAmperios4 = binding.EditfasesB4Hoja3.isChecked()
        almacenamiento.corrienteAmperios4Medida = binding.EditfasesB4MedidaHoja3.text.toString()
        almacenamiento.posicionInterruptor = binding.spinnner3DHoja3.selectedItem.toString()
        almacenamiento.switchCargador = binding.spinnner3EHoja3.selectedItem.toString()
        almacenamiento.switchControl = binding.spinnner3FHoja3.selectedItem.toString()
        almacenamiento.frecuencia = binding.spinnner3GHoja3.selectedItem.toString()
        almacenamiento.frecuenciaMedida = binding.EditFrecuenicaMedidaHoja3.text.toString()
        almacenamiento.factorPotencia = binding.spinnner3HHoja3.selectedItem.toString()
        almacenamiento.factorPotenciaMedida = binding.EditFactorPoteciaMedidaHoja3.text.toString()
        almacenamiento.kilovatiosMedida = binding.EditKilovatiosMedidaHoja3.text.toString()
        almacenamiento.enVacio = binding.spinnner4AHoja3.selectedItem.toString()
        almacenamiento.conCargas = binding.spinnner4BHoja3.selectedItem.toString()
        almacenamiento.conCargasMedida = binding.EditConCargasMedidaHoja3.text.toString()
        almacenamiento.recomendaciones = binding.EditRecomendacionesHoja3.text.toString()

        //hoja 4

        almacenamiento.fechaSalidaTecnico = binding.EditFecha1.text.toString()
        almacenamiento.horaSalidaTecnico = binding.EditReloj1.text.toString()
        almacenamiento.fechaSalidaCliente = binding.EditFecha2.text.toString()
        almacenamiento.horaSalidaCliente = binding.EditReloj2.text.toString()
        almacenamiento.fechaLLegadaCliente = binding.EditFecha3.text.toString()
        almacenamiento.horaLlegadaCliente = binding.EditReloj3.text.toString()
        almacenamiento.fechaAtencionCliente = binding.EditFecha4.text.toString()
        almacenamiento.horaAtencionCliente = binding.EditReloj4.text.toString()

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
        almacenamiento.otrosServicios =  binding.EditOtrosServicios.text.toString()
        almacenamiento.atsTrabajosrealizados = binding.spinnnerAtsTrealizar.selectedItem.toString()
        almacenamiento.atsTrabajosAlturas = binding.spinnnerAtsTalturas.selectedItem.toString()
        almacenamiento.atsTrabajosConfinados = binding.spinnnerAtsTConfinados.selectedItem.toString()
        almacenamiento.atsTrabajosCalientes = binding.spinnnerAtscaliente.selectedItem.toString()
        viewModel.guardaralmacenamiento(almacenamiento)
    }

    private fun guardarData(signatureBitmap: Bitmap, almacenamiento: Almacenamiento){
            val sigatu = signatureBitmap.toByteArray()
            almacenamiento.data = sigatu
            viewModel.guardaralmacenamiento(almacenamiento)
    }

    private fun guardarDataTecnico(signatureBitmap: Bitmap, almacenamiento: Almacenamiento){
        val sigatuTecnico = signatureBitmap.toByteArray()
        almacenamiento.dataTecnico = sigatuTecnico
        viewModel.guardaralmacenamiento(almacenamiento)
    }




    fun Bitmap.toByteArray():ByteArray{
        ByteArrayOutputStream().apply {
            compress(Bitmap.CompressFormat.JPEG,10,this)
            return toByteArray()
        }
    }

    private fun Calendario(){
        binding.fecha1Button.setOnClickListener {
            val EditButon = binding.EditFecha1
            val DialogFecha = DatePickerFragment{year, month, day ->  mostrarResultado(year, month, day, EditButon)}
            DialogFecha.show(supportFragmentManager, "DataPicker")
        }
        binding.fecha2Button.setOnClickListener {
            val EditButon = binding.EditFecha2
            val DialogFecha = DatePickerFragment{year, month, day ->  mostrarResultado(year, month, day, EditButon)}
            DialogFecha.show(supportFragmentManager, "DataPicker")
        }
        binding.fecha3Button.setOnClickListener {
            val EditButon = binding.EditFecha3
            val DialogFecha = DatePickerFragment{year, month, day ->  mostrarResultado(year, month, day, EditButon)}
            DialogFecha.show(supportFragmentManager, "DataPicker")
        }
        binding.fecha4Button.setOnClickListener {
            val EditButon = binding.EditFecha4
            val DialogFecha = DatePickerFragment{year, month, day ->  mostrarResultado(year, month, day, EditButon)}
            DialogFecha.show(supportFragmentManager, "DataPicker")
        }
        binding.fechaUltimoLavado.setOnClickListener {
            val EditButon = binding.EditUltimoLavado
            val DialogFecha = Sheet7Activity.DatePickerFragment { year, month, day ->
                mostrarResultado(
                    year,
                    month,
                    day,
                    EditButon
                )
            }
            DialogFecha.show(supportFragmentManager, "DataPicker")
        }
        binding.fechaUltimoTanqueo.setOnClickListener {
            val EditButon = binding.EditUltimoTanqueo
            val DialogFecha = Sheet7Activity.DatePickerFragment { year, month, day ->
                mostrarResultado(
                    year,
                    month,
                    day,
                    EditButon
                )
            }
            DialogFecha.show(supportFragmentManager, "DataPicker")
        }
    }

    private fun mostrarResultado(year: Int, month: Int, day: Int, edit: EditText) {
        edit.setText("$year/$month/$day")
    }

    class DatePickerFragment (val listener: (year:Int, month:Int, day:Int)-> Unit): DialogFragment(), DatePickerDialog.OnDateSetListener {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = Calendar.getInstance()
            var year = c.get(Calendar.YEAR)
            var month = c.get(Calendar.MONTH)
            var day = c.get(Calendar.DAY_OF_MONTH)

            val picket = DatePickerDialog(requireActivity(), this, year, month, day)
            picket.datePicker.minDate = c.timeInMillis
            return picket

        }

        override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
            listener(year, month+1, day)
        }
    }


    private fun Reloj (){
        binding.reloj1Button.setOnClickListener {
            val editReloj = binding.EditReloj1
            val horas = TimePicket { hora, minuto -> mostrarHora(hora, minuto, editReloj) }
            horas.show(supportFragmentManager, "TimePicker")
        }
        binding.reloj2Button.setOnClickListener {
            val editReloj = binding.EditReloj2
            val horas = TimePicket { hora, minuto -> mostrarHora(hora, minuto, editReloj) }
            horas.show(supportFragmentManager, "TimePicker")
        }
        binding.reloj3Button.setOnClickListener {
            val editReloj = binding.EditReloj3
            val horas = TimePicket { hora, minuto -> mostrarHora(hora, minuto, editReloj) }
            horas.show(supportFragmentManager, "TimePicker")
        }
        binding.reloj4Button.setOnClickListener {
            val editReloj = binding.EditReloj4
            val horas = TimePicket { hora, minuto -> mostrarHora(hora, minuto, editReloj) }
            horas.show(supportFragmentManager, "TimePicker")
        }
    }

    private fun mostrarHora(hora: Int, minuto: Int, reloj : EditText) {
        reloj.setText("$hora : $minuto")
    }

    //PDF GENERATE


    // on below line we are creating a generate PDF method
    // which is use to generate our PDF file.
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun generatePDF(almacenamientoPDF: Almacenamiento) {
        // creating an object variable
        // for our PDF document.
        var pdfDocument: PdfDocument = PdfDocument()

        // two variables for paint "paint" is used
        // for drawing shapes and we will use "title"
        // for adding text in our PDF file.
        var paint: Paint = Paint()
        var title: Paint = Paint()

        // we are adding page info to our PDF file
        // in which we will be passing our pageWidth,
        // pageHeight and number of pages and after that
        // we are calling it to create our PDF.
        var myPageInfo: PdfDocument.PageInfo? =
            PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()

        // below line is used for setting
        // start page for our PDF file.
        var myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)

        // creating a variable for canvas
        // from our page of PDF.
        var canvas: Canvas = myPage.canvas

        // below line is used to draw our image on our PDF file.
        // the first parameter of our drawbitmap method is
        // our bitmap
        // second parameter is position from left
        // third parameter is position from top and last
        // one is our variable for paint.
        //canvas.drawBitmap(scaledbmp, 56F, 40F, paint)
        canvas.drawBitmap(scaledbmp, 0F, 0F, paint)

        // below line is used for adding typeface for
        // our text which we will be adding in our PDF file.
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))

        // below line is used for setting text size
        // which we will be displaying in our PDF file.
        title.textSize = 15F

        // below line is sued for setting color
        // of our text inside our PDF file.
        title.setColor(ContextCompat.getColor(this, R.color.purple_200))

        // below line is used to draw text in our PDF file.
        // the first parameter is our text, second parameter
        // is position from start, third parameter is position from top
        // and then we are passing our variable of paint which is title.
        canvas.drawText(almacenamientoPDF.cliente.toString(), 100F, 230F, title)
        canvas.drawText(almacenamientoPDF.servicioSolicitadoPor.toString(), 170F, 260F, title)
        canvas.drawText(almacenamientoPDF.direccion.toString(), 650F, 230F, title)
        canvas.drawText(almacenamientoPDF.cargo.toString(), 100F, 300F, title)
        canvas.drawText(almacenamientoPDF.email.toString(), 720F, 300F, title)
        canvas.drawText(almacenamientoPDF.marcaMotor.toString(), 130F, 335F, title)
        canvas.drawText(almacenamientoPDF.marcaGenerador.toString(), 520F, 335F, title)
        canvas.drawText(almacenamientoPDF.marcaPlanta.toString(), 720F, 335F, title)
        canvas.drawText(almacenamientoPDF.modMotor.toString(), 130F, 370F, title)
        canvas.drawText(almacenamientoPDF.modGenerador.toString(), 520F, 370F, title)
        canvas.drawText(almacenamientoPDF.modPlanta.toString(), 720F, 370F, title)
        canvas.drawText(almacenamientoPDF.serialMotor.toString(), 130F, 405F, title)
        canvas.drawText(almacenamientoPDF.serialGenerador.toString(), 520F, 405F, title)
        canvas.drawText(almacenamientoPDF.serialPlanta.toString(), 720F, 405F, title)
        canvas.drawText(almacenamientoPDF.clp.toString(), 90F, 440F, title)
        canvas.drawText(almacenamientoPDF.tipoBateria.toString(), 230F, 440F, title)
        canvas.drawText(almacenamientoPDF.kw.toString(), 520F, 440F, title)
        canvas.drawText(almacenamientoPDF.spec.toString(), 720F, 440F, title)
        canvas.drawText(almacenamientoPDF.nArranques.toString(), 120F, 475F, title)
        canvas.drawText(almacenamientoPDF.horasMotor.toString(), 320F, 475F, title)
        canvas.drawText(almacenamientoPDF.horasControl.toString(), 520F, 475F, title)
        canvas.drawText(almacenamientoPDF.tipoControl.toString(), 720F, 475F, title)
        canvas.drawText(almacenamientoPDF.tecnicos.toString(), 450F, 510F, title)
        canvas.drawText(almacenamientoPDF.promotionID.toString(), 720F, 510F, title)


        when(almacenamientoPDF.nivelAceite.toString()){
            "B"-> canvas.drawText("X", 287F, 630F, title)
            "R"-> canvas.drawText("X", 315F, 630F, title)
            "M"-> canvas.drawText("X", 343F, 630F, title)
        }

        when(almacenamientoPDF.estadoRadiador.toString()){
            "B"-> canvas.drawText("X", 287F, 645F, title)
            "R"-> canvas.drawText("X", 315F, 645F, title)
            "M"-> canvas.drawText("X", 343F, 645F, title)
        }

        when(almacenamientoPDF.nivelAguaRadiador.toString()){
            "B"-> canvas.drawText("X", 287F, 660F, title)
            "R"-> canvas.drawText("X", 315F, 660F, title)
            "M"-> canvas.drawText("X", 343F, 660F, title)
        }

        when(almacenamientoPDF.aspasVentilador.toString()){
            "B"-> canvas.drawText("X", 287F, 675F, title)
            "R"-> canvas.drawText("X", 315F, 675F, title)
            "M"-> canvas.drawText("X", 343F, 675F, title)
        }

        when(almacenamientoPDF.bornerBateria.toString()){
            "B"-> canvas.drawText("X", 287F, 690F, title)
            "R"-> canvas.drawText("X", 315F, 690F, title)
            "M"-> canvas.drawText("X", 343F, 690F, title)
        }

        when(almacenamientoPDF.nivelAguaBaterias.toString()){
            "B"-> canvas.drawText("X", 287F, 705F, title)
            "R"-> canvas.drawText("X", 315F, 705F, title)
            "M"-> canvas.drawText("X", 343F, 705F, title)
        }
        //primer salto
        when(almacenamientoPDF.voltajeBateria.toString()){
            "B"-> canvas.drawText("X", 287F, 740F, title)
            "R"-> canvas.drawText("X", 315F, 740F, title)
            "M"-> canvas.drawText("X", 343F, 740F, title)
        }
        when(almacenamientoPDF.voltajeBateria.toString()){
            "B"-> canvas.drawText("X", 287F, 755F, title)
            "R"-> canvas.drawText("X", 315F, 755F, title)
            "M"-> canvas.drawText("X", 343F, 755F, title)
        }
        when(almacenamientoPDF.cargadorFuncional.toString()){
            "B"-> canvas.drawText("X", 287F, 770F, title)
            "R"-> canvas.drawText("X", 315F, 770F, title)
            "M"-> canvas.drawText("X", 343F, 770F, title)
        }
        when(almacenamientoPDF.correasTensionadas.toString()){
            "B"-> canvas.drawText("X", 287F, 785F, title)
            "R"-> canvas.drawText("X", 315F, 785F, title)
            "M"-> canvas.drawText("X", 343F, 785F, title)
        }
        when(almacenamientoPDF.estadoFiltroAire.toString()){
            "B"-> canvas.drawText("X", 287F, 800F, title)
            "R"-> canvas.drawText("X", 315F, 800F, title)
            "M"-> canvas.drawText("X", 343F, 800F, title)
        }
        when(almacenamientoPDF.estadoMangueras.toString()){
            "B"-> canvas.drawText("X", 287F, 815F, title)
            "R"-> canvas.drawText("X", 315F, 815F, title)
            "M"-> canvas.drawText("X", 343F, 815F, title)
        }

        //segundo salto

        when(almacenamientoPDF.caidaVOltaje.toString()){
            "B"-> canvas.drawText("X", 287F, 1030F, title)
            "R"-> canvas.drawText("X", 315F, 1030F, title)
            "M"-> canvas.drawText("X", 343F, 1030F, title)
        }

        when(almacenamientoPDF.presionAceite.toString()){
            "B"-> canvas.drawText("X", 287F, 1045F, title)
            "R"-> canvas.drawText("X", 315F, 1045F, title)
            "M"-> canvas.drawText("X", 343F, 1045F, title)
        }

        when(almacenamientoPDF.temperaturaAgua.toString()){
            "B"-> canvas.drawText("X", 287F, 1060F, title)
            "R"-> canvas.drawText("X", 315F, 1060F, title)
            "M"-> canvas.drawText("X", 343F, 1060F, title)
        }
        when(almacenamientoPDF.voltajeAlternador.toString()){
            "B"-> canvas.drawText("X", 287F, 1075F, title)
            "R"-> canvas.drawText("X", 315F, 1075F, title)
            "M"-> canvas.drawText("X", 343F, 1075F, title)
        }
        when(almacenamientoPDF.temperaturaAceite.toString()){
            "B"-> canvas.drawText("X", 287F, 1107F, title)
            "R"-> canvas.drawText("X", 315F, 1107F, title)
            "M"-> canvas.drawText("X", 343F, 1107F, title)
        }

        when(almacenamientoPDF.temperaturaGases.toString()){
            "B"-> canvas.drawText("X", 287F, 1121F, title)
            "R"-> canvas.drawText("X", 315F, 1121F, title)
            "M"-> canvas.drawText("X", 343F, 1121F, title)
        }
        when(almacenamientoPDF.indicadorRestriccion.toString()){
            "B"-> canvas.drawText("X", 287F, 1135F, title)
            "R"-> canvas.drawText("X", 315F, 1135F, title)
            "M"-> canvas.drawText("X", 343F, 1135F, title)
        }
        when(almacenamientoPDF.altaTemperaturaMotor.toString()){
            "B"-> canvas.drawText("X", 287F, 1195F, title)
            "R"-> canvas.drawText("X", 315F, 1195F, title)
            "M"-> canvas.drawText("X", 343F, 1195F, title)
        }

        //cambio de lado

        when(almacenamientoPDF.estadoRacores.toString()){
            "B"-> canvas.drawText("X", 704F, 630F, title)
            "R"-> canvas.drawText("X", 732F, 630F, title)
            "M"-> canvas.drawText("X", 762F, 630F, title)
        }

        when(almacenamientoPDF.fugas.toString()){
            "B"-> canvas.drawText("X", 704F, 645F, title)
            "R"-> canvas.drawText("X", 732F, 645F, title)
            "M"-> canvas.drawText("X", 762F, 645F, title)
        }

        when(almacenamientoPDF.estadoCombustible.toString()){
            "B"-> canvas.drawText("X", 704F, 660F, title)
            "R"-> canvas.drawText("X", 732F, 660F, title)
            "M"-> canvas.drawText("X", 762F, 660F, title)
        }

        when(almacenamientoPDF.estadoTanque.toString()){
            "B"-> canvas.drawText("X", 704F, 675F, title)
            "R"-> canvas.drawText("X", 732F, 675F, title)
            "M"-> canvas.drawText("X", 762F, 675F, title)
        }

        when(almacenamientoPDF.formaTanque.toString()){
            "B"-> canvas.drawText("X", 704F, 690F, title)
            "R"-> canvas.drawText("X", 732F, 690F, title)
            "M"-> canvas.drawText("X", 762F, 690F, title)
        }

        when(almacenamientoPDF.estadoGenerador.toString()){
            "B"-> canvas.drawText("X", 704F, 720F, title)
            "R"-> canvas.drawText("X", 732F, 720F, title)
            "M"-> canvas.drawText("X", 762F, 720F, title)
        }
        //primer salto
        when(almacenamientoPDF.voltajeBateria.toString()){
            "B"-> canvas.drawText("X", 704F, 743F, title)
            "R"-> canvas.drawText("X", 732F, 743F, title)
            "M"-> canvas.drawText("X", 762F, 743F, title)
        }
        when(almacenamientoPDF.voltajeBateria.toString()){
            "B"-> canvas.drawText("X", 704F, 758F, title)
            "R"-> canvas.drawText("X", 732F, 758F, title)
            "M"-> canvas.drawText("X", 762F, 758F, title)
        }
        when(almacenamientoPDF.cargadorFuncional.toString()){
            "B"-> canvas.drawText("X", 704F, 773F, title)
            "R"-> canvas.drawText("X", 732F, 773F, title)
            "M"-> canvas.drawText("X", 762F, 773F, title)
        }
        when(almacenamientoPDF.correasTensionadas.toString()){
            "B"-> canvas.drawText("X", 704F, 788F, title)
            "R"-> canvas.drawText("X", 732F, 788F, title)
            "M"-> canvas.drawText("X", 762F, 788F, title)
        }
        when(almacenamientoPDF.estadoFiltroAire.toString()){
            "B"-> canvas.drawText("X", 704F, 803F, title)
            "R"-> canvas.drawText("X", 732F, 803F, title)
            "M"-> canvas.drawText("X", 762F, 803F, title)
        }
        when(almacenamientoPDF.estadoMangueras.toString()){
            "B"-> canvas.drawText("X", 704F, 818F, title)
            "R"-> canvas.drawText("X", 732F, 818F, title)
            "M"-> canvas.drawText("X", 762F, 818F, title)
        }

        val firmaBMP = almacenamientoPDF.data?.let { BitmapFactory.decodeByteArray(almacenamientoPDF.data, 0, it.size) }
        val firmaEscala = firmaBMP?.let { Bitmap.createScaledBitmap(it, 100, 100, false) }

        if (firmaEscala != null) {
            canvas.drawBitmap(firmaEscala, 750F, 1670F, paint)
        }

        val firmaBMPTecnico = almacenamientoPDF.dataTecnico?.let { BitmapFactory.decodeByteArray(almacenamientoPDF.dataTecnico, 0, it.size) }
        val firmaEscalaTecnico = firmaBMPTecnico?.let { Bitmap.createScaledBitmap(it, 100, 50, false) }

        if (firmaEscalaTecnico != null) {
            canvas.drawBitmap(firmaEscalaTecnico, 320F, 1670F, paint)
        }






        /*var content = arrayOf(almacenamientoPDF)

        for(i in content.indices){
            println(content.get(i))
            var nivelAceite = content.get(i).toString()
            //var pintarData = arrayOf(almacenamientoPDF)
            //println(pintarData)
        }*/



        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        title.setColor(ContextCompat.getColor(this, R.color.purple_200))
        title.textSize = 15F

        // below line is used for setting
        // our text to center of PDF.


        //title.textAlign = Paint.Align.CENTER
        //canvas.drawText("This is sample document which we have created.", 396F, 560F, title)

        // after adding all attributes to our
        // PDF file we will be finishing our page.
        pdfDocument.finishPage(myPage)

        // below line is used to set the name of
        // our PDF file and its path.


        val file: File = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "GFG.pdf")
        if(file.exists()){
            println("Existe")
        }else{
            println("No existe")
        }

        try {
            // after creating a file name we will
            // write our PDF file to that location.
            pdfDocument.writeTo(FileOutputStream(file))

            /*filePath = if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.N){
                FileProvider.getUriForFile(this,
                    "$packageName.provider",
                    file)
                //

            }else{
                Uri.fromFile(file)
            }*/

            //uploadImage(filePath)

            // on below line we are displaying a toast message as PDF file generated..
            Toast.makeText(applicationContext, "PDF file generated..", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            // below line is used
            // to handle error
            e.printStackTrace()

            // on below line we are displaying a toast message as fail to generate PDF
            Toast.makeText(applicationContext, "Fail to generate PDF file..", Toast.LENGTH_SHORT)
                .show()
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close()
    }

    fun checkPermissions(): Boolean {
        // on below line we are creating a variable for both of our permissions.

        // on below line we are creating a variable for
        // writing to external storage permission
        var writeStoragePermission = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        // on below line we are creating a variable
        // for reading external storage permission
        var readStoragePermission = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        // on below line we are returning true if both the
        // permissions are granted anf returning false
        // if permissions are not granted.
        return writeStoragePermission == PackageManager.PERMISSION_GRANTED
                && readStoragePermission == PackageManager.PERMISSION_GRANTED
    }

    // on below line we are creating a function to request permission.
    fun requestPermission() {

        // on below line we are requesting read and write to
        // storage permission for our application.
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), PERMISSION_CODE
        )
    }

    // on below line we are calling
    // on request permission result.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // on below line we are checking if the
        // request code is equal to permission code.
        if (requestCode == PERMISSION_CODE) {

            // on below line we are checking if result size is > 0
            if (grantResults.size > 0) {

                // on below line we are checking
                // if both the permissions are granted.
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]
                    == PackageManager.PERMISSION_GRANTED) {

                    // if permissions are granted we are displaying a toast message.
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show()

                } else {

                    // if permissions are not granted we are
                    // displaying a toast message as permission denied.
                    Toast.makeText(this, "Permission Denied..", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun uploadImage(filePath: Uri?){
        if(filePath != null){
            val ref = storageReference?.child("myPDF/" + UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)
            val urlTask = uploadTask?.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                ref.downloadUrl
            }?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    downloadUri = task.result
                    println(downloadUri)
                } else {
                    // Handle failures
                    // ...
                }
            }
        }else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
    }


}