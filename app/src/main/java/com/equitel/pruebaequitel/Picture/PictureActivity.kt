package com.equitel.pruebaequitel.Picture

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
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.equitel.pruebaequitel.R
import com.equitel.pruebaequitel.Sheet2.Sheet2ViewModel
import com.equitel.pruebaequitel.databinding.ActivityPictureBinding
import com.equitel.pruebaequitel.databinding.ActivitySheet2Binding
import com.equitel.pruebaequitel.reciclerSheet.ActivitySheet5
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException
import java.util.*

class PictureActivity : AppCompatActivity() {
    lateinit var binding : ActivityPictureBinding
    lateinit var viewModel: PictureViewModel
    //Firebase Fotos
    private val PICK_IMAGE_REQUEST = 71
    private var downloadUri1 : Uri? = null
    private var downloadUri2 : Uri? = null
    private var downloadUri3 : Uri? = null
    private var downloadUri4 : Uri? = null
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    //

    //camera
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
        binding = ActivityPictureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            PictureViewModelFactory(application)
        ).get(PictureViewModel::class.java)

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        binding.buttonEnviar.setOnClickListener {
            guardarDatos()
            val intent = Intent(this, ActivitySheet5::class.java)
            startActivity(intent)
        }

        binding.imageButtonFoto1Capturar.setOnClickListener{
            heroImage = binding.imageViewFoto1
            openCamera()
        }
        binding.imageButtonFoto2Capturar.setOnClickListener{
            heroImage = binding.imageViewFoto2
            openCamera()
        }
        binding.imageButtonFoto3Capturar.setOnClickListener{
            heroImage = binding.imageViewFoto3
            openCamera()
        }
        binding.imageButtonFoto4Capturar.setOnClickListener{
            heroImage = binding.imageViewFoto4
            openCamera()
        }

        binding.imageButtonFoto1Subir.setOnClickListener{
            heroImage = binding.imageViewFoto1
            launchGallery()
        }

        binding.imageButtonFoto2Subir.setOnClickListener{
            heroImage = binding.imageViewFoto2
            launchGallery()
        }
        binding.imageButtonFoto3Subir.setOnClickListener{
            heroImage = binding.imageViewFoto3
            launchGallery()
        }
        binding.imageButtonFoto4Subir.setOnClickListener{
            heroImage = binding.imageViewFoto4
            launchGallery()
        }

        binding.imageButtonFoto1Firebase.setOnClickListener{
            heroImage = binding.imageViewFoto1
            uploadImage("1")
        }
        binding.imageButtonFoto2Firebase.setOnClickListener{
            heroImage = binding.imageViewFoto2
            uploadImage("2")
        }
        binding.imageButtonFoto3Firebase.setOnClickListener{
            heroImage = binding.imageViewFoto3
            uploadImage("3")
        }
        binding.imageButtonFoto4Firebase.setOnClickListener{
            heroImage = binding.imageViewFoto4
            uploadImage("4")
        }
    }

    fun openCamera() {
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
    fun createImageFile(): File {
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

    fun uploadImage(urlGuardar : String){
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
                    when(urlGuardar){
                        "1"-> downloadUri1 = task.result
                        "2"-> downloadUri2 = task.result
                        "3"-> downloadUri3 = task.result
                        "4"-> downloadUri4 = task.result
                    }
                    println(downloadUri1)
                } else {
                    // Handle failures
                    // ...
                }
            }
        }else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
    }


    //Extraer y guardar datos

    private fun guardarDatos(){
        viewModel.extraerAlmacenamientoFotos()
        viewModel.almacenamiento.observe(this, Observer {
            almacenamientos ->
            almacenamientos.imagen1 = downloadUri1.toString()
            almacenamientos.imagen2 = downloadUri2.toString()
            almacenamientos.imagen3 = downloadUri3.toString()
            almacenamientos.imagen4 = downloadUri4.toString()
            viewModel.guardarAlmacenamientoFotos(almacenamientos)
        })
    }
}