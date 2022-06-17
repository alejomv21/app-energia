package com.equitel.pruebaequitel.Sheet4

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
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.equitel.pruebaequitel.Sheet7.Sheet7Activity
import com.equitel.pruebaequitel.TimePicket
import com.equitel.pruebaequitel.databinding.ActivitySheet4Binding
import java.io.File
import java.util.*

class Sheet4Activity : AppCompatActivity() {
    lateinit var binding : ActivitySheet4Binding
    lateinit var viewModel: Sheet4ViewModel
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
        binding = ActivitySheet4Binding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this,
            Sheet4ViewModelFactory(application)).get(Sheet4ViewModel::class.java)

        heroImage = binding.Camera4
        binding.ButtonCamera4.setOnClickListener{
            openCamera()
        }

        binding.ButtonEnviar.setOnClickListener {
            Toast.makeText(this, "ELEMENTOS GUARDADOS ", Toast.LENGTH_SHORT).show()
            guardarAlmacenamiento()
            val intent = Intent(this, Sheet7Activity::class.java)
            startActivity(intent)
        }

        Calendario()
        Reloj()
    }


    private fun guardarAlmacenamiento(){
        viewModel.extraerAlmacenamiento()
        viewModel.almacenamiento.observe(this, Observer{
            almacenamiento->
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

            viewModel.guardaralmacenamiento(almacenamiento)
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

    private fun Calendario(){
        binding.TextHoraSalidaT.setOnClickListener { 
            val DialogFecha = DatePickerFragment{year, month, day ->  mostrarResultado(year, month, day)}
            DialogFecha.show(supportFragmentManager, "DataPicker")
        }
    }

    private fun mostrarResultado(year: Int, month: Int, day: Int) {
        binding.TextHoraSalidaT.setText("$year/$month/$day")
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


    private fun Reloj (){
        binding.TextHoraSalidaC.setOnClickListener {
            val horas = TimePicket { hora, minuto -> mostrarHora(hora, minuto) }
            horas.show(supportFragmentManager, "TimePicker")
        }
    }

    private fun mostrarHora(hora: Int, minuto: Int) {
        binding.TextHoraSalidaC.setText("$hora : $minuto")
    }
}