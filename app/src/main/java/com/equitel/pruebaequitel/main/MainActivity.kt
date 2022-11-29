package com.equitel.pruebaequitel.main

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
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.equitel.pruebaequitel.Almacenamiento
import com.equitel.pruebaequitel.R
import com.equitel.pruebaequitel.SerialPlanta
import com.equitel.pruebaequitel.Sheet2.Sheet2Activity
import com.equitel.pruebaequitel.Signatures.SignatureActivity
import com.equitel.pruebaequitel.api.ApiResponseStatus
import com.equitel.pruebaequitel.databinding.ActivityMainBinding
import com.equitel.pruebaequitel.reciclerSheet.ActivitySheet5
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {
    //firebase
    private val PICK_IMAGE_REQUEST = 71
    private var downloadUri : Uri? = null
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    lateinit var btn_choose_image: ImageButton
    lateinit var btn_upload_image: ImageButton

    private lateinit var viewModel: MainViewModel
    lateinit var binding : ActivityMainBinding
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bundle = intent.extras!!
        val idEquipo = bundle.getString("id_equipo") ?: ""
        val ordenTrabajo = bundle.getString("orden_trabajo") ?: ""
        val checkVariable = bundle.getString("check_variable") ?: ""

        checkTipoUbicacion()
        viewModel = ViewModelProvider(this,
            MainViewModelFactory(application, ordenTrabajo)).get(MainViewModel::class.java)



        viewModel.eqList.observe(this, Observer {
            eqList : SerialPlanta ->
            cambioValoresFactura(eqList)
        })

        viewModel.status.observe(this, Observer {
            apiResponseStatus : ApiResponseStatus ->
            if(apiResponseStatus == ApiResponseStatus.LOADING){
                    binding.loading.visibility = View.VISIBLE
            }else if (apiResponseStatus == ApiResponseStatus.DONE){
                    binding.loading.visibility = View.GONE
            }else if (apiResponseStatus == ApiResponseStatus.NOT_INTERNET_CONNECTION){
                binding.loading.visibility = View.GONE
            }
        })

        viewModel.serialMotor.observe(this, Observer {
            serialMotor ->
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, serialMotor)
                binding.EditSerialMotor.setAdapter(adapter)
        })

            viewModel.cliente.observe(this, Observer {
                    cliente ->
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cliente)
                binding.EditOCliente.setAdapter(adapter)
            })

        viewModel.MarcaGenerador.observe(this, Observer {
                marcaGenerador ->
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, marcaGenerador)
            binding.EditMarcaGen.setAdapter(adapter)
        })

        viewModel.Marcaplanta.observe(this, Observer {
                marcaplanta ->
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, marcaplanta)
            binding.EditMarcaPlanta.setAdapter(adapter)
        })

        viewModel.ModeloGenerador.observe(this, Observer {
                modeloGenrador ->
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, modeloGenrador)
            binding.EditModGen.setAdapter(adapter)
        })

        viewModel.ModeloMotor.observe(this, Observer {
                modeloMotor ->
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, modeloMotor)
            binding.EditModMotor.setAdapter(adapter)
        })

        viewModel.ModeloPlanta.observe(this, Observer {
                modeloPlanta ->
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, modeloPlanta)
            binding.EditModPlanta.setAdapter(adapter)
        })

        viewModel.SerialGenerador.observe(this, Observer {
                serialGenerador ->
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, serialGenerador)
            binding.EditSerialGenerador.setAdapter(adapter)
        })

        viewModel.SerialPlanta.observe(this, Observer {
                serialPlanta ->
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, serialPlanta)
            binding.EditSerialPlanta.setAdapter(adapter)
        })

        viewModel.tecnico.observe(this, Observer {
                tecnico ->
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tecnico)
            binding.EditTecnicosCargo1.setAdapter(adapter)
            binding.EditTecnicosCargo2.setAdapter(adapter)
        })

        viewModel.hora.observe(this, Observer {
            hora->
            binding.EditFecha.setText(hora)
        })


        binding.EditSpec.setOnClickListener {
            viewModel.extraerAlmacenamiento()

        }
        val tipoServicio : Array<String> = resources.getStringArray(R.array.tipoServicio)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipoServicio)
        binding.EditMotivoVisita.setAdapter(adapter)

        val sede : Array<String> = resources.getStringArray(R.array.sede)
        val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, sede)
        binding.EditSede.setAdapter(adaptador)

        pruebas(ordenTrabajo)

        validarTipoServicio(checkVariable)

        heroImage = binding.Camera1
        btn_choose_image = binding.ButtonCameraSearch
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        btn_upload_image = binding.ButtonCamera1Upload
        binding.ButtonCamera1.setOnClickListener{
            openCamera()
        }
        btn_choose_image.setOnClickListener { launchGallery() }
        btn_upload_image.setOnClickListener { uploadImage() }

        binding.ButtonGuadado.setOnClickListener{
            agragarAlmacenamiento(idEquipo, ordenTrabajo, checkVariable)
            cambioSheet2()
        }
    }

    private fun cambioValoresFactura(serialPlanta: SerialPlanta) {
        validarCheckUbicacacion(serialPlanta)
        binding.Editciudad.setText(serialPlanta.ciudad)
        binding.EditSede.setText(serialPlanta.ciudad)
        binding.EditOCliente.setText(serialPlanta.cliente)
        binding.EditDir.setText(serialPlanta.dir)
        binding.EditMarcaMotor.setText(serialPlanta.MarcaMotor)
        binding.EditMarcaGen.setText(serialPlanta.marcaGen)
        binding.EditMarcaPlanta.setText(serialPlanta.marcaPlnata)
        binding.EditModMotor.setText(serialPlanta.modMotor)
        binding.EditModGen.setText(serialPlanta.modGen)
        binding.EditModPlanta.setText(serialPlanta.modPlanta)
        binding.EditSerialMotor.setText(serialPlanta.snMotor)
        binding.EditSerialGenerador.setText(serialPlanta.snGen)
        binding.EditSerialPlanta.setText(serialPlanta.snPlanta)
        binding.EditClp.setText(serialPlanta.cpl)
        binding.EditKW.setText(serialPlanta.kw)
        binding.EditSpec.setText(serialPlanta.spec)
        binding.EditTipoControl.setText(serialPlanta.tipoControl)
        binding.EditTecnicosCargo1.setText(serialPlanta.tecnicosCargo)
        binding.EditPromotionID.setText(serialPlanta.promotionID)
        binding.EditMotivoVisita.setText(serialPlanta.motivoVisita)

    }

    private fun agragarAlmacenamiento(idEquipo : String, ordenTrabajo : String, checkVariable : String){
        viewModel.extraerAlmacenamiento()
        //checkTipoUbicacion()

        viewModel.almacenamiento.observe(this, Observer {
            almacenamiento->
            almacenamiento.idEquipo = idEquipo
            almacenamiento.ordenTrabajo = ordenTrabajo
            almacenamiento.tipoServicio = checkVariable
            almacenamiento.tipoUbicacion = binding.TextTipoUbicacion.text.toString()
            almacenamiento.tipoEquipo = binding.TextTipoEquipo.text.toString()
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
            almacenamiento.imagen1 = downloadUri.toString()

            viewModel.GuardarAlmacenamiento(almacenamiento)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if(itemId == R.id.main_menu_latest){
            viewModel.extraerAlmacenamiento()
            viewModel.almacenamiento.observe(this, Observer {
                    almacenamient ->
                descargarAlmacenamiento(almacenamient)
            })
        }
        return super.onOptionsItemSelected(item)
    }

    private fun validarTipoServicio(tipoServicio : String){
        if(tipoServicio == "MANTENIMIENTO" || tipoServicio == "ARTIMO" || tipoServicio == "SERVICIO" || tipoServicio == "MONTAJE" || tipoServicio == "ENTREGA"){
            binding.EditSerialMotor.isFocusableInTouchMode = true
            binding.EditSerialPlanta.isFocusableInTouchMode = true
            binding.EditSerialGenerador.isFocusableInTouchMode = true
        }
    }

    private fun descargarAlmacenamiento(almacenamiento: Almacenamiento){
        binding.Editciudad.setText(almacenamiento.sede)
        binding.EditSede.setText(almacenamiento.sede)
        binding.Editciudad.setText(almacenamiento.ciudad)
        binding.EditFecha.setText(almacenamiento.fecha)
        binding.EditOCliente.setText(almacenamiento.cliente)
        binding.EditDir.setText(almacenamiento.direccion)
        binding.EditServicioSolicitado.setText(almacenamiento.servicioSolicitadoPor)
        binding.EditCargo.setText(almacenamiento.cargo)
        binding.EditEmail.setText(almacenamiento.email)
        binding.EditCell.setText(almacenamiento.tel)
        binding.EditMarcaMotor.setText(almacenamiento.marcaMotor)
        binding.EditMarcaGen.setText(almacenamiento.marcaGenerador)
        binding.EditMarcaPlanta.setText(almacenamiento.marcaPlanta)
        binding.EditModMotor.setText(almacenamiento.modMotor)
        binding.EditModGen.setText(almacenamiento.modGenerador)
        binding.EditModPlanta.setText(almacenamiento.modPlanta)
        binding.EditSerialMotor.setText(almacenamiento.serialMotor)
        binding.EditSerialGenerador.setText(almacenamiento.serialGenerador)
        binding.EditSerialPlanta.setText(almacenamiento.serialPlanta)
        binding.EditClp.setText(almacenamiento.clp)
        binding.EditKW.setText(almacenamiento.kw)
        binding.EditSpec.setText(almacenamiento.spec)
        binding.EditNArranques.setText(almacenamiento.nArranques)
        binding.EditHorasMotor.setText(almacenamiento.horasMotor)
        binding.EditHorasControl.setText(almacenamiento.horasControl)
        binding.EditTipoControl.setText(almacenamiento.tipoControl)
        binding.EditTecnicosCargo1.setText(almacenamiento.tecnicos)
        binding.EditPromotionID.setText(almacenamiento.promotionID)
        binding.EditMotivoVisita.setText(almacenamiento.motivoVisita)
    }

    private fun validarCheckUbicacacion(serialPlanta: SerialPlanta){
        when(serialPlanta.tipoUbicacion.toString().uppercase(Locale.getDefault())){
            "CABINA" -> binding.checkBoxCabina.isEnabled = true
            "CUARTO" -> binding.checkBoxCuarta.isEnabled = true
            "ABIERTO" -> binding.checkBoxAbierto.isEnabled = true
            "OTRO" -> binding.checkBoxOtro.isEnabled = true
        }
        when(serialPlanta.tipoEquipo.toString().uppercase(Locale.getDefault())){
            "ACPM"-> binding.checkBoxACPM.isEnabled = true
            "GASOLINA"-> binding.checkBoxGasol.isEnabled = true
            "GAS"-> binding.checkBoxGas.isEnabled = true
            "BAT"-> binding.checkBoxBat.isEnabled = true
        }
    }

    private fun pruebas(ordenTrabajo: String){
        viewModel.prueba(ordenTrabajo)
    }

    private fun cambioSheet2(){
        //val intent = Intent(this, ActivitySheet5::class.java)
        val intent = Intent(this, Sheet2Activity::class.java)
        startActivity(intent)
    }
    private fun openCamera() {
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
    private fun createImageFile(): File {
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

    private fun uploadImage(){
        if(filePath != null){
            val ref = storageReference?.child("myImages/" + UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)
            Toast.makeText(this, "Imagen Guardada", Toast.LENGTH_SHORT).show()
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
    private fun checkTipoUbicacion(){
        binding.checkBoxCabina.setOnClickListener {
            binding.TextTipoUbicacion.setText("CABINA")
        }
        binding.checkBoxCuarta.setOnClickListener {
            binding.TextTipoUbicacion.setText("CUARTO")
        }
        binding.checkBoxAbierto.setOnClickListener {
            binding.TextTipoUbicacion.setText("ABIERTO")
        }
        binding.checkBoxOtro.setOnClickListener {
            binding.TextTipoUbicacion.setText("OTRO")
        }
        binding.checkBoxACPM.setOnClickListener {
            binding.TextTipoEquipo.setText("ACPM")
        }
        binding.checkBoxGasol.setOnClickListener {
            binding.TextTipoEquipo.setText("GASOL")
        }
        binding.checkBoxGas.setOnClickListener {
            binding.TextTipoEquipo.setText("GAS")
        }
        binding.checkBoxBat.setOnClickListener {
            binding.TextTipoEquipo.setText("BAT")
        }
    }


}