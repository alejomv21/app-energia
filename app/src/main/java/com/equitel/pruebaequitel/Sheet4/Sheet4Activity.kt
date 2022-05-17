package com.equitel.pruebaequitel.Sheet4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.equitel.pruebaequitel.R
import com.equitel.pruebaequitel.databinding.ActivitySheet4Binding
import com.equitel.pruebaequitel.login.LoginActivity
import com.equitel.pruebaequitel.reciclerSheet.ActivitySheet5

class Sheet4Activity : AppCompatActivity() {
    lateinit var binding : ActivitySheet4Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySheet4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ButtonEnviar.setOnClickListener {
            val intent = Intent(this, ActivitySheet5::class.java)
            startActivity(intent)
        }
    }
}