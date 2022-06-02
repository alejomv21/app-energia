package com.equitel.pruebaequitel.Sheet6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.equitel.pruebaequitel.R
import com.equitel.pruebaequitel.databinding.ActivitySheet6Binding

class Sheet6Activity : AppCompatActivity() {
    lateinit var binding : ActivitySheet6Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySheet6Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}