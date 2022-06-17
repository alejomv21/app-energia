package com.equitel.pruebaequitel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.equitel.pruebaequitel.Sheet6.Sheet6Activity
import com.equitel.pruebaequitel.Sheet7.Sheet7Activity
import com.equitel.pruebaequitel.Sheet8.Sheet8Activity
import com.equitel.pruebaequitel.Signatures.SignatureActivity
import com.equitel.pruebaequitel.databinding.ActivitySearchBinding
import com.equitel.pruebaequitel.main.MainActivity
import com.equitel.pruebaequitel.reciclerSheet.ActivitySheet5

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,
             SearchViewModelFactory(application)).get(SearchViewModel::class.java)


        onClick()
        OnclickSend()
        checkValidate()
        reporgramar()
    }

    private fun onClick() {
        binding.button.setOnClickListener {
            check()
        }
    }

    private fun checkValidate(){
        binding.checkBoxInspeccion.setOnClickListener {
            binding.textViewCheckSeleccion.setText("INSPECCION")
        }
        binding.checkBoxMantenimiento.setOnClickListener {
            binding.textViewCheckSeleccion.setText("MANTENIMIENTO")
        }
        binding.checkBoxArtimo.setOnClickListener {
            binding.textViewCheckSeleccion.setText("ARTIMO")
        }
        binding.checkBoxServicio.setOnClickListener {
            binding.textViewCheckSeleccion.setText("SERVICIO")
        }
        binding.checkBoxMontaje.setOnClickListener {
            binding.textViewCheckSeleccion.setText("MONTAJE")
        }
        binding.checkBoxEntrega.setOnClickListener {
            binding.textViewCheckSeleccion.setText("ENTREGA")
        }
        binding.checkBoxEmergencia.setOnClickListener {
            binding.textViewCheckSeleccion.setText("EMERGENCIA")
        }
    }

    private fun check(){
        if(binding.checkBoxArtimo.isChecked || binding.checkBoxMantenimiento.isChecked || binding.checkBoxServicio.isChecked || binding.checkBoxMontaje.isChecked){
            binding.textViewOrdenTrabajo.visibility = View.VISIBLE
            binding.editTextOrdenTrabajo.visibility = View.VISIBLE
            binding.textViewIdEquipo.visibility = View.VISIBLE
            binding.EditTextIdEquipo.visibility = View.VISIBLE
            binding.textViewTipoEmergencia.visibility = View.GONE
            binding.EditTextTipoEmergencia.visibility = View.GONE
            binding.textViewCausaFalla.visibility = View.GONE
            binding.EditTextCausaFalla.visibility = View.GONE
            binding.textViewMotivoReprogramar.visibility = View.GONE
            binding.EditTextMotivoReprogramar.visibility = View.GONE
            binding.ButtonReprogramar.visibility = View.GONE
        }else if(binding.checkBoxEntrega.isChecked ){
            binding.textViewOrdenTrabajo.visibility = View.GONE
            binding.editTextOrdenTrabajo.visibility = View.GONE
            binding.textViewIdEquipo.visibility = View.VISIBLE
            binding.EditTextIdEquipo.visibility = View.VISIBLE
            binding.textViewTipoEmergencia.visibility = View.GONE
            binding.EditTextTipoEmergencia.visibility = View.GONE
            binding.textViewCausaFalla.visibility = View.GONE
            binding.EditTextCausaFalla.visibility = View.GONE
            binding.textViewMotivoReprogramar.visibility = View.GONE
            binding.EditTextMotivoReprogramar.visibility = View.GONE
            binding.ButtonReprogramar.visibility = View.GONE
        }else if(binding.checkBoxInspeccion.isChecked){
            binding.textViewOrdenTrabajo.visibility = View.GONE
            binding.editTextOrdenTrabajo.visibility = View.GONE
            binding.textViewIdEquipo.visibility = View.GONE
            binding.EditTextIdEquipo.visibility = View.GONE
            binding.textViewTipoEmergencia.visibility = View.GONE
            binding.EditTextTipoEmergencia.visibility = View.GONE
            binding.textViewCausaFalla.visibility = View.GONE
            binding.EditTextCausaFalla.visibility = View.GONE
            binding.textViewMotivoReprogramar.visibility = View.GONE
            binding.EditTextMotivoReprogramar.visibility = View.GONE
            binding.ButtonReprogramar.visibility = View.GONE
        }else if(binding.checkBoxEmergencia.isChecked){
            binding.textViewOrdenTrabajo.visibility = View.VISIBLE
            binding.editTextOrdenTrabajo.visibility = View.VISIBLE
            binding.textViewIdEquipo.visibility = View.VISIBLE
            binding.EditTextIdEquipo.visibility = View.VISIBLE
            binding.textViewTipoEmergencia.visibility = View.VISIBLE
            binding.EditTextTipoEmergencia.visibility = View.VISIBLE
            binding.textViewCausaFalla.visibility = View.VISIBLE
            binding.EditTextCausaFalla.visibility = View.VISIBLE
            binding.EditTextMotivoReprogramar.visibility = View.GONE
            binding.ButtonReprogramar.visibility = View.GONE
            binding.textViewMotivoReprogramar.visibility = View.GONE
            binding.EditTextMotivoReprogramar.visibility = View.GONE
        }
        else{
            Toast.makeText(this, "debe seleccionar una opcion", Toast.LENGTH_LONG).show()
        }
    }

    private fun OnclickSend(){
        binding.buttonSend.setOnClickListener {
            //val almacen = validarID()
            Toast.makeText(this, "ELEMENTOS GUARDADOS ", Toast.LENGTH_SHORT).show()
            guardarId()
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

    private fun guardarId(){
        val almacenamiento = Almacenamiento()
        almacenamiento.idEquipo = binding.EditTextIdEquipo.text.toString()
        almacenamiento.ordenTrabajo = binding.editTextOrdenTrabajo.text.toString()
        almacenamiento.tipoServicio = binding.textViewCheckSeleccion.text.toString()
        viewModel.GuardarItem(almacenamiento)
    }

    private fun validarID(): Almacenamiento{
        val almacanamiento = Almacenamiento()
        if(!binding.EditTextIdEquipo.toString().isEmpty()){
            almacanamiento.idEquipo = binding.EditTextIdEquipo.toString()
        }
        return almacanamiento
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if(itemId == R.id.search_menu_reprogramar) {
            binding.textViewOrdenTrabajo.visibility = View.VISIBLE
            binding.editTextOrdenTrabajo.visibility = View.VISIBLE
            binding.textViewIdEquipo.visibility = View.VISIBLE
            binding.EditTextIdEquipo.visibility = View.VISIBLE
            binding.textViewTipoEmergencia.visibility = View.GONE
            binding.EditTextTipoEmergencia.visibility = View.GONE
            binding.textViewCausaFalla.visibility = View.GONE
            binding.EditTextCausaFalla.visibility = View.GONE
            binding.textViewMotivoReprogramar.visibility = View.VISIBLE
            binding.EditTextMotivoReprogramar.visibility = View.VISIBLE
            binding.ButtonReprogramar.visibility = View.VISIBLE
        }else if(itemId == R.id.search_menu_hoja7){
            val intent = Intent(this, ActivitySheet5::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun reporgramar(){
        binding.ButtonReprogramar.setOnClickListener{
            Toast.makeText(this, "ELEMENTOS GUARDADOS ", Toast.LENGTH_SHORT).show()
            guardarId()
            val intent = Intent(this, ActivitySheet5::class.java)
            startActivity(intent)
        }
    }




}