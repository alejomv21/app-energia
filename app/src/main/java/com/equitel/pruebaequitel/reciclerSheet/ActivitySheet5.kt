package com.equitel.pruebaequitel.reciclerSheet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.equitel.pruebaequitel.Almacenamiento
import com.equitel.pruebaequitel.R
import com.equitel.pruebaequitel.Sheet6.Sheet6Activity
import com.equitel.pruebaequitel.databinding.ActivitySheet5Binding
import com.equitel.pruebaequitel.main.MainActivity

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
        val adapter = EqAdapter(this)
        binding.eqRecicler.adapter = adapter

        viewModel.almacenamiento.observe(this, Observer {
                almacenamiento->

                adapter.submitList(almacenamiento)

        })
        adapter.setOnItemClickListener1 {
            Toast.makeText(this, "INGRESANDO A ELEMENTOS ALMACENADOS", Toast.LENGTH_SHORT).show()
            openDetailActivity(it)
        }

    }

    private fun openDetailActivity(almacenamiento: Almacenamiento) {
        val intent = Intent(this, Sheet6Activity::class.java)
        intent.putExtra(Sheet6Activity.EQ_KEY, almacenamiento)
        startActivity(intent)
    }




}

