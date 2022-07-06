package com.equitel.pruebaequitel.Sheet7

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
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.equitel.pruebaequitel.R
import com.equitel.pruebaequitel.SearchActivity

import com.equitel.pruebaequitel.databinding.ActivitySheet7Binding
import com.equitel.pruebaequitel.reciclerSheet.ActivitySheet5
import java.io.File
import java.util.*

class Sheet7Activity : AppCompatActivity() {
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

        spinnerAdapter(adapter, adapter1, timepoadapter, siNoNaAadapter)

        heroImage = binding.Camera2
        binding.ButtonCamera2.setOnClickListener{
            openCamera()
        }

        binding.buttonEnviar.setOnClickListener {
            Toast.makeText(this, "ELEMENTOS GUARDADOS ", Toast.LENGTH_SHORT).show()
            guardar()
            val intent = Intent(this, ActivitySheet5::class.java)
            startActivity(intent)
        }

        Calendario();

    }
    private fun spinnerAdapter(adapter : ArrayAdapter<String>, adapter1: ArrayAdapter<String>, timepoAdapter: ArrayAdapter<String>, siNoNaAadapter: ArrayAdapter<String>){
        binding.spinnnerA.setAdapter(adapter)
        binding.spinnnerB.setAdapter(adapter)
        binding.spinnnerE.setAdapter(adapter1)
        binding.spinnnerF.setAdapter(adapter1)
        binding.spinnnerG.setAdapter(adapter1)
        binding.spinnnerK.setAdapter(adapter1)
        binding.spinnnerL.setAdapter(adapter1)
        binding.spinnnerM.setAdapter(adapter1)
        binding.spinnnerNDuplicado.setAdapter(adapter1)
        binding.spinnnerN.setAdapter(siNoNaAadapter)
        binding.spinnnerO.setAdapter(adapter1)
        binding.spinnnerODuplicado.setAdapter(siNoNaAadapter)
        binding.spinnnerP.setAdapter(timepoAdapter)
        binding.spinnnerQ.setAdapter(timepoAdapter)
        binding.spinnnerR.setAdapter(adapter)
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

            Log.d("MANZANAs", almamacenamiento.estadoControl.toString())

            viewModel.guardaralmacenamiento(almamacenamiento)

        })
    }

    private fun openCamera() {
        //val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //startActivityForResult(camera, 1000)
        val file = createImageFile()
        val uri = if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.N){
            FileProvider.getUriForFile(this,
                "$packageName.provider",
                file)
        }else{
            Uri.fromFile(file)
        }
        getContent.launch(uri)
    }
    private fun createImageFile(): File {
        val filename = "superhero_image"
        val fileDIrectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(filename, ".jpg", fileDIrectory)
        picturePath = file.absolutePath
        return file
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