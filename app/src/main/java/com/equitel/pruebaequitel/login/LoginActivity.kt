package com.equitel.pruebaequitel.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.equitel.pruebaequitel.SearchActivity
import com.equitel.pruebaequitel.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        button()
    }

    private fun button(){
        binding.buttonEnviar1.setOnClickListener {
            val usuario = binding.EditUsuario.text.toString()
            viewModel.validarContrasena(usuario)
            viewModel.contrasena.observe(this, Observer {
                contrasena->
                    val password = binding.EditPassword.text.toString()
                    if(contrasena == password){
                        val intent = Intent(this, SearchActivity::class.java)
                        startActivity(intent)
                    }else if(contrasena == "usuario no es valido"){
                        Toast.makeText(this, "usuario no es valido", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show()
                    }
            })

        }
    }
}