package com.equitel.pruebaequitel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.equitel.pruebaequitel.databinding.ActivitySearchBinding
import com.equitel.pruebaequitel.main.MainActivity

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,
             SearchViewModelFactory(    application)).get(SearchViewModel::class.java)


        onClick()
        OnclickSend()
        checkValidate()
    }

    private fun onClick() {
        binding.button.setOnClickListener {
            check()
            val almacen = validarID()
            guardarId(almacen)
        }
    }

    private fun checkValidate(){
        binding.checkBoxInspeccion.setOnClickListener {
            binding.textViewCheckSeleccion.text = "INSPECCION"
        }
        binding.checkBoxMantenimiento.setOnClickListener {
            binding.textViewCheckSeleccion.text = "MANTENIMIENTO"
        }
        binding.checkBoxArtimo.setOnClickListener {
            binding.textViewCheckSeleccion.text = "ARTIMO"
        }
        binding.checkBoxServicio.setOnClickListener {
            binding.textViewCheckSeleccion.text = "SERVICIO"
        }
        binding.checkBoxMontaje.setOnClickListener {
            binding.textViewCheckSeleccion.text = "MONTAJE"
        }
        binding.checkBoxEntrega.setOnClickListener {
            binding.textViewCheckSeleccion.text = "ENTREGA"
        }
        binding.checkBoxEmergencia.setOnClickListener {
            binding.textViewCheckSeleccion.text = "EMERGENCIA"
        }
    }
    private fun check(){
        if(binding.checkBoxArtimo.isChecked || binding.checkBoxMantenimiento.isChecked || binding.checkBoxServicio.isChecked || binding.checkBoxMontaje.isChecked){
            binding.textViewOrdenTrabajo.visibility = View.VISIBLE
            binding.editTextOrdenTrabajo.visibility = View.VISIBLE
            binding.textViewIdEquipo.visibility = View.VISIBLE
            binding.EditTextIdEquipo.visibility = View.VISIBLE
        }else if(binding.checkBoxEntrega.isChecked || binding.checkBoxEmergencia.isChecked){
            binding.textViewOrdenTrabajo.visibility = View.GONE
            binding.editTextOrdenTrabajo.visibility = View.GONE
            binding.textViewIdEquipo.visibility = View.VISIBLE
            binding.EditTextIdEquipo.visibility = View.VISIBLE
        }else if(binding.checkBoxInspeccion.isChecked){
            binding.textViewOrdenTrabajo.visibility = View.GONE
            binding.editTextOrdenTrabajo.visibility = View.GONE
            binding.textViewIdEquipo.visibility = View.GONE
            binding.EditTextIdEquipo.visibility = View.GONE
        }
        else{
            Toast.makeText(this, "debe seleccionar una opcion", Toast.LENGTH_LONG).show()
        }
    }

    private fun OnclickSend(){
        binding.buttonSend.setOnClickListener {
            val idEquipo = binding.EditTextIdEquipo.text.toString()
            val ordenTrabajo = binding.editTextOrdenTrabajo.text.toString()
            val checkVariable = binding.textViewCheckSeleccion.text.toString()
            OpenMainActivity(idEquipo, ordenTrabajo, checkVariable)
        }
    }

    private fun OpenMainActivity(idEquipo : String, ordenTrabajo : String, checkVariable : String){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("id_equipo", idEquipo)
        intent.putExtra("orden_trabajo", ordenTrabajo)
        intent.putExtra("check_variable", checkVariable)
        startActivity(intent)
    }

    private fun guardarId(almacenamiento: Almacenamiento){
        almacenamiento.idEquipo = binding.textViewIdEquipo.toString()
        viewModel.GuardarItem(almacenamiento)
    }

    private fun validarID(): Almacenamiento{
        val almacanamiento = Almacenamiento()
        if(!binding.EditTextIdEquipo.toString().isEmpty()){
            almacanamiento.idEquipo = binding.EditTextIdEquipo.toString()
        }
        return almacanamiento
    }




}