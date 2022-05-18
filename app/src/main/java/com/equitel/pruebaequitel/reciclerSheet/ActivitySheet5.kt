package com.equitel.pruebaequitel.reciclerSheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.equitel.pruebaequitel.Almacenamiento
import com.equitel.pruebaequitel.R
import com.equitel.pruebaequitel.databinding.ActivitySheet5Binding

class ActivitySheet5 : AppCompatActivity() {
    lateinit var binding: ActivitySheet5Binding
    lateinit var viewModel: Sheet5ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySheet5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, Sheet5ViewModelFactory(application)).get(Sheet5ViewModel::class.java)

        binding.eqRecicler.layoutManager = LinearLayoutManager(this)

        //val eqList = mutableListOf<Almacenamiento>()

        ListarAlmacenaientos()
    }

    private fun ListarAlmacenaientos(){
        viewModel.almacenamiento.observe(this, Observer {
            almacenamiento->
                val adapter = EqAdapter()
                binding.eqRecicler.adapter = adapter
                adapter.submitList(almacenamiento)
        })
    }
}

