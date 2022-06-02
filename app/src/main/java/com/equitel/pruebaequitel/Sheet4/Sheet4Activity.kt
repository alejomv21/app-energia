package com.equitel.pruebaequitel.Sheet4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.equitel.pruebaequitel.R
import com.equitel.pruebaequitel.databinding.ActivitySheet4Binding
import com.equitel.pruebaequitel.reciclerSheet.ActivitySheet5

class Sheet4Activity : AppCompatActivity() {
    lateinit var binding : ActivitySheet4Binding
    lateinit var viewModel: Sheet4ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySheet4Binding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this,
            Sheet4ViewModelFactory(application)).get(Sheet4ViewModel::class.java)

        binding.ButtonEnviar.setOnClickListener {
            guardarAlmacenamiento()
            val intent = Intent(this, ActivitySheet5::class.java)
            startActivity(intent)
        }
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

            viewModel.guardaralmacenamiento(almacenamiento)
        })
    }
}