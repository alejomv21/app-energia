package com.equitel.pruebaequitel.Sheet2

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.equitel.pruebaequitel.FirebaseStorageManager
import com.equitel.pruebaequitel.R
import com.equitel.pruebaequitel.Sheet3.Sheet3Activity
import com.equitel.pruebaequitel.databinding.ActivitySheet2Binding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class Sheet2Activity : AppCompatActivity() {
    //firebase
    private val PICK_IMAGE_REQUEST = 71
    private var downloadUri : Uri? = null
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    lateinit var btn_choose_image: ImageButton
    lateinit var btn_upload_image: ImageButton

    private val btnSelectImage: AppCompatButton by lazy {
        findViewById(R.id.ButtonCamera2)
    }
    //camera
    lateinit var binding : ActivitySheet2Binding
    lateinit var viewModel: Sheet2ViewModel
    private lateinit var heroImage : ImageView
    private var heroBitmap : Bitmap? = null
    private var picturePath = ""
    private val getContent = registerForActivityResult(ActivityResultContracts.TakePicture()){
            success ->
        if(success && picturePath.isNotEmpty()){
            heroBitmap = BitmapFactory.decodeFile(picturePath)
            heroImage.setImageBitmap(heroBitmap)
        }
        //heroImage.setImageBitmap(heroBitmap!!)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySheet2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            Sheet2ViewModelFactory(application)
        ).get(Sheet2ViewModel::class.java)

        /*binding.buttonEnviar.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }*/



        val siNo: Array<String> = resources.getStringArray(R.array.SiNo)
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_list_item_1, siNo)

        val vHs: Array<String> = resources.getStringArray(R.array.vHs)
        val adaptervhs = ArrayAdapter(this, android.R.layout.simple_list_item_1, vHs)

        val aDisyuntores: Array<String> = resources.getStringArray(R.array.vHs)
        val adapterDisyuntores =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, aDisyuntores)

        val aPrecalentador: Array<String> = resources.getStringArray(R.array.precalentador)
        val adapterPrecalentador =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, aPrecalentador)

        val aVoltaje: Array<String> = resources.getStringArray(R.array.voltaje)
        val adapterVoltaje = ArrayAdapter(this, android.R.layout.simple_list_item_1, aVoltaje)

        val aTanque: Array<String> = resources.getStringArray(R.array.Tanque)
        val adapterTanque = ArrayAdapter(this, android.R.layout.simple_list_item_1, aTanque)

        val aDisyuntoresP : Array<String> = resources.getStringArray(R.array.Disyuntres)
        val adapterDisyuntoresParte= ArrayAdapter(this, android.R.layout.simple_list_item_1, aDisyuntoresP)

        val correas : Array<String> = resources.getStringArray(R.array.correas)
        val adaptercorreas= ArrayAdapter(this, android.R.layout.simple_list_item_1, correas)

        val buenoMaloNA : Array<String> = resources.getStringArray(R.array.buenoMaloNA)
        val buenoMaloNAadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, buenoMaloNA)

        val SistemaRespaldo : Array<String> = resources.getStringArray(R.array.SistemaRespaldo)
        val SistemaRespaldoArray = ArrayAdapter(this, android.R.layout.simple_list_item_1, SistemaRespaldo)

        val tipoServicio : Array<String> = resources.getStringArray(R.array.OpcionesHoja2)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipoServicio)
        spinnerAdapter(adapter, adapter1,adaptervhs, adapterPrecalentador, adapterVoltaje, adapterTanque, adapterDisyuntoresParte, adaptercorreas, buenoMaloNAadapter, SistemaRespaldoArray)

        binding.spinnner3E.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position == 1){
                    binding.text3G.visibility = View.VISIBLE
                    binding.text3H.visibility = View.VISIBLE
                    binding.text3I.visibility = View.VISIBLE
                    binding.text3J.visibility = View.VISIBLE
                    binding.spinnner3G.visibility = View.VISIBLE
                    binding.spinnner3H.visibility = View.VISIBLE
                    binding.spinnner3I.visibility = View.VISIBLE
                    binding.spinnner3J.visibility = View.VISIBLE
                }
            }
        }


        heroImage = binding.Camera2
        btn_choose_image = binding.ButtonCameraSearch
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        btn_upload_image = binding.ButtonCamera2Upload

        binding.ButtonCamera2.setOnClickListener{
            openCamera()
        }
        btn_choose_image.setOnClickListener { launchGallery() }
        btn_upload_image.setOnClickListener { uploadImage() }


        //Enviar
        binding.buttonEnviar.setOnClickListener {
            println(binding.spinnnerA.selectedItem.toString())
            Log.d("limas", binding.spinnnerA.selectedItem.toString())
            val validar = validarCampos()
            //recordatorio cambiar para validar
            if (validar != true) {
                Log.d("lleno total", binding.spinnnerA.selectedItem.toString())
            }
            extraerDatos()
            val intent = Intent(this, Sheet3Activity::class.java)
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
            ultimoGuardado()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun spinnerAdapter(adapter : ArrayAdapter<String>, adapter1: ArrayAdapter<String>, adaptervhs: ArrayAdapter<String>, adapterPrecalentador: ArrayAdapter<String>,
                               adapterVoltaje: ArrayAdapter<String>, adapterTanque: ArrayAdapter<String>,  adapterDisyuntoresParte: ArrayAdapter<String>,  adaptercorreas: ArrayAdapter<String>, buenoMaloNAadapter: ArrayAdapter<String>,
                               SistemaRespaldoArray : ArrayAdapter<String>){
        binding.spinnnerA.setAdapter(adapter)
        binding.spinnnerB.setAdapter(adapter)
        binding.spinnnerC.setAdapter(adapter)
        binding.spinnnerD.setAdapter(adapter)
        binding.spinnnerE.setAdapter(adapter)
        binding.spinnnerF.setAdapter(adaptercorreas)
        binding.spinnnerG.setAdapter(buenoMaloNAadapter)
        binding.spinnnerH.setAdapter(buenoMaloNAadapter)
        binding.spinnnerI.setAdapter(adapter)
        binding.spinnnerJ.setAdapter(adapter1)
        binding.spinnnerK.setAdapter(adapter)
        binding.spinnnerL.setAdapter(adapter)
        binding.spinnnerNDuplicado.setAdapter(adapter)
        binding.spinnnerN.setAdapter(adapter)
        binding.spinnnerO.setAdapter(adapter1)
        binding.spinnnerP.setAdapter(adapter)
        binding.spinnnerQ.setAdapter(adaptervhs)
        binding.spinnnerR.setAdapter(adapterTanque)
        binding.spinnner2A.setAdapter(adapter)
        binding.spinnner2B.setAdapter(adapter)
        binding.spinnner2C.setAdapter(adapter)
        binding.spinnner2D.setAdapter(adapter)
        binding.spinnner2E.setAdapter(adapter1)
        binding.spinnner2F.setAdapter(adapter)
        binding.spinnner2G.setAdapter(adapter)
        binding.spinnner2H.setAdapter(adapter)
        binding.spinnner3C.setAdapter(adapterDisyuntoresParte)
        binding.spinnnerTipoSistemaRespaldo.setAdapter(SistemaRespaldoArray)
        binding.spinnner3D.setAdapter(adapter)
        binding.spinnner3E.setAdapter(adapter1)
        binding.spinnner3G.setAdapter(adapterPrecalentador)
        binding.spinnner3H.setAdapter(adapterVoltaje)
        binding.spinnner3I.setAdapter(adapter)
        binding.spinnner3J.setAdapter(adapter)
    }


    private fun extraerDatos(){
        viewModel.extraerAlmacenamiento()
        viewModel.almacenamiento.observe(this, Observer {
                almamacenamiento->

            almamacenamiento.nivelAceite =  binding.spinnnerA.selectedItem.toString()
            almamacenamiento.nivelAceiteMedida = binding.EditNivelAceiteMedida.text.toString()
            almamacenamiento.estadoRadiador =  binding.spinnnerB.selectedItem.toString()
            almamacenamiento.nivelAguaRadiador =  binding.spinnnerC.selectedItem.toString()
            almamacenamiento.aspasVentilador = binding.spinnnerD.selectedItem.toString()
            almamacenamiento.bornerBateria=  binding.spinnnerE.selectedItem.toString()
            almamacenamiento.nivelAguaBaterias = binding.spinnnerF.selectedItem.toString()
            almamacenamiento.densidadElectrolitos = binding.spinnnerG.selectedItem.toString()
            almamacenamiento.voltajeBateria =  binding.spinnnerH.selectedItem.toString()
            almamacenamiento.voltajeBateriaMedida =  binding.EditVoltajeBateria.text.toString()
            almamacenamiento.cargadorFuncional =  binding.spinnnerI.selectedItem.toString()
            almamacenamiento.cargadorFuncionaMedida =  binding.EditCargadorFuncional.text.toString()
            almamacenamiento.correasTensionadas =  binding.spinnnerJ.selectedItem.toString()
            almamacenamiento.estadoFiltroAire =  binding.spinnnerK.selectedItem.toString()
            almamacenamiento.estadoMangueras =  binding.spinnnerL.selectedItem.toString()


            almamacenamiento.estadoTapaRadiador = binding.spinnnerNDuplicado.selectedItem.toString()
            almamacenamiento.presionTapaRadiador = binding.EditODuplicado.text.toString()
            almamacenamiento.dimensiones = binding.EditPDuplicado.text.toString()


            almamacenamiento.estadoRacores =  binding.spinnnerN.selectedItem.toString()
            almamacenamiento.fugas =  binding.spinnnerO.selectedItem.toString()
            almamacenamiento.estadoCombustible =  binding.spinnnerP.selectedItem.toString()
            almamacenamiento.estadoCombustibleMedida =  binding.EditEstadoCombustible.text.toString()
            almamacenamiento.estadoTanque =  binding.spinnnerQ.selectedItem.toString()
            almamacenamiento.estadoTanqueMedida =  binding.EditEstadoTanque.text.toString()
            almamacenamiento.formaTanque =  binding.spinnnerR.selectedItem.toString()
            almamacenamiento.estadoGenerador =  binding.spinnner2A.selectedItem.toString()
            almamacenamiento.aspasVentiladorGenerador =  binding.spinnner2B.selectedItem.toString()
            almamacenamiento.conexionPotencia =  binding.spinnner2C.selectedItem.toString()
            almamacenamiento.conexionCableadocontrol =  binding.spinnner2D.selectedItem.toString()
            almamacenamiento.objetsExtrasInterior =  binding.spinnner2E.selectedItem.toString()
            almamacenamiento.puenteRectificadorGiratorio =  binding.spinnner2F.selectedItem.toString()
            almamacenamiento.estadoControl =  binding.spinnner2G.selectedItem.toString()
            almamacenamiento.estadoCuartoCabina =  binding.spinnner2H.selectedItem.toString()
            almamacenamiento.marcaControlTrasferencia = binding.Edit3A.text.toString()
            almamacenamiento.modelosControlTrasferencia = binding.Edit3B.text.toString()
            almamacenamiento.tipoMecanismo =  binding.spinnner3C.selectedItem.toString()
            almamacenamiento.tipoSistemaRespaldo =  binding.spinnnerTipoSistemaRespaldo.selectedItem.toString()
            almamacenamiento.funcionamientoATS =  binding.spinnner3D.selectedItem.toString()
            almamacenamiento.poseePrecalentador =  binding.spinnner3E.selectedItem.toString()
            almamacenamiento.tipoPrecalentamiento =  binding.spinnner3G.selectedItem.toString()
            almamacenamiento.voltajeOperacion =  binding.spinnner3H.selectedItem.toString()
            almamacenamiento.estadoPrecalentadorATS =  binding.spinnner3I.selectedItem.toString()
            almamacenamiento.estadoManguerasATS =  binding.spinnner3J.selectedItem.toString()
            almamacenamiento.trabajoRealizado = binding.EditTrabajoRealizado.text.toString()
            almamacenamiento.imagen2 = downloadUri.toString()
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
        val F=  binding.spinnnerF.selectedItem.toString()
        val G=  binding.spinnnerG.selectedItem.toString()
        val H =  binding.spinnnerH.selectedItem.toString()
        val I =  binding.spinnnerI.selectedItem.toString()
        val J =  binding.spinnnerJ.selectedItem.toString()
        val K =  binding.spinnnerK.selectedItem.toString()
        val L =  binding.spinnnerL.selectedItem.toString()
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

        if(A == "" || B == "" || C == "" || D == "" || E == "" || H == ""|| I == ""|| J == "" || K == "" || L == "" || N == ""
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
            val f = almamacenamiento.nivelAguaBaterias.toString()
            val nivelAguaBaterias = condicionAMB(f)
            binding.spinnnerF.setSelection(nivelAguaBaterias)
            val g = almamacenamiento.densidadElectrolitos.toString()
            val densidadElectrolitos = condicion(g)
            binding.spinnnerG.setSelection(densidadElectrolitos)
            val h = almamacenamiento.voltajeBateria.toString()
            val voltajeBateria = condicion(h)
            binding.spinnnerH.setSelection(voltajeBateria)
            val i = almamacenamiento.cargadorFuncional.toString()
            val cargadorFuncional = condicion(i)
            binding.spinnnerI.setSelection(cargadorFuncional)
            val j = almamacenamiento.correasTensionadas.toString()
            val correasTensionadas = condicionAMB(j)
            binding.spinnnerJ.setSelection(correasTensionadas)
            val k = almamacenamiento.estadoFiltroAire.toString()
            val estadoFiltroAire = condicion(k)
            binding.spinnnerK.setSelection(estadoFiltroAire)
            val l = almamacenamiento.estadoMangueras.toString()
            val estadoMangueras = condicion(l)
            binding.spinnnerL.setSelection(estadoMangueras)

            val mDobles = almamacenamiento.estadoTapaRadiador.toString()
            val estadoTapaRadiador = condicionSiNo(mDobles)
            binding.spinnnerNDuplicado.setSelection(estadoTapaRadiador)

            binding.EditODuplicado.setText(almamacenamiento.presionTapaRadiador.toString())

            binding.EditPDuplicado.setText(almamacenamiento.dimensiones)
            val n = almamacenamiento.estadoRacores.toString()
            val estadoRacores = condicion(n)
            binding.spinnnerN.setSelection(estadoRacores)
            val o = almamacenamiento.fugas.toString()
            val fugas = condicionSiNo(o)
            binding.spinnnerO.setSelection(fugas)
            val p = almamacenamiento.estadoCombustible.toString()
            val estadoCombustible = condicion(p)
            binding.spinnnerP.setSelection(estadoCombustible)
            val q = almamacenamiento.estadoTanque.toString()
            val estadoTanque = condicionVHS(q)
            binding.spinnnerQ.setSelection(estadoTanque)
            val r = almamacenamiento.formaTanque.toString()
            val formaTanque = condicionTanque(r)
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
            val objetsExtrasInterior = condicionSiNo(e2)
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

            binding.Edit3A.setText(almamacenamiento.marcaControlTrasferencia.toString())
            binding.Edit3B.setText(almamacenamiento.modelosControlTrasferencia.toString())

            val c3 = almamacenamiento.tipoMecanismo.toString()
            val tipoMecanismo = condicionDisyuntor(c3)
            binding.spinnner3C.setSelection(tipoMecanismo)

            val sistemaRes = almamacenamiento.tipoSistemaRespaldo.toString()
            val tipoSistemaRespaldo = condicionSistemaRespaldo(sistemaRes)
            binding.spinnnerTipoSistemaRespaldo.setSelection(tipoSistemaRespaldo)

            val d3 = almamacenamiento.funcionamientoATS.toString()
            val funcionamientoATS = condicion(d3)
            binding.spinnner3D.setSelection(funcionamientoATS)

            val e3 = almamacenamiento.poseePrecalentador.toString()
            val poseePrecalentador = condicionSiNo(e3)
            binding.spinnner3E.setSelection(poseePrecalentador)

            val g3 = almamacenamiento.tipoPrecalentamiento.toString()
            val tipoPrecalentamiento = condicionResistencia(g3)
            binding.spinnner3G.setSelection(tipoPrecalentamiento)

            val h3 = almamacenamiento.voltajeOperacion.toString()
            val voltajeOperacion = condicionVoltaje(h3)
            binding.spinnner3H.setSelection(voltajeOperacion)

            val i3 = almamacenamiento.estadoPrecalentadorATS.toString()
            val estadoPrecalentadorATS = condicionVoltaje(i3)
            binding.spinnner3H.setSelection(estadoPrecalentadorATS)

            val j3 = almamacenamiento.estadoManguerasATS.toString()
            val estadoManguerasATS = condicion(j3)
            binding.spinnner3H.setSelection(estadoManguerasATS)

            binding.EditTrabajoRealizado.setText(almamacenamiento.trabajoRealizado.toString())

        })
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

    private fun condicionAMB(valor : String): Int{
        var posicion : Int = 0
        when(valor){
            "A"-> posicion = 1
            "M"-> posicion = 2
            "B"-> posicion = 3
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

    private fun condicionTanque(valor : String): Int{
        var posicion : Int = 0
        when(valor){
            "CILINDRICO"-> posicion = 1
            "CUADRADO"-> posicion = 2
        }
        return posicion
    }

    private fun condicionDisyuntor(valor : String): Int{
        var posicion : Int = 0
        when(valor){
            "CONTACTOR"-> posicion = 1
            "INTERRUPTOR"-> posicion = 2
            "INTERRUPTOR MOTORIZADO"-> posicion = 3
            "DOBLE TIRO"-> posicion = 4
            "MANUAL"-> posicion = 5
        }
        return posicion
    }

    private fun condicionSistemaRespaldo(valor : String): Int{
        var posicion : Int = 0
        when(valor){
            "ATS PLANTA- RED"-> posicion = 1
            "ATS PLANTA-PLANTA"-> posicion = 2
            "ATS RED-PLANTA-PLANTA DOBLE TIRO"-> posicion = 3
            "SISTEMA DE SINCRONISMO"-> posicion = 4
        }
        return posicion
    }

    private fun condicionVHS(valor : String): Int{
        var posicion : Int = 0
        when(valor){
            "V"-> posicion = 1
            "H"-> posicion = 2
            "S"-> posicion = 2
        }
        return posicion
    }

    private fun condicionResistencia(valor : String): Int{
        var posicion : Int = 0
        when(valor){
            "Resistencia Interna"-> posicion = 1
            "Externo tipo Botella"-> posicion = 2
        }
        return posicion
    }

    private fun condicionVoltaje(valor : String): Int{
        var posicion : Int = 0
        when(valor){
            "110V"-> posicion = 1
            "220V"-> posicion = 2
            "440V"-> posicion = 3
            "480V"-> posicion = 4
            "12 VDC"-> posicion = 5
            "24 VDC"-> posicion = 6
        }
        return posicion
    }



    public fun openCamera() {
        //val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //startActivityForResult(camera, 1000)
        val file = createImageFile()
        filePath = if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.N){
            FileProvider.getUriForFile(this,
                "$packageName.provider",
                file)
            //

        }else{
            Uri.fromFile(file)
        }

        getContent.launch(filePath)
        //

    }
    public fun createImageFile(): File {
        val filename = "superhero_image"
        val fileDIrectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(filename, ".jpg", fileDIrectory)
        picturePath = file.absolutePath
        return file
    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                heroImage.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    public fun uploadImage(){
        if(filePath != null){
            val ref = storageReference?.child("myImages/" + UUID.randomUUID().toString())
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