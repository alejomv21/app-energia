package com.equitel.pruebaequitel.Sheet7

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
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
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.equitel.pruebaequitel.Picture.PictureActivity
import com.equitel.pruebaequitel.R
import com.equitel.pruebaequitel.SearchActivity

import com.equitel.pruebaequitel.databinding.ActivitySheet7Binding
import com.equitel.pruebaequitel.reciclerSheet.ActivitySheet5
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException
import java.util.*

class Sheet7Activity : AppCompatActivity() {
    //firebase
    private val PICK_IMAGE_REQUEST = 71
    private var downloadUri : Uri? = null
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    lateinit var btn_choose_image: ImageButton
    lateinit var btn_upload_image: ImageButton


    lateinit var binding : ActivitySheet7Binding
    lateinit var viewModel: Sheet7ViewModel
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
        binding = ActivitySheet7Binding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, Sheet7ViewModelFactory(application)).get(Sheet7ViewModel::class.java)

        val siNo : Array<String> = resources.getStringArray(R.array.SiNo)
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_list_item_1, siNo)

        val tiempo : Array<String> = resources.getStringArray(R.array.tiempo)
        val timepoadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tiempo)

        val tipoServicio : Array<String> = resources.getStringArray(R.array.OpcionesHoja2)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipoServicio)

        val siNoNa : Array<String> = resources.getStringArray(R.array.siNoNa)
        val siNoNaAadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, siNoNa)

        val servicio_a_cotizar : Array<String> = resources.getStringArray(R.array.servicio_a_cotizar)
        val servicio_a_cotizarAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, servicio_a_cotizar)

        val tipo_servicio_realizado : Array<String> = resources.getStringArray(R.array.tipo_servicio_realizado)
        val tipo_servicio_realizadoAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipo_servicio_realizado)

        spinnerAdapter(adapter, adapter1, timepoadapter, siNoNaAadapter, servicio_a_cotizarAdapter, tipo_servicio_realizadoAdapter)

        heroImage = binding.Camera2
        btn_choose_image = binding.ButtonCameraSearch
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        btn_upload_image = binding.ButtonCamera7Upload
        binding.ButtonCamera7.setOnClickListener{
            openCamera()
        }
        btn_choose_image.setOnClickListener { launchGallery() }
        btn_upload_image.setOnClickListener { uploadImage() }

        binding.buttonEnviar.setOnClickListener {
            Toast.makeText(this, "ELEMENTOS GUARDADOS ", Toast.LENGTH_SHORT).show()
            guardar()
            val intent = Intent(this, PictureActivity::class.java)
            startActivity(intent)
        }

        Calendario();

    }
    private fun spinnerAdapter(adapter : ArrayAdapter<String>, adapter1: ArrayAdapter<String>, timepoAdapter: ArrayAdapter<String>, siNoNaAadapter: ArrayAdapter<String>, servicio_a_cotizarAdapter: ArrayAdapter<String>, tipo_servicio_realizadoAdapter: ArrayAdapter<String>){
        binding.spinnnerA.setAdapter(adapter)
        binding.spinnnerB.setAdapter(adapter)
        binding.spinnnerE.setAdapter(adapter1)
        binding.spinnnerF.setAdapter(adapter1)
        binding.spinnnerG.setAdapter(adapter1)
        binding.spinnnerK.setAdapter(adapter1)
        binding.spinnnerL.setAdapter(adapter1)
        binding.spinnnerM.setAdapter(adapter1)
        binding.spinnnerNDuplicado.setAdapter(adapter1)

        binding.spinnnerTrabajoRiesgoTapete.setAdapter(adapter1)
        binding.spinnnerTrabajoRiesgoCARETA.setAdapter(adapter1)
        binding.spinnnerTrabajoRiesgoOverol.setAdapter(adapter1)
        binding.spinnnerTrabajoRiesgoHerramienta.setAdapter(adapter1)
        binding.spinnnerTrabajoEspaciosConfinadosCareta.setAdapter(adapter1)
        binding.spinnnerTrabajoEspaciosConfinadosLinea.setAdapter(adapter1)
        binding.spinnnerTrabajoEspaciosConfinadosAndamio.setAdapter(adapter1)
        binding.spinnnerTrabajoAlturasEslinga.setAdapter(adapter1)
        binding.spinnnerTrabajoAlturasLinea.setAdapter(adapter1)
        binding.spinnnerTrabajoAlturasAndamio.setAdapter(adapter1)
        binding.spinnnertextTrabajoAlturasArnes.setAdapter(adapter1)


        binding.spinnnerN.setAdapter(siNoNaAadapter)
        binding.spinnnerO.setAdapter(adapter1)
        binding.spinnnerODuplicado.setAdapter(siNoNaAadapter)
        binding.spinnnerP.setAdapter(timepoAdapter)
        binding.spinnnerQ.setAdapter(timepoAdapter)
        binding.spinnnerR.setAdapter(servicio_a_cotizarAdapter)
        binding.spinnnerTipoServicioRealizado.setAdapter(tipo_servicio_realizadoAdapter)
        binding.spinnnerAtsTrealizar.setAdapter(adapter1)
        binding.spinnnerAtsTalturas.setAdapter(adapter1)
        binding.spinnnerAtsTConfinados.setAdapter(adapter1)
        binding.spinnnerAtscaliente.setAdapter(adapter1)
    }


    private fun guardar(){
        viewModel.extraerAlmacenamiento()
        viewModel.almacenamiento.observe(this, Observer {
                almamacenamiento->

            almamacenamiento.estadoConexion =  binding.spinnnerA.selectedItem.toString()
            almamacenamiento.sensores =  binding.spinnnerB.selectedItem.toString()
            almamacenamiento.alarmas =  binding.EditAlarma.text.toString()
            almamacenamiento.observaciones =  binding.EditObservaciones.text.toString()
            almamacenamiento.muestraAceite =  binding.spinnnerE.selectedItem.toString()
            almamacenamiento.muestraCombustible =  binding.spinnnerF.selectedItem.toString()
            almamacenamiento.muestraRefrigerante =  binding.spinnnerG.selectedItem.toString()
            almamacenamiento.ultimoLavadoTanque =  binding.EditUltimoLavado.text.toString()
            almamacenamiento.ultimoTanqueo =  binding.EditUltimoTanqueo.text.toString()
            almamacenamiento.capacidadTanqueo =  binding.EditCapacidadTanque.text.toString()
            almamacenamiento.casco =  binding.spinnnerK.selectedItem.toString()
            almamacenamiento.guantes =  binding.spinnnerL.selectedItem.toString()
            almamacenamiento.overol =  binding.spinnnerM.selectedItem.toString()
            almamacenamiento.gafas =  binding.spinnnerNDuplicado.selectedItem.toString()
            almamacenamiento.otros =  binding.EditOtros.text.toString()

            almamacenamiento.trabajoRiesgoTapete =  binding.spinnnerTrabajoRiesgoTapete.selectedItem.toString()
            almamacenamiento.trabajoRiesgoCareta =  binding.spinnnerTrabajoRiesgoCARETA.selectedItem.toString()
            almamacenamiento.trabajoRiesgoOverol =  binding.spinnnerTrabajoRiesgoOverol.selectedItem.toString()
            almamacenamiento.trabajoRiesgoHerramienta =  binding.spinnnerTrabajoRiesgoHerramienta.selectedItem.toString()
            almamacenamiento.trabajoEspaciosConfinadosCareta =  binding.spinnnerTrabajoEspaciosConfinadosCareta.selectedItem.toString()
            almamacenamiento.trabajoEspaciosConfinadosLinea =  binding.spinnnerTrabajoEspaciosConfinadosLinea.selectedItem.toString()
            almamacenamiento.trabajoEspaciosConfinadosAndamio =  binding.spinnnerTrabajoEspaciosConfinadosAndamio.selectedItem.toString()
            almamacenamiento.trabajoAlturasEslinga =  binding.spinnnerTrabajoAlturasEslinga.selectedItem.toString()
            almamacenamiento.trabajoAlturasLinea =  binding.spinnnerTrabajoAlturasLinea.selectedItem.toString()
            almamacenamiento.trabajoAlturasAndamio =  binding.spinnnerTrabajoAlturasAndamio.selectedItem.toString()
            almamacenamiento.trabajoAlturasArnes =  binding.spinnnertextTrabajoAlturasArnes.selectedItem.toString()

            almamacenamiento.transporteATimepo =  binding.spinnnerODuplicado.selectedItem.toString()
            almamacenamiento.insumosCompletos =  binding.spinnnerN.selectedItem.toString()
            almamacenamiento.pendienteRecogerInsumos =  binding.spinnnerO.selectedItem.toString()
            almamacenamiento.tiempoEsperaIngreso =  binding.spinnnerP.selectedItem.toString()
            almamacenamiento.tiempoEsperaSalida =  binding.spinnnerQ.selectedItem.toString()
            almamacenamiento.serviciosCotizar =  binding.spinnnerR.selectedItem.toString()
            almamacenamiento.tipoServicioRealizado =  binding.EditTipoServicio.text.toString()
            almamacenamiento.otrosServicios =  binding.EditOtrosServicios.text.toString()
            almamacenamiento.atsTrabajosrealizados = binding.spinnnerAtsTrealizar.selectedItem.toString()
            almamacenamiento.atsTrabajosAlturas = binding.spinnnerAtsTalturas.selectedItem.toString()
            almamacenamiento.atsTrabajosConfinados = binding.spinnnerAtsTConfinados.selectedItem.toString()
            almamacenamiento.atsTrabajosCalientes = binding.spinnnerAtscaliente.selectedItem.toString()
            almamacenamiento.imagen5 = downloadUri.toString()

            Log.d("MANZANAs", almamacenamiento.estadoControl.toString())

            viewModel.guardaralmacenamiento(almamacenamiento)

        })
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if(itemId == R.id.main_menu_latest){
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    class DatePickerFragment (val listener: (year:Int, month:Int, day:Int)-> Unit): DialogFragment(), DatePickerDialog.OnDateSetListener {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = Calendar.getInstance()
            var year = c.get(Calendar.YEAR)
            var month = c.get(Calendar.MONTH)
            var day = c.get(Calendar.DAY_OF_MONTH)

            return DatePickerDialog(requireActivity(), this, year, month, day)

        }

        override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
            listener(year, month+1, day)
        }
    }

    private fun Calendario() {
        binding.fechaUltimoLavado.setOnClickListener {
            val EditButon = binding.EditUltimoLavado
            val DialogFecha = DatePickerFragment { year, month, day ->
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
            val DialogFecha = DatePickerFragment { year, month, day ->
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
}