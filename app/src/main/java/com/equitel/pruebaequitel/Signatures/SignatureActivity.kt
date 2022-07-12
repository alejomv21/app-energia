package com.equitel.pruebaequitel.Signatures

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.equitel.pruebaequitel.databinding.ActivitySignatureBinding

class SignatureActivity : AppCompatActivity(){
    lateinit var contenido: ConstraintLayout
    lateinit var binding : ActivitySignatureBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignatureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contenido = binding.constrainRecicler
        contenido.setOnClickListener {

        }

    }


    private fun guardarLayout(){
        contenido.getDrawingCache(true);


    }


    open fun getBitmapFromView(view: View): Bitmap? {
        var bitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        var canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    open fun getBitmapFromView(view: View, defaultColor: Int): Bitmap? {
        var bitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        var canvas = Canvas(bitmap)
        canvas.drawColor(defaultColor)
        view.draw(canvas)
        return bitmap
    }

}